package com.revature.services;

import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Mono;

@Service
public interface UserService {
	
	/**
	 * Logs in a specific user
	 * 
	 * @param username The username of the user
	 * @param password The password of the user
	 * @return A mono of the user that is being logged in, once the system 
	 * queries the database
	 */
	
	Mono<User> login (String username, String password);
	
	User register(String username, String password, 
			String email);
	
	/*
	 *  Allows admin to change employee.  String employee is the employee being searched
	 */
	
	User roleChange(User user, User employee, String type);
	
	/*
	 * Allows admin to view employee. String employee is the employee being searched
	 */
	
	Mono<User> viewUser (User user, String employee);
	
	/*
	 * requires all of fields need to create a user so that an Admin 
	 * 	can change employee's info. String employee is the username of the employee 
	 * will be changed.
	 */
	User changeUserCredentials(User user, UserDTO employee,
			String password, String email, String type);
	

}
