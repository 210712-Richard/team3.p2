package com.revature.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Mono<ResponseEntity<Object>> createNewProduct(@RequestBody Product product, WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if (loggedUser == null || !UserType.PRODUCT_OWNER.equals(loggedUser.getType())) {
			return Mono.just(ResponseEntity.status(403).build());
		}
		return Mono.just(productService.createNewProduct(product)).map((p)-> {
			if(p == null) {
				return ResponseEntity.status(409).build();
			} else {
				return ResponseEntity.ok(p);
			}
		});
	}

	@PutMapping("/{username}/{id}/add/")
	public ResponseEntity<Product> addById(@RequestBody Product product,
			@PathVariable("username") User user, @PathVariable("id") UUID id, WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if (loggedUser == null || !UserType.DEVELOPER.equals(loggedUser.getType())) {
			return ResponseEntity.status(403).build();
		}
		productService.addProductById(loggedUser, id);
		return ResponseEntity.ok(product);
	}
	
	@PutMapping("/{username}/{id}/remove/")
	public ResponseEntity<Product> removeById(@RequestBody Product product,
			@PathVariable("username") User user, @PathVariable("id") UUID id, WebSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if (loggedUser == null || !UserType.DEVELOPER.equals(loggedUser.getType())) {
			return ResponseEntity.status(403).build();
		}

		productService.removeProductById(loggedUser, id);
		return ResponseEntity.ok(product);
	}
	

}
