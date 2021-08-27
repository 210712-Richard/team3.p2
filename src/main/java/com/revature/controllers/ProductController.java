package com.revature.controllers;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import com.revature.beans.Product;
import com.revature.beans.User;

import com.revature.services.ProductService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public Mono<ResponseEntity<Product>>createNewProduct(@RequestBody Product product, WebSession session) {

		return productService.createNewProduct(product.getId(), product.getProductOwner(), product.getScrumMasterBoardMap(), product.getBoardIds(), product.getUsernames(), product.getBoardIdNameMap(), product.getProductName(), product.getMasterBoardId()) 
		.map( user -> ResponseEntity.ok(product))
		.defaultIfEmpty(ResponseEntity.status(404).build());
				 

	}

	@PutMapping(value = "/add/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE )
	public Mono<ResponseEntity<User>>addById( @PathVariable("id") UUID id,
			@PathVariable("username")String username) {

		return productService.addProductById(username, id)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	
	
	@PutMapping(value = "/remove/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> removeById( @PathVariable("id") UUID id, @PathVariable("username") String username) {
	

		return productService.removeProductById(username, id)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
}