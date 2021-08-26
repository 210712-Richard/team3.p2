package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.revature.aspects.IsScrumMaster;
import com.revature.beans.Sprint;
import com.revature.services.SprintService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sprints")
public class SprintController {

	private SprintService sprintService;

	@Autowired
	public SprintController(SprintService sprintService) {
		super();
		this.sprintService = sprintService;
	}
	@PostMapping()
	@IsScrumMaster
	public Mono<ResponseEntity<Sprint>> createSprint(@RequestBody Sprint sprint, WebSession session){
		sprint.setScrumboardID(session.getAttribute("selectedScrumBoard"));
		return sprintService.createSpring(sprint)
			.map( s -> ResponseEntity.ok(s))
			.switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));
	}
	@PatchMapping("/endCurrentSprint")
	@IsScrumMaster
	public Mono<ResponseEntity<Sprint>> retireCurrentSprint(WebSession session){
		return sprintService.retireCurrentSprint(session.getAttribute("selectedScrumBoard"))
				.map(s -> ResponseEntity.ok(s))
				.switchIfEmpty(Mono.just(ResponseEntity.status(401).build()));
	}
}
