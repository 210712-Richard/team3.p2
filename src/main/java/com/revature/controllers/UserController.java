package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.dto.UserDTO;
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
		return notificationService.checkNotificationByID(session.getAttribute("loggedUser"), UUID.fromString(id))
				.map(note -> {
					if (note == null) {
						return ResponseEntity.status(402).build();
					} else {
						return ResponseEntity.ok(note);
					}
				});
	}

	// As a user I can login
	@PostMapping("/login")
	public Mono<ResponseEntity<User>> login(@RequestBody User user, WebSession session) {
		return userService.login(user.getUsername(), user.getPassword())
				.map(u -> {
					session.getAttributes().put("loggedUser", u);
					return ResponseEntity.ok(u);
				})
				.switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));
	}

	// As a User I can logout
	@DeleteMapping
	public ResponseEntity<Void> logout(WebSession session) {
		session.invalidate();
		return ResponseEntity.noContent().build();
	}

	// As a user I can register an account
	@PutMapping(value = "{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> register(@RequestBody User user, @PathVariable("username") String username) {
		// getting the data from the new user
		try {
			User newUser = userService.register(username, user.getPassword(), user.getEmail());
			return ResponseEntity.ok(newUser);
		} catch (Exception e) {
			return ResponseEntity.status(500).contentType(MediaType.TEXT_HTML)
					.body("<html><body><div>Failed to make user</div></body></html>");
		}
	}

	// As an Admin I can view a user
	@GetMapping("{employee}")
	public ResponseEntity<Mono<UserDTO>> getCurrentUsers(@PathVariable("username") String employee,
			WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		// checking if logged user is an admin
		if (!loggedUser.getType().equals(UserType.ADMIN)) {
			return ResponseEntity.status(403).build();
		} else {
			Mono<UserDTO> employeeData = userService.viewUser(loggedUser, employee);
			return ResponseEntity.ok(employeeData);
		}
	}

	// As an Admin I can change user roles

	// As an Admin I can change user credentials

}
