package com.revature.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface UserService {

	/**
	 * Logs in a specific user
	 * 
	 * @param username The username of the user
	 * @param password The password of the user
	 * @return A mono of the user that is being logged in, once the system queries
	 *         the database
	 */

	Mono<User> login(String username, String password);

	/**
	 * Registers a user and puts it in the database
	 * 
	 * @param username The username of the user being registered
	 * @return The registered user as a User object.
	 */

	Mono<User> register(User user);

	/**
	 * Allows Admin to view user
	 * 
	 * @param user The admin user account
	 * @param employee The name of the employee being searched
	 * @return The employee information as a User object
	 */
	Mono<User> viewUser (User user, String employee);
	
	/**
	 * Allows Admin to change credentials of a user
	 * 
	 * @param user The admin user's account
	 * @param employee the UserDTO of the employee's information
	 * @param password the new password for the employee
	 * @param email the new email of the employee
	 * @param type the new type of the employee
	 * @return User the updated user of the employee
	 */
	
	Mono<User> changeUserCredentials(User employee,
			String password, String email);
	

	
	/**
	 * Allows an admin user to change the UserType of a user
	 * 
	 * @param user
	 * @param employee
	 * @String type
	 */

	User roleChange(User user, User employee, String type);
	

	

// THE 4 METHODS BELOW MAY BE BETTER CANDIDATES FOR OTHER SERVICE LAYERS, PENDING DISCUSSION
	
	/**
	 * Allows a user to view all the products that they are involved with
	 * 
	 * @param user The user who wants to check their products
	 * @return A flux of all products that the user is involved with
	 */
	Flux<Product> viewProducts(User user);

	/**
	 * Allows a user to select a particular product from the list of products that
	 * they are a part of
	 * 
	 * @param user The user who wants to select a product
	 * @return a Mono of the product that the user selected
	 */

	Mono<Product> selectProduct(User user, UUID productId);

	/**
	 * Allows a user to view all the scrum boards that they are involved with
	 * 
	 * @param user The user who wants to check their scrum boards
	 * @return A flux of all scrum boards that the user is involved with
	 */

	Flux<ScrumBoard> viewScrumBoards(User user);

	/**
	 * Allows a user to select a particular scrum board from the list of scrum
	 * boards that they are a part of
	 * 
	 * @param user The logged in user
	 * @param product The currently selected product
	 * @param boardId The id of the board that the user wants to check
	 * @return A Mono of the board that the user selected
	 */

	Mono<ScrumBoard> selectScrumBoard(User user, Product product, UUID boardId);

}
