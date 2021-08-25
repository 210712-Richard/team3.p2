package com.revature.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.User;
import com.revature.services.ScrumService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/scrums")
public class ScrumController {

	private ScrumService scrumService;
	
	@Autowired
	ScrumController(ScrumService scrumService){
		this.scrumService = scrumService;
	}
	@PatchMapping(value = "/assign/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> assignTask(@PathVariable("id") UUID id, @PathVariable("username") String username){

		return scrumService.assignTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
	@PatchMapping(value = "/remove/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> removeTask(@PathVariable("id") UUID id, @PathVariable("username") String username){

		return scrumService.removeTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
}
