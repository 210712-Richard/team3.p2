package com.revature.services;

import java.util.List;
import java.util.Optional;
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
import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

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
	public Mono<Task> moveTask(UUID taskId, TaskCompletionStatus status) {
		//Move task within scrumboard by changing the status
		Mono<TaskDTO> t = taskDAO.findById(taskId.toString());
		TaskDTO task = t.block();
		if(t.block() == null) {
			return null;
		}
		task.setStatus(status);
		taskDAO.save(task);
		return taskDAO.findById(taskId.toString()).map(tasks -> tasks.getTask());
		
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
		Mono<TaskDTO> t = taskDAO.findById(taskId.toString());
		TaskDTO task = t.block();
		if(t.block() == null) {
			return null;
		}
		task.setPriorityStatus(priority);
		taskDAO.save(task);
		return taskDAO.findById(taskId.toString()).map(tasks -> tasks.getTask());
		
	}

	@Override
	public Mono<Sprint> addToSprintBackLog(UUID sprintId, UUID taskId) {
		//Find Task set status to backlog
		Mono<TaskDTO> t = taskDAO.findById(taskId.toString());
		TaskDTO task = t.block();
		if(t.block() == null) {
			return null;
		}
		task.setStatus(TaskCompletionStatus.BACKLOG);
		
		//Find sprint and add this task to the sprint's list of tasks
		Mono<SprintDTO> sp = sprintDAO.findById(sprintId.toString());
		if(sp.block() == null) {
			return null;
		}
		SprintDTO sprint = sp.block();
		sprint.getTaskIds().add(sprintId);
		sprintDAO.save(sprint);
		return sprintDAO.findById(sprintId.toString()).map(sprints -> sprints.getSprint());
	}

	@Override
	public Mono<User> assignTasks(UUID userId, UUID taskId) {
		//Maybe put this under UserService since its updating a user's task list
		//Find user and add task id to the user's list of task ids
		Mono<UserDTO> u = userDAO.findById(userId.toString());
		UserDTO user = u.block();
		if(u.block() == null) {
			return null;
		}
		user.getTaskIds().add(taskId);
		userDAO.save(user);
		return userDAO.findById(userId.toString()).map(users -> users.getUser());
	
	}
}
