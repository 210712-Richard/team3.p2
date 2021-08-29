package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Notification;
import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.data.NotificationDAO;
import com.revature.data.ProductDAO;
import com.revature.data.UserDAO;
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

	private NotificationDAO nd;
	private UserDAO ud;
	private ProductDAO pd;

	@Autowired
	public NotificationServiceImpl(NotificationDAO nd, UserDAO ud, ProductDAO pd) {
		super();
		this.nd = nd;
		this.ud = ud;
		this.pd = pd;
	}

	@Override
	public void notify(String username, String message) {
		Notification note = new Notification();
		note.setUsername(username);
		note.setMessage(message);
		note.setId(UUID.randomUUID());
		nd.save(new NotificationDTO(note)).subscribe();
	}

	@Override
	public Flux<Notification> checkNotifications(User user) {

		// .findAll() returns a flux of NotificationDTOs. We are mapping each
		// element of that flux to a Notification using the .getNotification()
		// function. Then, we filter the flux down into only notifications where
		// the username matches the one for the user that is supplied.

		return nd.findByUsername(user.getUsername()).map(dto -> dto.getNotification());

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

	@Override
	public void clearNotifications(User user) {
		nd.findByUsername(user.getUsername()).map(dto -> {
			return nd.delete(dto).subscribe();
		}).subscribe();
	}
	
	@Override
	public void notifyAll(String message) {
		Notification note = new Notification();
		ud.findAll().map(dto -> {
			note.setUsername(dto.getUsername());
			note.setMessage(message);
			note.setId(UUID.randomUUID());
			nd.save(new NotificationDTO(note)).subscribe();
			return dto;
		}).subscribe();
	}

	@Override
	public void notifyAllInProduct(Product product, String message) {
		Notification note = new Notification();
		ud.findAll().filter(dto -> product.getUsernames().contains(dto.getUsername())).map(dto -> {
			note.setUsername(dto.getUsername());
			note.setMessage(message);
			note.setId(UUID.randomUUID());
			nd.save(new NotificationDTO(note)).subscribe();
			return dto;
		}).subscribe();
	}

	@Override
	public void notifyAllInScrumBoard(ScrumBoard board, String message) {
		Notification note = new Notification();
		Flux.from(pd.findByProductid(board.getProductId()))
			.flatMap(dto -> Flux.fromStream(dto.getUsernames().stream()))		
			.flatMap(username -> ud.findByUsername(username))
			.filter(data -> data.getUser().getBoardIds().contains(board.getId()))
			.map(d -> {
				note.setUsername(d.getUsername());
				note.setMessage(message);
				note.setId(UUID.randomUUID());
				return nd.save(new NotificationDTO(note)).subscribe();
			}).subscribe();
	}
}
