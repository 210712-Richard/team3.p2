package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.data.NotificationDAO;
import com.revature.dto.NotificationDTO;

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
	public List<Notification> checkNotifications(User user) {

		// The findAllByID function takes in a list of ids and returns all entities
		// matching those ids. As such, it is necessary to make the username of the
		// user passed here into a list with a single element.

		List<String> username = new ArrayList<String>();
		username.add(user.getUsername());

		// Here, findAllById is being called, returning a list of NotificationDTO
		// objects. That list is being turned into a stream, and each object in
		// that stream is being mapped to notification. Those notifications are
		// then being collected into a list, and that list of notifications is
		// returned.

		List<Notification> notes = nd.findAllById(username)
				.stream()
				.map(nDTO -> nDTO.getNotification())
				.collect(Collectors.toList());
		return notes;
	}

	@Override
	public Notification checkNotificationByID(User user, UUID id) {
		
		NotificationDTO databaseNote = nd.findById(id.toString()).orElse(null);
		Notification note = databaseNote.getNotification();
		return note;
	}

}
