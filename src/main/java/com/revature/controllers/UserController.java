package com.revature.controllers;

import java.util.UUID;

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
import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.services.NotificationService;
import com.revature.services.UserService;
import com.revature.util.WebSessionAttributes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	private User loggedUser;

	@Autowired
	private NotificationService notificationService;

	// As a user, I can read and access notifications

	@GetMapping("/notifications")
	public ResponseEntity<Flux<Notification>> getNotifications(WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			return ResponseEntity.status(403).build();
		}
		Flux<Notification> noteFlux = notificationService.checkNotifications(loggedUser);
		return ResponseEntity.ok(noteFlux);
	}

	@GetMapping("/notifications/{id}")
	public Mono<ResponseEntity<Notification>> getNotificationById(@PathVariable("id") String id, WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			return Mono.just(ResponseEntity.status(403).build());
		}
		return notificationService
				.checkNotificationByID(session.getAttribute(WebSessionAttributes.LOGGED_USER), UUID.fromString(id))
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
		return userService.login(user.getUsername(), user.getPassword()).map(u -> {
			session.getAttributes().put(WebSessionAttributes.LOGGED_USER, u);
			return ResponseEntity.ok(u);
		}).switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));
	}

	// As a User I can logout
	@DeleteMapping
	public ResponseEntity<Void> logout(WebSession session) {
		session.invalidate();
		return ResponseEntity.noContent().build();
	}

	// As a user I can register an account
	@PutMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> register(@RequestBody User user, WebSession session) {
		// getting the data from the new user
		return userService.register(user).map(u -> {
			session.getAttributes().put(WebSessionAttributes.LOGGED_USER, u);
			return ResponseEntity.ok(u);
		}).switchIfEmpty(Mono.just(ResponseEntity.status(404).build()));
	}

	// As a user, I can select a product
	@PostMapping("/products/{productId}")
	public Mono<ResponseEntity<Product>> selectProduct(@PathVariable String productId, WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		return userService.selectProduct(loggedUser, UUID.fromString(productId)).map(prod -> {
			session.getAttributes().put(WebSessionAttributes.SELECTED_PRODUCT, prod);
			return ResponseEntity.ok(prod);
		}).switchIfEmpty(Mono.just(ResponseEntity.status(404).build()));
	}

	// As a user, I can select a scrum board
	@PostMapping("/scrumboards/{scrumId}")
	public Mono<ResponseEntity<ScrumBoard>> selectScrumBoard(@PathVariable String scrumId, WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		Product product = session.getAttribute(WebSessionAttributes.SELECTED_PRODUCT);
		return userService.selectScrumBoard(loggedUser, product, UUID.fromString(scrumId)).map(scrum -> {
			session.getAttributes().put(WebSessionAttributes.SELECTED_SCRUM_BOARD, scrum);
			return ResponseEntity.ok(scrum);
		}).switchIfEmpty(Mono.just(ResponseEntity.status(404).build()));
	}

	// As a user, I can view all the products I am involved with
	@GetMapping("/products")
	public ResponseEntity<Flux<Product>> viewProducts(WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		return ResponseEntity.ok(userService.viewProducts(loggedUser));
	}

	// As a user, I can view all the scrum boards I am involved with
	@GetMapping("/boards")
	public ResponseEntity<Flux<ScrumBoard>> viewScrumBoards(WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		return ResponseEntity.ok(userService.viewScrumBoards(loggedUser));
	}

	// As an Admin I can view a user
	@GetMapping("{employee}")
	public ResponseEntity<Mono<User>> getCurrentUsers(@PathVariable("employee") String employee, WebSession session) {
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		// checking if logged user is an admin
		if (!loggedUser.getType().equals(UserType.ADMIN)) {
			return ResponseEntity.status(403).build();
		} else {
			Mono<User> employeeData = userService.viewUser(loggedUser, employee);
			return ResponseEntity.ok(employeeData);
		}
	}

	// As an Admin I can change user roles
	@PostMapping("{employee}/newRole/{role}")
	public ResponseEntity<User> changeUserRole(@PathVariable("employee") String employee,
			@PathVariable("role") String role, WebSession session) {

		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);

		User employeeData = userService.viewUser(loggedUser, employee).block();

		User changedEmp = userService.roleChange(loggedUser, employeeData, role);

		return ResponseEntity.ok(changedEmp);

	}

	// As an Admin I can change user credentials

}
