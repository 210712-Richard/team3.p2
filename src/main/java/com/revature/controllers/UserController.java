package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.services.NotificationService;
import com.revature.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationService notificationService;

	// As a user, I can read and access notifications

	@GetMapping("/notifications")
	public ResponseEntity<Flux<Notification>> getNotifications(WebSession session) {
		User loggedUser = session.getAttribute("loggedUser");
		System.out.println(loggedUser);
		if (loggedUser == null) {
			return ResponseEntity.status(403).build();
		}
		Flux<Notification> noteFlux = notificationService.checkNotifications(loggedUser);
		return ResponseEntity.ok(noteFlux);
	}

	@GetMapping("/notifications/{id}")
	public Mono<ResponseEntity<Notification>> getNotificationById(@PathVariable("id") String id, WebSession session) {
		User loggedUser = session.getAttribute("loggedUser");
		if (loggedUser == null) {
			return Mono.just(ResponseEntity.status(403).build());
		}
		return notificationService.checkNotificationByID(session.getAttribute("loggedUser"), UUID.fromString(id)).map(note -> {
			if (note == null) {
				return ResponseEntity.status(402).build();
			} else {
				return ResponseEntity.ok(note);
			}
		});
	}
	//As a user I can login
	@PostMapping("/users")
	public ResponseEntity<Mono<User>> login (@RequestBody User user, WebSession session){
		log.trace("User login method");
		
		Mono<User> loggedUser = userService.login(user.getUsername());
		
		if (loggedUser == null) {
			return ResponseEntity.notFound().build();
		}
		session.getAttributes().put("loggedUser", user);
		
		return ResponseEntity.ok(loggedUser);
	}

}
