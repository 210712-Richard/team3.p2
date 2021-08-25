package com.revature.controllers;

import java.util.List;
import java.util.Map;
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
import com.revature.beans.UserType;
import com.revature.services.ProductService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<Object> createNewProduct(@RequestBody Product product, WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if (loggedUser == null || !UserType.PRODUCT_OWNER.equals(loggedUser.getType())) {
			return ResponseEntity.status(403).build();
		}
		Product newProduct = productService.createNewProduct(product.getId(), product.getProductOwner(), product.getScrumMasterBoardMap(), product.getBoardIds(), product.getUsernames(), product.getBoardIdNameMap(), product.getProductName(), product.getMasterBoardId());  
				return ResponseEntity.ok(newProduct);

	}

	@PutMapping(value = "/add/{username}/users/{id}/", produces = MediaType.APPLICATION_JSON_VALUE )
	public Mono<ResponseEntity<Product>>addById(@RequestBody Product product,
			@PathVariable("username")String username, @PathVariable("id") UUID id, WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if (loggedUser == null || !UserType.PRODUCT_OWNER.equals(loggedUser.getType())) {
			return Mono.just(ResponseEntity.status(403).build());
		}
		productService.addProductById(username, id);
		return Mono.just(ResponseEntity.ok(product));
	}
	
	@PutMapping(value = "/remove/{id}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<User>> removeById( @PathVariable("id") UUID id, @PathVariable("username") String username) {
	

		return productService.removeProductById(username, id)
				.map( user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
	

}
