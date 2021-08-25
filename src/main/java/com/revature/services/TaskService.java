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
	
	Mono<User> assignTasks(UUID taskId, String username);
	
	Mono<User> removeTasks(UUID id, String username);
}
