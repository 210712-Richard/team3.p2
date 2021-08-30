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

import com.revature.beans.Notification;
import com.revature.beans.User;
import com.revature.data.NotificationDAO;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.NotificationDTO;
import com.revature.dto.ProductDTO;
import com.revature.dto.ScrumBoardDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class NotificationServiceTest {
	private final static Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	@InjectMocks
	private NotificationServiceImpl ns;
	@Mock
	private NotificationDAO notificationDao;
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
	private NotificationDTO nDto;
	private Notification n;
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
		n = new Notification();
		n.setId(UUID.randomUUID());
		n.setMessage("test");
		n.setUsername("test");
		nDto = new NotificationDTO(n);
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
	public void testNotify() {
		Mockito.when(notificationDao.save(Mockito.any())).thenReturn(Mono.just(nDto));
		ArgumentCaptor<NotificationDTO> captor = ArgumentCaptor.forClass(NotificationDTO.class);
		
		ns.notify(n.getUsername(), n.getMessage());
		
		Mockito.verify(notificationDao).save(captor.capture());
		
		NotificationDTO cap = captor.getValue();
		
		assertEquals(cap.getNotification().getMessage(), n.getMessage(), "notification messages should be identical");
		assertEquals(cap.getNotification().getUsername(), n.getUsername(), "notification usernames should be identical");
	}
	
	@Test
	public void testCheckNotifications() {
		Mockito.when(notificationDao.findByUsername(Mockito.anyString())).thenReturn(Flux.just(nDto));

		Flux<Notification> fluxNotes = ns.checkNotifications(u);
		
		StepVerifier.create(fluxNotes).expectNextMatches(note -> note.equals(n)).verifyComplete();
		
		Mockito.verify(notificationDao).findByUsername(Mockito.anyString());
	}

	@Test
	public void testCheckNotificationByID() {
		Mockito.when(notificationDao.findByUsernameAndId(Mockito.any(), Mockito.any())).thenReturn(Mono.just(nDto));
		
		Mono<Notification> monoNote = ns.checkNotificationByID(u, n.getId());
		
		StepVerifier.create(monoNote).expectNextMatches(note -> note.equals(n)).verifyComplete();
		
		Mockito.verify(notificationDao).findByUsernameAndId(Mockito.any(), Mockito.any());
	}
	
}
