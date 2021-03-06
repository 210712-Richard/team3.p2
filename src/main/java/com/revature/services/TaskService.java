package com.revature.services;

import java.util.UUID;

import com.revature.beans.Sprint;
import com.revature.beans.SprintStatus;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.dto.TaskDTO;

import reactor.core.publisher.Mono;

public interface TaskService {
	/**
	 * 
	 * @param boardId
	 * @param taskId
	 * @param status
	 * @return
	 */
	Mono<Task> moveTask(TaskCompletionStatus status, Task task);
	
	Mono<Task> addToProductBackLog(UUID product, TaskDTO task);
		
	Mono<Task> makePriority(UUID masterBoardId, UUID taskId, TaskPriority priority);
	
	Mono<Sprint> addToSprintBackLog(UUID sprintBoardId, SprintStatus sprintStatus, UUID taskBoardId, TaskCompletionStatus taskStatus, UUID taskId);	
			
	Mono<User> assignTasks(UUID taskId, String username);
	
	Mono<User> removeTasks(UUID id, String username);
	
	
	
	
}
