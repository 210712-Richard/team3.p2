package com.revature.services;

import java.util.UUID;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;

import reactor.core.publisher.Mono;

public interface ScrumService {

	/**
	 * Checks the sprint table for sprints that have reached their end
	 * time and date, or sprints that have reached their start time and date. If a
	 * sprint has ended, its status is changed from CURRENT to PAST. If a sprint is 
	 * beginning, its status is changed from FUTURE to CURRENT. 
	 */

	void autoUpdate();

	/**
	 * 
	 * @param user
	 * @param scrumBoard board to create
	 * @param product selected product
	 * @return created db board
	 */
	Mono<ScrumBoard> createScrumBoard(User user, ScrumBoard scrumBoard, Product product);

	/**
	 * Adds a user to a board
	 * 
	 * @param username The username that is being added
	 * @param boardId The id of the board that the user is being added to
	 * @return A mono of the user that was added
	 */
	
	Mono<User> addUserToBoard(String username, UUID boardId);
	
}
