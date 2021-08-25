#Author: MuckJosh
#Feature: Sprints
#Scenario Outline: create a sprint, assign task to user, remove task from user, delete sprint.
#Background: taskUuid to create uuid for a task.

@tag
Feature: Sprints
Background:
			* def taskUuid = function(){ return java.util.UUID.fromString('')}
			
    
  @tag3
  Scenario: As a Scrum Master send a post request to create a sprint
		
		Given url 'http://localhost:8080/sprints/'
		And def signin = call read('classpath:userFeatures/login.feature')
		And request {startDate:'2021-8-26', endDate:'2021-9-1', startTime:'09:00 AM',
		endTime:'09:00AM'}
		And cookie SESSION = signin.sessionCookie
		When method post
		Then status 200
		And match response contains {}
		
		@Tag3
		Scenario: As a Scrum Master send a patch request to a wrong users and a 404 
		
		Given url 'http://localhost:8080/scrums/assign/'
		And path taskUuid()
		And path 'users', 'bad_test'
		When method patch
		Then status 404
		And karate.log(response)
