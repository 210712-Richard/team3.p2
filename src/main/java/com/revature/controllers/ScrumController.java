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
		if(id == null || username == null) {
			System.out.println("null");
		}
		return scrumService.assignTasks(id, username).map( user ->{
			if(user == null) {
				return ResponseEntity.status(402).build();
			}else {
				return ResponseEntity.ok(user);
			}
		});
	}
}
