package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.beans.Product;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.dto.ProductDTO;

import reactor.core.publisher.Mono;

/**
 * 
 * @author jasmine
 *
 */


@Service
public class ProductServiceImpl implements ProductService {
	private ProductDAO productDao;
	

	
	@Autowired
	public ProductServiceImpl(ProductDAO productDao) {
		super();
		this.productDao = productDao;
	}
	
	/**
	 * @param product
	 * @return
	 * Product owner can add new product
	 */
	@Override
	public Mono<Product> createNewProduct(Product product) {
		return productDao.save(new ProductDTO(product))
				.map((dto) -> dto.getProduct());
		}
	
	
	
	/**
	 * @param user
	 * @param id
	 * @return
	 * Developer can add themselves to products by product id
	 */
	@Override
	public Mono<Product> addProductById (User user, UUID id) {
		return productDao.findById(id)
				.map(dto -> {
		        (user.getProductIds()).add(id);
			    productDao.save(dto);
			    return dto.getProduct();
		    });
	     }
		

	
	/**
	 * @param user
	 * @param id
	 * @return
	 * Developer can remove themselves from products by product id
	 */
	@Override
	public Mono<Product> removeProductById(User user, UUID id) {
		return productDao.findById(id)
				.map(dto -> {
			    (user.getProductIds()).remove(id);
				productDao.save(dto);
				return dto.getProduct();
			});
		}
	}
