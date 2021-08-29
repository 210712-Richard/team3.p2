package com.revature.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.aspects.IsProductMaster;
import com.revature.aspects.IsScrumMaster;
import com.revature.beans.ScrumBoard;
import com.revature.beans.Sprint;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.dto.TaskDTO;
import com.revature.services.NotificationService;
import com.revature.services.TaskService;
import com.revature.util.WebSessionAttributes;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	//As a developer, I can move my task on the scrumboard
	@PatchMapping(value = "/status/{boardId}/{taskId}/{status}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Task>> moveTask(@PathVariable("taskId") String taskId, @PathVariable("boardId") String boardId, @PathVariable("status") TaskCompletionStatus status, @RequestBody Task task, WebSession session){
		return taskService.moveTask(UUID.fromString(boardId), UUID.fromString(taskId), status, task.getStatus())
			.map(s -> ResponseEntity.ok(s))
			.defaultIfEmpty(ResponseEntity.status(409).build()); 
	}
	
	//As a Product Owner, I can add to the Product Backlog
	@PostMapping("/{productId}")
	public Mono<ResponseEntity<Task>> addToProductBackLog(@PathVariable("productId") UUID productId, @RequestBody TaskDTO task){
		return taskService.addToProductBackLog(productId, task)
			.flatMap(s ->Mono.just(ResponseEntity.ok(s)))
			.defaultIfEmpty(ResponseEntity.status(409).build());
	}
	
	
	//As a Product Owner, I can add a priority to an existing Product backlog task
	@PatchMapping(value = "/priority/{masterBoardId}/{taskId}/{priority}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	@IsProductMaster
	public Mono<ResponseEntity<Task>> makePriority(@PathVariable ("masterBoardId") String masterBoardId, @PathVariable ("taskId") String taskId, @PathVariable ("priority") TaskPriority priority, WebSession session){
		return taskService.makePriority(UUID.fromString(masterBoardId), UUID.fromString(taskId), priority)
				.map(s -> ResponseEntity.ok(s))
				.defaultIfEmpty(ResponseEntity.status(409).build()); 
	}
	//Undo Add test
	@DeleteMapping(value = "/{boardid}/{status}/{taskid}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Object>> undoAdd(@PathVariable ("boardid") UUID boardid, @PathVariable ("status") TaskCompletionStatus status, @PathVariable ("taskid") UUID taskid){
		return taskService.undoAdd(boardid, status, taskid)
			.flatMap(s -> Mono.just(ResponseEntity.ok(s))
			.defaultIfEmpty(ResponseEntity.status(409).build()));
		}
	
	//As a Scrum Master, I can add to the Sprint backLog from the Product Backlog or from a finished sprint
	@PatchMapping(value = "/{taskBoardId}/{taskStatus}/{taskId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Sprint>> addToSprintBackLog(@PathVariable ("taskBoardId") UUID taskBoardId, @PathVariable ("taskStatus") TaskCompletionStatus taskStatus, @PathVariable ("taskId") UUID taskId, @RequestBody Sprint sprint, WebSession session){
		return taskService.addToSprintBackLog(sprint.getScrumboardID(), sprint.getStatus(), taskBoardId, taskStatus, taskId)
				.map(s -> ResponseEntity.ok(s))
				.defaultIfEmpty(ResponseEntity.status(409).build()); 
	}
	
	@PatchMapping(value = "/{taskBoardId}/{taskStatus}/{taskId}/{masterBoardId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<Sprint>> undoProductBacklog(@PathVariable ("taskBoardId") UUID taskBoardId, @PathVariable ("taskStatus") TaskCompletionStatus taskStatus, @PathVariable ("taskId") UUID taskId, @PathVariable ("masterBoardId") UUID masterBoardId, @RequestBody Sprint sprint, WebSession session){
		return taskService.addToSprintBackLog(sprint.getScrumboardID(), sprint.getStatus(), taskBoardId, taskStatus, taskId).flatMap(s -> Mono.just(ResponseEntity.ok(s))
				.defaultIfEmpty(ResponseEntity.status(409).build()));
	}
	
	@PatchMapping(value = "/assign/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@IsScrumMaster
	public Mono<ResponseEntity<User>> assignTask(@PathVariable("id") UUID id, @PathVariable("username") String username, WebSession session){
		return taskService.assignTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
	@PostMapping("/apply/{id}")
	public Mono<ResponseEntity<String>> applyTask(@PathVariable("id") UUID id, WebSession session){
		ScrumBoard board = session.getAttribute(WebSessionAttributes.SELECTED_SCRUM_BOARD);
		User loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		String message = loggedUser.getUsername() + " wants to apply for the task " + id;
		notificationService.notify(board.getScrumMasterUsername(), message);
		return Mono.just(ResponseEntity.ok("You have successfully applied for this task!"));
	}
	
	@PatchMapping(value = "/remove/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@IsScrumMaster
	public Mono<ResponseEntity<User>> removeTask(@PathVariable("id") UUID id, @PathVariable("username") String username, WebSession session){
		//check if user has selected a product, scrum and is the scrum master.
		return taskService.removeTasks(id, username)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
}