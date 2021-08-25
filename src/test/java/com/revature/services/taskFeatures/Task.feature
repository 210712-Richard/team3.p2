#Author: MuckJosh
#Feature: Assign Tasks
#Scenario Outline: assign task to user, remove task from user
#Background: taskUuid to create uuid for a task.

@tag
Feature: assign and remove tasks from user
Background:
			* def taskUuid = function(){ return java.util.UUID.fromString('99001ef7-db95-4efe-acd4-740f06c754d7')}

Scenario: send a request and login successfully
    Given url loginUrl + '/login'
    And request {username : 'test_username', password : 'test_password'}
    When method post
    Then status 200
    And match response contains {username: 'test_username'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def sessionCookie = responseCookies.SESSION

  @tag1
  Scenario: As a Scrum Master assign a task by sending a patch request to users and receive the user
		
		Given url 'http://localhost:8080/tasks/assign/'
		And path taskUuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}
		
	@Tag1
	Scenario: As a Scrum Master send a patch request to a wrong users and a 404 
		
		Given url 'http://localhost:8080/tasks/assign/'
		And path taskUuid()
		And path 'users', 'bad_test'
		When method patch
		Then status 404
		And karate.log(response)

	@Tag2
	Scenario: As a scrum Master deassign a task from a user with a patch request
	
		Given url 'http://localhost:8080/tasks/remove/'
		And path taskUuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}