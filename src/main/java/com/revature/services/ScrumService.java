package com.revature.services;

import java.util.UUID;

import com.revature.beans.User;

import reactor.core.publisher.Mono;

public interface ScrumService {
	/**
	 * Method is used to assign tasks to a particular user.
	 * 
	 * @param taskId   : id for the particular task
	 * @param username : user thats assigned task
	 * @return User to be displayed with new
	 */
	Mono<User> assignTasks(UUID taskId, String username);

	/**
	 * Method is used to remove tasks from a particular user.
	 * 
	 * @param taskId   : id for the particular task
	 * @param username : user thats de-assigned a task
	 * @return User to be displayed with task removed
	 */
	Mono<User> removeTasks(UUID id, String username);

	/**
	 * Checks the sprint table for sprints that have reached their end
	 * time and date, or sprints that have reached their start time and date. If a
	 * sprint has ended, its status is changed from CURRENT to PAST. If a sprint is 
	 * beginning, its status is changed from FUTURE to CURRENT. 
	 */

	void autoUpdate();

}
