package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.Task;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ProductDTO;
import com.revature.dto.ScrumBoardDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserServiceTest {
	private final static Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	@InjectMocks
	private UserService us = new UserServiceImpl();
	@Mock
	private UserDAO userDao;
	@Mock
	private TaskDAO taskDao;
	@Mock
	private ProductDAO productDao;
	@Mock
	private ScrumBoardDAO scrumDao;
	
	private UserDTO uDto;
	private User u;
	private ProductDTO pDto;
	private ScrumBoardDTO sDto;
	private TaskDTO tDto;
	
	@BeforeEach
	public void setupInjects() {
		MockitoAnnotations.openMocks(this);
		u = new User();
		u.setEmail("test");
		u.setPassword("test");
		u.setUsername("test");
		uDto = new UserDTO(u);
		pDto = new ProductDTO();
		pDto.setId(UUID.randomUUID());
		pDto.setMasterBoardId(UUID.randomUUID());
		pDto.setProductName(u.getUsername());
		pDto.setProductOwner(u.getUsername());
		u.getProductIds().add(pDto.getId());
		sDto = new ScrumBoardDTO();
		sDto.setBoardId(UUID.randomUUID());
		sDto.setName("testName");
		sDto.setProductId(pDto.getId());
		sDto.setScrumMaster(u.getUsername());
		u.getBoardIds().add(sDto.getBoardId());
		tDto = new TaskDTO();
		tDto.setBoardid(sDto.getBoardId());
		tDto.setId(UUID.randomUUID());
		u.getTaskIds().add(tDto.getId());
		
	}
	
	@Test 
	public void testRegister() {
		Mockito.when(userDao.save(Mockito.any())).thenReturn(Mono.just(uDto));
		ArgumentCaptor<UserDTO> captor = ArgumentCaptor.forClass(UserDTO.class);
		
		us.register(u);
		
		Mockito.verify(userDao).save(captor.capture());
		
		UserDTO cap = captor.getValue();
		
		assertEquals(cap.getUser(), u, "users should match");
		
	}
	@Test
	public void testLogin() {
		Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
		
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		
		Mono<User> monoUser = us.login(u.getUsername(), u.getPassword());
		
		StepVerifier.create(monoUser).expectNextMatches(user -> user.equals(u)).verifyComplete();
		
		Mockito.verify(userDao).findById(captor.capture());
		
		String str = captor.getValue();
		
		assertEquals(str , u.getUsername(), "usernames should match");
		
	}
	@Test
	public void viewUser() {
		Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
		
		Mono<User> monoUser = us.viewUser(u, u.getUsername());
		
		StepVerifier.create(monoUser).expectNextMatches(user -> user.equals(u)).verifyComplete();
		
		Mockito.verify(userDao).findById(Mockito.anyString());

	}
	
	@Test
	public void viewProducts() {
		Mockito.when(productDao.findByProductid(Mockito.any())).thenReturn(Mono.just(pDto));
		
		Flux<Product> products = us.viewProducts(u);
		
		StepVerifier.create(products).expectNextMatches(p -> p.equals(pDto.getProduct()));
	}
	@Test
	public void selectProduct() {
		Mockito.when(productDao.findByProductid(Mockito.any())).thenReturn(Mono.just(pDto));
		
		Mono<Product> product = us.selectProduct(u, pDto.getId());
		
		StepVerifier.create(product).expectNextMatches(p -> p.equals(pDto.getProduct()));
	}
	@Test
	public void viewBoards() {
		Mockito.when(scrumDao.findAll()).thenReturn(Flux.just(sDto));
		
		Flux<ScrumBoard> boards = us.viewScrumBoards(u);
		
		StepVerifier.create(boards).expectNextMatches(p -> p.equals(sDto.getScrumBoard()));
	}
	@Test
	public void selectScrumBoard() {
		Mockito.when(scrumDao.findByBoardid(Mockito.any())).thenReturn(Mono.just(sDto));
		
		Mono<ScrumBoard> board = us.selectScrumBoard(u, pDto.getProduct() ,sDto.getBoardId());
		
		StepVerifier.create(board).expectNextMatches(p -> p.equals(sDto.getScrumBoard()));
	}
	@Test
	public void viewTasks() {
		Mockito.when(taskDao.findAll()).thenReturn(Flux.just(tDto));
		
		Flux<Task> tasks = us.viewTasks(u);
		
		StepVerifier.create(tasks).expectNextMatches(p -> p.equals(tDto.getTask()));
	}
}
