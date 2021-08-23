package com.revature.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class UserServiceImpl implements UserService {

	private UserDAO userDao;
	private TaskDAO taskDao;

	@Autowired
	public UserServiceImpl(UserDAO userDao, TaskDAO taskDao) {
		super();
		this.userDao = userDao;
		this.taskDao = taskDao;
	}

	@Override
	public Mono<User> login(String username, String password) {
		Mono<UserDTO> userData = userDao.findById(username);
		System.out.println(userData);
		return userData
				.filter(dto -> dto.getPassword().equals(password))
				.map(dto -> {
					System.out.println(dto);
					return dto.getUser();
				});
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

		} else {
			return;
		}

	}

	@Override
	public Mono<UserDTO> viewUser(User user, String employee) {
		if (user.getType().equals(UserType.valueOf("Admin"))) {

			Mono<UserDTO> emp = userDao.findById(employee);

			emp.subscribe();
			return emp;

		} else {
			return null;
		}
	}

	@Override
	public User changeUserCredentials(User user, User employee, String password, String email, String type) {

		if (user.getType().equals(UserType.valueOf("Admin"))) {

			employee.setType(UserType.valueOf(type));
			employee.setPassword(password);
			employee.setEmail(email);

		} else {
			return null;
		}

		return null;
	}

}
