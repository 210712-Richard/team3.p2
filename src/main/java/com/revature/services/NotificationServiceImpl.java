package com.revature.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.data.NotificationDAO;
import com.revature.dto.NotificationDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author sidd
 *
 */

@Service
public class NotificationServiceImpl implements NotificationService {

	private static Logger log = LogManager.getLogger(NotificationServiceImpl.class);

	private NotificationDAO nd;

	public NotificationServiceImpl(NotificationDAO nd) {
		super();
		this.nd = nd;
	}

	@Override
	public void notify(User user, String message) {
		Notification note = new Notification();
		note.setUsername(user.getUsername());
		note.setMessage(message);
		note.setId(UUID.randomUUID());
		nd.save(new NotificationDTO(note));
	}

	@Override
	public Flux<Notification> checkNotifications(User user) {

		// .findAll() returns a flux of NotificationDTOs. We are mapping each
		// element of that flux to a Notification using the .getNotification()
		// function. Then, we filter the flux down into only notifications where
		// the username matches the one for the user that is supplied.
		
		return nd.findByUsername(user.getUsername()).map(dto -> dto.getNotification()).flux();
		
//		return nd.findAll()
//				.map(dto -> dto.getNotification())
//				.filter(note -> note.getUsername().equals(user.getUsername()));
//		
	}

	@Override
	public Mono<Notification> checkNotificationByID(User user, UUID id) {
		
		// Here, I am using a method I created in NotificationDAO to get a Mono
		// of NotificationDTO. I am then mapping that DTO to a regular 
		// Notification and returning that as a Mono.
		
		return nd.findByUsernameAndId(user.getUsername(), id).map(dto -> dto.getNotification());
	}

}
