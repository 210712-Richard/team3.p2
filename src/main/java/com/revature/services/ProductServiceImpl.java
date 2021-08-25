package com.revature.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.beans.Product;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.UserDAO;
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
	private UserDAO userDAO;
	private User user;
	

	
	@Autowired
	public ProductServiceImpl(ProductDAO productDao) {
		super();
		this.productDao = productDao;
	}
	
	/**
	 * @param product
	 */

	@Override
	public Product createNewProduct(UUID id, String productOwner, Map<UUID, String> scrumMasterBoardMap, List<UUID> boardIds,
			List<String> usernames, Map<UUID, String> boardIdNameMap, String productName, UUID masterBoardID) {
		Product product = new Product();
		product.setId(id);
		product.setProductOwner(productOwner);
		product.setScrumMasterBoardMap(scrumMasterBoardMap);
		product.setBoardIds(boardIds);
		product.setUsernames(usernames);
		product.setBoardIdNameMap(boardIdNameMap);
		product.setProductName(productName);
		product.setMasterBoardId(masterBoardID);
	    productDao.save(new ProductDTO(product));
	    return product;
		
		
	}

	
	
	
	/**
	 * @param user
	 * @param id
	 * @return
	 * User can add themselves to products by product id
	 */
//	@Override
//	public Mono<Product> addProductById (User user, UUID id) {
//		return productDao.findById(id)
//				.map(dto -> {
//		        (user.getProductIds()).add(id);
//			    productDao.save(dto);
//			    return dto.getProduct();
//		    });
//	     }
		

	
	/**
	 * @param user
	 * @param id
	 * @return
	 * Developer can remove themselves from products by product id
	 */


	@Override
	public Mono<User> removeProductById(String username, UUID id) {
		return userDAO.findById(username)
				.map(dto -> {
			dto.getProductIds()
			.removeIf(p -> p.equals(id));
			return dto.getUser();
			
		});
				
				
//				productDao.findById(user.getUsername(),id)
//				.map(dto -> {
//			    (user.getProductIds()).remove(id);
//				productDao.save(dto);
//				return dto.getProduct();
//			});
		}

	@Override
	public Mono<Product> addProductById(String username, UUID id) {
		return productDao.findById(id)
				.map(dto -> {
		        (user.getProductIds()).add(id);
			    productDao.save(dto);
			    return dto.getProduct();
		    });
	     }
		
	}


	
