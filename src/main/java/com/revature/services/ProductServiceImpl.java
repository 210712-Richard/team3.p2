package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Product;
import com.revature.data.ProductDao;
import com.revature.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {
	private ProductDao productDao;
	
	@Autowired
	public ProductServiceImpl(ProductDao productDao) {
		super();
		this.productDao = productDao;
		
	}
	
    //Product owner can add new product
	@Override
	public Product addProduct (Product product) {
		productDao.save(new ProductDTO(product));
		return product;
	}
	
	
	//Should be able to have multiple projects running at once, 
	// ??
	
	
	
    //and developers can be part of multiple projects at the same time
	
	
	
}
