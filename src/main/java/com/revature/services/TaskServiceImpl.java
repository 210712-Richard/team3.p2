package com.revature.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;

import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService{

	private static Logger log = LogManager.getLogger(TaskServiceImpl.class);

	private TaskDAO taskDAO;
	private SprintDAO sprintDAO;
	private UserDAO userDAO;
	
	
	@Autowired
	public TaskServiceImpl(TaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
	}
	
	
	@Override
	public Mono<Task> moveTask(UUID boardid, UUID taskId, TaskCompletionStatus status, TaskCompletionStatus newStatus) {
		//Move task within scrumboard by changing the status
		System.out.println("Move task service");
		System.out.println(boardid.toString() +" "   + status.toString() + " " +taskId.toString());
		return taskDAO.findByBoardidAndStatusAndId(boardid, status.toString(), taskId).map(dto -> {
			taskDAO.delete(dto);
			dto.setStatus(newStatus);
			taskDAO.save(dto);
			System.out.println("Calling get task");
			System.out.println(dto.getTask());
			return dto.getTask();
		});
			
	}

	@Override
	public Mono<Task> addToProductBackLog(UUID product, Task task) {
		return null;
		//New task added to product backlog
		/*
		 * How do i do this without a list of tasks in product object
		 */
		
		
	}

	@Override
	public Mono<Task> makePriority(UUID taskId, TaskPriority priority) {
		//Change priority status of an existing task
		return taskDAO.findById(taskId.toString()).map(dto -> {
			dto.setPriorityStatus(priority);
			taskDAO.save(dto);
			return dto.getTask();
		});
		
	}

	@Override
	public Mono<Sprint> addToSprintBackLog(UUID sprintId, UUID taskId) {
		//Find Task set status to backlog
		taskDAO.findById(taskId.toString()).map(dto -> {
			dto.setStatus(TaskCompletionStatus.BACKLOG);
			taskDAO.save(dto);
			return dto.getTask();
		});
		
		//Find sprint and add this task to the sprint's list of tasks
		return sprintDAO.findById(sprintId.toString()).map(dto -> {
			dto.getTaskIds().add(taskId);
			sprintDAO.save(dto);
			return dto.getSprint();
		});
	}
	
	@Override
	public Mono<User> assignTasks(UUID taskId, String username) {

		return userDAO.findById(username).map(dto -> {
			dto.getTaskIds().add(taskId);
			userDAO.save(dto);
			return dto.getUser();
		});
	}
	
	@Override
	public Mono<User> removeTasks(UUID id, String username){
		return userDAO.findById(username)
				.map(dto -> {
					dto.getTaskIds()
						.removeIf(p-> p.equals(id));
					return dto.getUser();
				});
	}
}
