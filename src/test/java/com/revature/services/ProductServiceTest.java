package com.revature.services;


import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.revature.beans.Product;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ProductDTO;
import com.revature.dto.ScrumBoardDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author jasmine
 *
 */
public class ProductServiceTest {
	

	@InjectMocks
	private ProductServiceImpl ps;
	@Mock
	private ProductDAO productDao;
	@Mock
	private UserDAO userDao;
	@Mock
	private TaskDAO taskDao;

	@Mock
	private ScrumBoardDAO scrumDao;
	
	private ProductDTO pDto;
	private Product product;
	private User u;
	private UserDTO uDto;
	private ScrumBoardDTO sDto;
	private TaskDTO tDto;
	
	
	@BeforeEach
	void setupInjects() {
		MockitoAnnotations.openMocks(this);
		u = new User();
        u.setUsername("test");
        uDto = new UserDTO(u);
        product = new Product();
		product.setProductOwner("test");
		product.setProductName("test");
		product.setUsernames(null);
		product.setBoardIdNameMap(null);
		product.setBoardIds(null);
		product.setMasterBoardId(null);
		product.setId(UUID.randomUUID());
		product.setScrumMasterBoardMap(null);
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
	

}
    @Test 
    void testAddNewProduct() {
    Mockito.when(productDao.save(Mockito.any())).thenReturn(Mono.just(pDto));
    ArgumentCaptor<ProductDTO> captor = ArgumentCaptor.forClass(ProductDTO.class);
	ps.createNewProduct(product.getId(), product.getProductOwner(), product.getScrumMasterBoardMap(),
						product.getBoardIds(), product.getUsernames(), product.getBoardIdNameMap(),
						product.getProductName(), product.getMasterBoardId());
	Mockito.verify(productDao).save(captor.capture());


	

}
	
	@Test
	void testAddProductById() {
    Mockito.when(productDao.findByProductid(Mockito.any())).thenReturn(Mono.just(pDto));
    Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
    Mockito.when(ps.addProductById(Mockito.anyString(), product.getId())).thenReturn(Mono.just(u));
    Mono<User> addProduct = ps.addProductById(u.getUsername(), product.getId());
	StepVerifier.create(addProduct).expectNextMatches(p -> p.equals(uDto.getUser()));
	
	}
	
	
	@Test
	void testRemoveProductById() {
	Mockito.when(productDao.findByProductid(Mockito.any())).thenReturn(Mono.just(pDto));
	Mockito.when(userDao.findById(Mockito.anyString())).thenReturn(Mono.just(uDto));
    Mockito.when(ps.addProductById(Mockito.anyString(), product.getId())).thenReturn(Mono.just(u));
	Mono<User> removeProduct = ps.removeProductById(u.getUsername(), product.getId());
	StepVerifier.create(removeProduct).expectNextMatches(p -> p.equals(uDto.getUser()));
}
}	
