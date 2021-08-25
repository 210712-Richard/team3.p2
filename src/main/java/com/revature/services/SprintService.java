package com.revature.services;

import java.util.UUID;

import com.revature.beans.Sprint;

import reactor.core.publisher.Mono;
/**
 * 
 * @author MuckJosh
 *
 */

public interface SprintService {
	
	/**
	 * 
	 * @param sprint sprint to be created 
	 * @return returns the sprint inserted into database
	 */
	Mono<Sprint> createSpring(Sprint sprint);

	/**
	 * 
	 * @param boardId  id of the scrumboard
	 * @param sprintId  id of the sprint to be modified
	 * @return returns the sprint to be closed
	 */
	Mono<Sprint> retireCurrentSprint(UUID scrumboardID);

}
