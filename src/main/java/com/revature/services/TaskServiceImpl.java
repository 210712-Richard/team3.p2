package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.beans.SprintStatus;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;

import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService{

	private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
	private TaskDAO taskDAO;
	private SprintDAO sprintDAO;
	private UserDAO userDAO;
	private ProductDAO productDAO;
	
	
	@Autowired
    public TaskServiceImpl(TaskDAO taskDAO, SprintDAO sprintDAO, UserDAO userDAO, ProductDAO productDAO) {
        super();
        this.taskDAO = taskDAO;
        this.sprintDAO = sprintDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }
	
	public TaskServiceImpl() {
	}

	@Override
	public Mono<Task> moveTask(TaskCompletionStatus status, Task task) {
		//Move task within scrumboard by changing the status
		return taskDAO.findByBoardidAndStatusAndId(task.getBoardId(), task.getStatus().name(), task.getId()).flatMap(dto -> {
			taskDAO.delete(dto).subscribe();
			dto.setStatus(TaskCompletionStatus.valueOf(status.toString()));
			log.warn(dto.toString());
			return taskDAO.insert(dto);
		}).map(t -> t.getTask());
			
	}

	@Override
	public Mono<Task> addToProductBackLog(UUID product, TaskDTO task) {
		//New task added to product backlog
		return productDAO.findByProductid(product).flatMap(dto -> {
			task.setBoardid(dto.getMasterBoardId());
			task.setStatus(TaskCompletionStatus.PRODUCT_BACKLOG);
			task.setId(UUID.randomUUID());
			return taskDAO.save(task);
		}).map(t -> t.getTask());
		
	}

	@Override
	public Mono<Task> makePriority(UUID masterBoardId, UUID taskId, TaskPriority priority) {
		//Change priority status of an existing Product Backlog task
		return taskDAO.findByBoardidAndStatusAndId(masterBoardId,TaskCompletionStatus.BACKLOG.name(), taskId).flatMap(dto -> {
			dto.setPriorityStatus(TaskPriority.valueOf(priority.toString()));
			log.warn(dto.toString());
			return taskDAO.save(dto);
		}).map(t -> t.getTask());
	}

	@Override
	public Mono<Sprint> addToSprintBackLog(UUID sprintBoardId, SprintStatus sprintStatus, UUID taskBoardId, TaskCompletionStatus taskStatus, UUID taskId) {
		//Find Task set status to backlog
		Mono<Task> task = taskDAO.findByBoardidAndStatusAndId(taskBoardId, taskStatus.name(), taskId).map(t -> {
			taskDAO.delete(t).subscribe();
			return t.getTask();
		});

		//Find sprint and add this task to the sprint's list of tasks
		Mono<Sprint> sprint = sprintDAO.findByScrumboardIDAndStatus(sprintBoardId, sprintStatus).map(t -> t.getSprint());
		
		return Mono.zip(task, sprint).flatMap(z -> {
			Task t = z.getT1();
			Sprint s = z.getT2();
			t.setBoardId(s.getScrumboardID());
			t.setStatus(TaskCompletionStatus.BACKLOG);
			taskDAO.save(new TaskDTO(t)).subscribe();
			List<UUID> nList = new ArrayList<>();
			if(s.getTaskIds() != null) {
				//Collections.copy(nList, s.getTaskIds());
				nList.addAll(s.getTaskIds());
			}
			if(nList.contains(t.getId())) {
				nList.remove(t.getId());
			}
//			if(s.getTaskIds() != null) {
//				Collections.copy(nList, s.getTaskIds());
//			}
			nList.addAll(s.getTaskIds());
			nList.add(t.getId());
			s.setTaskIds(nList);
			return sprintDAO.save(new SprintDTO(s));
		}).map(s -> s.getSprint());
	}
	
	@Override
	public Mono<User> assignTasks(UUID taskId, String username) {
		return userDAO.findById(username).flatMap(dto -> {
			List<UUID> list = dto.getTaskIds().stream().collect(Collectors.toList());
			list.add(taskId);
			dto.setTaskIds(list);
			return userDAO.save(dto);
		}).map(u -> u.getUser());
	}
	
	@Override
	public Mono<User> removeTasks(UUID id, String username) {
		return userDAO.findById(username).flatMap(dto -> {
			List<UUID> list = dto.getTaskIds().stream().collect(Collectors.toList());
			list.removeIf(p->p.equals(id));
			dto.setTaskIds(list);
			return userDAO.save(dto);
		}).map(u -> u.getUser());
	}

}



