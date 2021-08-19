package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.beans.Notification;
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
		return ResponseEntity.ok(notificationService.checkNotifications(session.getAttribute("loggedUser")));
	}

	@GetMapping("/notifications/{id}")
	public ResponseEntity<Mono<Notification>> getNotificationById(@PathVariable("id") String id, WebSession session) {
		return ResponseEntity
				.ok(notificationService.checkNotificationByID(session.getAttribute("loggedUser"), UUID.fromString(id)));
	}

}
