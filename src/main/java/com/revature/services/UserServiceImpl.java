package com.revature.services;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Task;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class UserServiceImpl implements UserService{

	private UserDAO userDao;
	private TaskDAO taskDao;
	
	@Autowired
	public UserServiceImpl (UserDAO userDao, TaskDAO taskDao) {
		super();
		this.userDao = userDao;
		this.taskDao = taskDao;
		
	}
	@Override
	public Mono<User> login(String name) {
		Mono<User> userMono = userDao.findById(name).map(user -> user.getUser());
		
		Mono<List<Task>> tasklist = Flux.from(userDao.findById(name))
				.map(user -> user.getTaskIds())
				.flatMap(l -> Flux.fromIterable(l))
				.flatMap(uuid -> taskDao.findByUUID(uuid))
				.map(task -> task.getTask())
				.collectList();
		
		Mono<Tuple2<List<Task>, User>> userBuild = tasklist.zipWith(userMono);
		
		Mono<User> returnedUser = userBuild.map(tuple -> {
			User user = tuple.getT2();
			List<Task> t = tuple.getT1();
			List<UUID> taskId = t.stream()
								.map(task -> task.getId())
								.toList(); 
			user.setTaskIds(taskId);
			return user;
		});
		return returnedUser;
	}

	@Override
	public User register(String username, String password, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		userDao.save(new UserDTO(user));
		
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
