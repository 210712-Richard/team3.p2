package com.revature.services;

import com.revature.beans.User;

public interface UserService {
	
	User login (String name);
	
	User register(String username, String password, 
			String email,String firstName, String lastName);
	
	User roleChange(User user, String employee, String type);
	
	User viewUser (User user, String employee);
	
	User changeUserCredentials(User user, String employee,
			String password, String email, String firstName, String lastName, String type);
	
}
