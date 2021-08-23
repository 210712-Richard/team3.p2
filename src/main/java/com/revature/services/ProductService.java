package com.revature.services;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.revature.beans.Product;
import com.revature.beans.User;

import reactor.core.publisher.Mono;


public interface ProductService {


//	Mono<Product> removeProductById(User user, UUID id);


	Mono<Product> addProductById(String username, UUID id);


	//Mono<Product> createNewProduct(Product product);


	/**
	 * @param product
	 * @return
	 * Product owner can add new product
	 */
	Product createNewProduct(UUID id, String productOwner, Map<UUID, String> scrumMasterBoardMap,
			List<UUID> boardIds, List<String> usernames, Map<UUID, String> boardIdNameMap, String productName,
			UUID masterBoardID);


	/**
	 * @param user
	 * @param id
	 * @return
	 * Developer can remove themselves from products by product id
	 */
	//Mono<Product> removeProductById(UUID id);


	/**
	 * @param user
	 * @param id
	 * @return
	 * Developer can remove themselves from products by product id
	 */
	Mono<User> removeProductById(String username, UUID id);

}
