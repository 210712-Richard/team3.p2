package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ProductDTO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private UserDAO userDao;
	private TaskDAO taskDao;
	private ProductDAO productDao;
	private ScrumBoardDAO scrumDao;

	@Autowired
	public UserServiceImpl(UserDAO userDao, TaskDAO taskDao, ProductDAO productDao, ScrumBoardDAO scrumDao) {
		super();
		this.userDao = userDao;
		this.taskDao = taskDao;
		this.productDao = productDao;
		this.scrumDao = scrumDao;
	}

	@Override
	public Mono<User> login(String username, String password) {
		Mono<UserDTO> userData = userDao.findById(username);
		System.out.println(userData);
		return userData.filter(dto -> dto.getPassword().equals(password)).map(dto -> {
			System.out.println(dto);
			return dto.getUser();
		});
	}

	@Override
	public Mono<User> register(User user) {

		return userDao.save(new UserDTO(user)).map(u->u.getUser());

	}

	@Override
	public User roleChange(User user, User employee, String type) {

		if (user.getType().equals(UserType.valueOf("Admin"))) {

			employee.setType(UserType.valueOf(type));

			return employee;
		} else {
			return null;
		}

	}

	@Override
	public Mono<User> viewUser(User user, String employee) {
		

			Mono<UserDTO> emp = userDao.findById(employee);
			return emp
					.map(dto -> {
						return dto.getUser();
					});
	
	}

	@Override
	public void changeUserCredentials(User user, UserDTO employee, String password, String email, String type) {

		if (user.getType().equals(UserType.valueOf("Admin"))) {

			employee.setType(UserType.valueOf(type));
			employee.setPassword(password);
			employee.setEmail(email);

		} 

		
	}


	@Override
	public Flux<Product> viewProducts(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Product> selectProduct(User user, UUID productId) {
		Mono<ProductDTO> productData = productDao.findById(productId);
		return productData
				.flatMap(dto -> {
					if (dto.getUsernames().contains(user.getUsername())) {
						return Mono.just(dto.getProduct());
					} else {
						return Mono.empty();
					}
				});
	}

	@Override
	public Flux<ScrumBoard> viewScrumBoards(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ScrumBoard> selectScrumBoard(User user, UUID boardId) {
		// TODO Auto-generated method stub
		return null;
	}


}
