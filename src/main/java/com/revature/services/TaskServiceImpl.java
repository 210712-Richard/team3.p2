package com.revature.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class TaskServiceImpl implements TaskService {

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
		// Move task within scrumboard by changing the status
		return taskDAO.findById(taskId.toString()).map(dto -> {
			dto.setStatus(status);
			taskDAO.save(dto);
			return dto.getTask();
		});

	}

	@Override
	public Mono<Task> addToProductBackLog(UUID product, Task task) {
		return null;
		// New task added to product backlog
		/*
		 * How do i do this without a list of tasks in product object
		 */

	}

	@Override
	public Mono<Task> makePriority(UUID taskId, TaskPriority priority) {
		// Change priority status of an existing task
		return taskDAO.findById(taskId.toString()).map(dto -> {
			dto.setPriorityStatus(priority);
			taskDAO.save(dto);
			return dto.getTask();
		});

	}

	@Override
	public Mono<Sprint> addToSprintBackLog(UUID sprintId, UUID taskId) {
		// Find Task set status to backlog
		taskDAO.findById(taskId.toString()).map(dto -> {
			dto.setStatus(TaskCompletionStatus.BACKLOG);
			taskDAO.save(dto);
			return dto.getTask();
		});

		// Find sprint and add this task to the sprint's list of tasks
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
	public Mono<User> removeTasks(UUID id, String username) {
		return userDAO.findById(username).flatMap(dto -> {
			List<UUID> list = dto.getTaskIds().stream().collect(Collectors.toList());
			list.removeIf(p->p.equals(id));
			dto.setTaskIds(list);
			return userDAO.save(dto);
		}).map(u -> u.getUser());
	}
}
