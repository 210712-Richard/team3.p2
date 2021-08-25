package com.revature.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.beans.Sprint;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.dto.TaskDTO;
import com.revature.services.TaskService;
import com.revature.util.WebSessionAttributes;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
	//As a developer, I can move my task on the scrumboard
	@PatchMapping(value = "/status/{boardId}/{taskId}/{status}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Task>> moveTask(@PathVariable("taskId") String taskId, @PathVariable("boardId") String boardId, @PathVariable("status") TaskCompletionStatus status, @RequestBody Task task, WebSession session){
		return taskService.moveTask(UUID.fromString(boardId), UUID.fromString(taskId), status, task.getStatus()).map((s) -> {
			if(s == null) {
				return ResponseEntity.status(409).build();
			} else {
				return ResponseEntity.ok(s);
			}
		});
	}
	
	//As a Product Owner, I can add to the Product Backlog
	@PostMapping("/{productId}")
	public Mono<ResponseEntity<Object>> addToProductBackLog(@PathVariable("productId") UUID productId, @RequestBody TaskDTO task){
		return taskService.addToProductBackLog(productId, task).map((s) -> {
			if(s == null) {
				return ResponseEntity.status(409).build();	
			} else {
				return ResponseEntity.ok(s);
			}
		});
	}
	
	
	//As a Product Owner, I can add a priority to an existing Product backlog task
	@PatchMapping(value = "/priority/{masterBoardId}/{taskId}/{priority}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Task>> makePriority(@PathVariable ("masterBoardId") String masterBoardId, @PathVariable ("taskId") String taskId, @PathVariable ("priority") TaskPriority priority, WebSession session){
		return taskService.makePriority(UUID.fromString(masterBoardId), UUID.fromString(taskId), priority).map((s) ->  {
			if(s == null) {
				return ResponseEntity.status(409).build();
			} else {
				return ResponseEntity.ok(s);
			}
		});
	
	}
	
	//As a Scrum Master, I can add to the Sprint backLog from the Product Backlog
	@PutMapping("/{sprintId}/{taskId}")
	public Mono<ResponseEntity<Sprint>> addToSprintBackLog(@PathVariable ("sprintId") UUID sprintId, @PathVariable ("taskId") UUID taskId, WebSession session){
		User loggedUser = (User) session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if(loggedUser == null || !UserType.SCRUM_MASTER.equals(loggedUser.getType())) {
			return Mono.just(ResponseEntity.status(403).build());
		}
		return taskService.addToSprintBackLog(sprintId, taskId).map((s) -> {
			if(s == null) {
				return ResponseEntity.status(409).build();
			} else {
				return ResponseEntity.ok(s);
			}
		});

	}
	
	@PatchMapping(value = "/assign/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> assignTask(@PathVariable("id") UUID id, @PathVariable("username") String username, WebSession session){
		//check if user has selected a product, scrum and is the scrum master.
		return taskService.assignTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
	@PatchMapping(value = "/remove/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> removeTask(@PathVariable("id") UUID id, @PathVariable("username") String username, WebSession session){
		//check if user has selected a product, scrum and is the scrum master.
		return taskService.removeTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
}

