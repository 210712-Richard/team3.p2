package com.revature.services;


import java.util.UUID;

import com.revature.beans.Product;
import com.revature.beans.User;

import reactor.core.publisher.Mono;


public interface ProductService {


	Mono<Product> removeProductById(User user, UUID id);


	Mono<Product> addProductById(User user, UUID id);


	Mono<Product> createNewProduct(Product product);

}
