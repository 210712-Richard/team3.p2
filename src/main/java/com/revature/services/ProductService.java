package com.revature.services;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.revature.beans.Product;
import com.revature.beans.User;

import reactor.core.publisher.Mono;

public interface ProductService {
	

Mono<Product> createNewProduct(UUID id, String productOwner, Map<UUID, String> scrumMasterBoardMap,
			List<UUID> boardIds, List<String> usernames, Map<UUID, String> boardIdNameMap, String productName,
			UUID masterBoardID);



Mono<User> addProductById(String username, UUID productId);
	



Mono<User> removeProductById(String username, UUID id);






}
