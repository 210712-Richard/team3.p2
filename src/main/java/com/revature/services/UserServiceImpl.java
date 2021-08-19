package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.UserDAO;
import com.revature.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService{

	private UserDAO userDao;
	
	@Autowired
	public UserServiceImpl (UserDAO userDao) {
		super();
		this.userDao = userDao;
		
	}
	@Override
	public User login(String name) {
//		UserDTO databaseUser = userDao.findbyId(name).orElse(null);
//		
//		User user = databaseUser.getUser(); //I think we need to add get user in userDTO
//		
//		return user;
		// just setting to null so it doesn't give me an error
		return null;
	}

	@Override
	public User register(String username, String password, String email, String firstName, String lastName) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		
//		userDao.save(new UserDTO(user));
//		uncomment this when DTO is finished
		
		return user;
	}

	@Override
	public User roleChange(User user, String employee, String type) {
		
//		if (user.getType().equals("Admin")) {
//			
//			User emp = userDao.findById(employee);
//			
//			if (!emp.getType().equals("Admin")) {
//				emp.setType(UserType.valueOf(type));
//				
//				userDao.save(new UserDTO(emp)); 
//				return emp;
//				
//			}else {
//				return null;
//			}
//		}else {
//			return null; 
//		}
// 		uncomment this code once the DTO and User DAO are good to go
		
		return null;
	}

	@Override
	public User viewUser(User user, String employee) {
//		if (user.getType().equals("Admin")) {
//			
//			User emp = userDao.findById(employee);
//			return emp; 
//		}else{
//			return null}
		
//		uncomment this code once the DTO and User DAO are good to go
		
		return null;
	}

	@Override
	public User changeUserCredentials(User user, String employee,
			String password, String email, String firstName, String lastName, String type) {
		
//		if (user.getType().equals("Admin")) {
//			
//			User emp = userDao.findById(employee);
//			
//				emp.setFirstName(firstName);
//				emp.setLastName(lastName);
//				emp.setPassword(password);
//				emp.setEmail(email);
//				emp.setType(UserType.valueOf(type));
//				
//				userDao.save(new UserDTO(emp)); 
//				return emp;
//				
//		
//		}else {
//			return null; 
//		}
//		uncomment this code once the DTO and User DAO are good to go
		
		return null;
	}

}
