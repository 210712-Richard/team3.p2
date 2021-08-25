#Author: MuckJosh
#Feature: Sprints
#Scenario Outline: create a sprint, assign task to user, remove task from user, delete sprint.
#Background: taskUuid to create uuid for a task.

@tag
Feature: CRUD a Sprint
Background:
			* def taskUuid = function(){ return java.util.UUID.fromString('99001ef7-db95-4efe-acd4-740f06c754d7')}

  @tag1
  Scenario: As a Scrum Master I can create a sprint
		
		Given url 'http://localhost:8080/sprints/assign/'
		And path taskUuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}
		
		@Tag3
		Scenario: As a Scrum Master send a patch request to a wrong users and a 404 
		
		Given url 'http://localhost:8080/scrums/assign/'
		And path taskUuid()
		And path 'users', 'bad_test'
		When method patch
		Then status 404
		And karate.log(response)
