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
import com.revature.beans.Sprint;
import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ProductDTO;
import com.revature.dto.ScrumBoardDTO;
import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TaskServiceTest {
	private final static Logger log = LoggerFactory.getLogger(TaskServiceTest.class);
	@InjectMocks
	private TaskService ts = new TaskServiceImpl();
	@Mock
	private UserDAO userDao;
	@Mock
	private TaskDAO taskDao;
	@Mock
	private ProductDAO productDao;
	@Mock
	private ScrumBoardDAO scrumDao;
	@Mock
	private SprintDAO sprintDao;
	
	private UserDTO uDto;
	private User u;
	private ProductDTO pDto;
	private ScrumBoardDTO sDto;
	private TaskDTO tDto;
	private SprintDTO spDto;
	
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
		pDto.setMasterBoardID(UUID.randomUUID());
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
		
		Sprint sprint = new Sprint();
		spDto = new SprintDTO(sprint);
		
		
		
	}
	
	@Test
	public void testMoveTask() {
		Mockito.when(taskDao.findByBoardidAndStatusAndId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(tDto));
		
		TaskCompletionStatus newStatus = TaskCompletionStatus.COMPLETED;
		Mono<Task> task = ts.moveTask(sDto.getBoardId(), tDto.getId(), tDto.getStatus(), newStatus);
		
		StepVerifier.create(task).expectNextMatches(p -> p.equals(tDto.getTask()));
	}
	
	@Test
	public void testAddToProductBackLog() {
		Mockito.when(productDao.findByProductid(Mockito.any()).thenReturn(Mono.just(pDto)));
	
		Mono<Task> task = ts.addToProductBackLog(pDto.getId(), tDto);
		
		StepVerifier.create(task).expectNextMatches(t -> t.equals(tDto.getTask()));
		
	}
	
	@Test
	public void testMakePriority() {
		Mockito.when(taskDao.findByBoardidAndStatusAndId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(tDto));
		
		TaskPriority newStatus = TaskPriority.HIGH;
		Mono<Task> task = ts.makePriority(tDto.getBoardid(), tDto.getId(), newStatus);
		
		StepVerifier.create(task).expectNextMatches(p -> p.equals(tDto.getTask()));
	}
	
	@Test
	public void testAddToSprintBackLog(){
		Mockito.when(taskDao.findByBoardidAndStatusAndId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(tDto));
		
		Mockito.when(sprintDao.findByScrumboardIDAndStatus(Mockito.any(), Mockito.any())).thenReturn(Mono.just(spDto));
		
		Mono<Sprint> sprint = ts.addToSprintBackLog(spDto.getScrumboardID(), spDto.getStatus(), tDto.getBoardid(), tDto.getStatus(), tDto.getId());

		StepVerifier.create(sprint).expectNextMatches(p -> p.equals(spDto.getSprint()));
	}
	
	@Test
	public void testAssignTasks() {
		Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
		
		Mono<User> user = ts.assignTasks(tDto.getId(), uDto.getUsername());
		
		StepVerifier.create(user).expectNextMatches(p -> p.equals(uDto.getUser()));
		
	}
	
	@Test
	public void testRemoveTasks() {
		Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
		
		Mono<User> user = ts.assignTasks(tDto.getId(), uDto.getUsername());
		
		StepVerifier.create(user).expectNextMatches(p -> p.equals(uDto.getUser()));
	}
	
}
