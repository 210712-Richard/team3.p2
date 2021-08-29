package com.revature.services;

import java.util.UUID;

import com.revature.beans.Notification;
import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {

	/**
	 * Creates a notification and sends it to a specific user. A random UUID is
	 * generated for the notification.
	 * 
	 * @param username The username of the user to whom the notification is being
	 *                 sent
	 * @param message  The message contained within the notification
	 */

	void notify(String username, String message);

	/**
	 * Allows a user to check their notifications.
	 * 
	 * @param user The user whose notifications we want to check
	 * @return The notifications that the user has not seen yet
	 */

	Flux<Notification> checkNotifications(User user);

	/**
	 * Allows a user to find a notification by a particular UUID
	 * 
	 * @param user The user who the notification has been sent to
	 * @param id   The UUID of the notification that the user wants to look at
	 * @return The notification. Returns null if no notification exists with the
	 *         specified parameters
	 */

	Mono<Notification> checkNotificationByID(User user, UUID id);

	/**
	 * Clears all the notifications that a particular user has accumulated
	 * 
	 * @param user The user whose notifications are to be cleared
	 */
	
	void clearNotifications(User user);
	
	/**
	 * Notifies all users in the system
	 * 
	 * @param message The notification message
	 */

	void notifyAll(String message);

	/**
	 * Notifies all users affiliated with a product
	 * 
	 * @param product The product that the users are affiliated with
	 * @param message The notification message
	 */

	void notifyAllInProduct(Product product, String message);

	/**
	 * Notifies all users affiliated with a scrum board
	 * 
	 * @param product The product that the users are in
	 * @param board   The scrum board that the users are affiliated with
	 * @param message The notification message
	 */

	void notifyAllInScrumBoard(ScrumBoard board, String message);

	
}
