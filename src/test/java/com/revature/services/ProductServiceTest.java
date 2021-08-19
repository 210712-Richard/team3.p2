package com.revature.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import com.revature.beans.Product;
import com.revature.data.ProductDAO;
import com.revature.dto.ProductDTO;

public class ProductServiceTest {
	
	private static ProductServiceImpl service;
	private static ProductDAO pd;
	private static Product product;
	
	@BeforeAll
	public static void setUpClass() {
		product = new Product();
		
	}
	@BeforeEach
	public void setUpTest() {
		pd = Mockito.mock(ProductDAO.class);
		service = new ProductServiceImpl(pd);
	}
	
	@Test
	public void testNewProduct() {
		service.addProduct(product);
		ArgumentCaptor<ProductDTO> captor = ArgumentCaptor.forClass(ProductDTO.class);
		
		Mockito.verify(pd).save(captor.capture());
	}
	

}
