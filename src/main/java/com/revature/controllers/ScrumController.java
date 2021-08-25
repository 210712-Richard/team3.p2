package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.ScrumService;

@RestController
@RequestMapping("/scrums")
public class ScrumController {

	private ScrumService scrumService;
	
	@Autowired
	ScrumController(ScrumService scrumService){
		this.scrumService = scrumService;
	}
	
}
