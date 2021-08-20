package com.revature.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.UserDAO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{

	private UserDAO userDao;
	
	@Autowired
	public UserServiceImpl (UserDAO userDao) {
		super();
		this.userDao = userDao;
		
	}
	@Override
	public Mono<User> login(String name) {
		Mono<User> userMono = userDao.findById(name).map(user -> user.getUser());
		
		
		return userMono;
	}

	@Override
	public User register(String username, String password, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		userDao.save(new UserDTO(user));

//		uncomment this when DTO is finished
		
		return user;
	}

	@Override
	public void roleChange(User user, User employee, String type) {
		
		if (user.getType().equals(UserType.valueOf("Admin"))) {
			
			employee.setType(UserType.valueOf(type));
			
			
		}else {
			return; 
		}
		
	}

	@Override
	public Mono<UserDTO> viewUser(User user, String employee) {
		if (user.getType().equals(UserType.valueOf("Admin"))) {
			
			Mono<UserDTO> emp = userDao.findById(employee);
			
			emp.subscribe();
			return emp;
			
			
		}else{
			return null;
			}
	}

	@Override
	public User changeUserCredentials(User user, User employee,
			String password, String email, String type) {
		
		if (user.getType().equals(UserType.valueOf("Admin"))) {
		
		employee.setType(UserType.valueOf(type));
		employee.setPassword(password);
		employee.setEmail(email);
		
					}else {
						return null; 
					}
		
		return null;
	}

}
