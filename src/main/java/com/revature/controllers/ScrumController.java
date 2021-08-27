	package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.aspects.IsProductMaster;
import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.services.ScrumService;
import com.revature.util.WebSessionAttributes;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/scrums")
public class ScrumController {

	private ScrumService scrumService;
	
	@Autowired
	ScrumController(ScrumService scrumService){
		this.scrumService = scrumService;
	}
	@PostMapping()
	@IsProductMaster
	public Mono<ResponseEntity<ScrumBoard>> createScrumBoard(@RequestBody ScrumBoard scrumBoard, WebSession session){
		return scrumService.createScrumBoard((User)session.getAttribute(WebSessionAttributes.LOGGED_USER) , scrumBoard, (Product)session.getAttribute(WebSessionAttributes.SELECTED_PRODUCT))
				.map(board -> ResponseEntity.ok(board))
				.defaultIfEmpty(ResponseEntity.status(404).build());
	}
}
