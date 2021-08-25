package com.revature.services;

import java.util.UUID;

import com.revature.beans.Sprint;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;

import reactor.core.publisher.Mono;

public interface TaskService {
	
	Mono<Task> moveTask(UUID taskId, TaskCompletionStatus status);
	
	Mono<Task> addToProductBackLog(UUID product, Task task);
	
	Mono<Task> makePriority(UUID taskId, TaskPriority priority);
	
	Mono<Sprint> addToSprintBackLog(UUID sprint, UUID taskId);	
	/**
	 * Method is used to assign tasks to a particular user.
	 * 
	 * @param taskId : id for the particular task
	 * @param username : user thats assigned task
	 * @return User to be displayed with new 
	 */
	Mono<User> assignTasks(UUID taskId, String username);
	/**
	 * Method is used to remove tasks from a particular user.
	 * 
	 * @param taskId : id for the particular task
	 * @param username : user thats de-assigned a task
	 * @return User to be displayed with task removed
	 */
	Mono<User> removeTasks(UUID id, String username);
}
