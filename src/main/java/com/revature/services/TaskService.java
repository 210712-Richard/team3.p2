package com.revature.services;

import java.util.UUID;

import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;

public interface TaskService {
	
	void moveTask(UUID taskId, TaskCompletionStatus status);
	
	void addToProductBackLog(UUID product, Task task);
	
	void makePriority(UUID taskId, TaskPriority priority);
	
	void addToSprintBackLog(UUID sprint, UUID taskId);
	
	void assignTasks(UUID user, UUID taskId);
	
	
}
