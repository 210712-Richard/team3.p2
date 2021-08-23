#Author: MuckJosh
#Feature: Assign Tasks
#Scenario Outline: assign task to user, remove task from user
#Background: taskUuid to create uuid for a task.

@tag
Feature: assign and remove tasks from user
Background:
			* def taskUuid = function(){ return java.util.UUID.fromString('99001ef7-db95-4efe-acd4-740f06c754d7')}
  
  @tag1
  Scenario: As a Scrum Master assign a task by sending a patch request to users and receive the user
		
		Given url 'http://localhost:8080/scrums/assign/'
		And path taskUuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}
		
	@Tag1
	Scenario: As a Scrum Master send a patch request to a wrong users and a 404 
		
		Given url 'http://localhost:8080/scrums/assign/'
		And path taskUuid()
		And path 'users', 'bad_test'
		When method patch
		Then status 404
		And karate.log(response)

	@Tag2
	Scenario: As a scrum Master deassign a task from a user with a patch request
	
		Given url 'http://localhost:8080/scrums/remove/'
		And path taskUuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}

#Author Brian

	@Tag3
	Scenario: As a developer move task on the scrumboard with put request
	
		Given url 'http://localhost:8080/tasks/move/6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd/99001ef7-db95-4efe-acd4-740f06c754d7/IN_PROGRESS'
		#And path taskUuid()
		And request { board_id : '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', status: 'COMPLETED', task_id: '99001ef7-db95-4efe-acd4-740f06c754d7'}
		When method patch
		Then status 200
		And match response contains { status: 'COMPLETED'}
		#And match response contains { end_date: '#ignore', start_time: '#ignore', board_id: '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', end_time: '#ignore', name: '#ignore', description: '#ignore', task_id: '99001ef7-db95-4efe-acd4-740f06c754d7', priority: '#ignore', status: 'IN_PROGRESS', start_date: '#ignore' }
		
		
	@Tag3
	Scenario: As a developer move task on the scrumboard with put request
	
		Given url 'http://localhost:8080/tasks/move/6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd/99001ef7-db95-4efe-acd4-740f06c754d7/COMPLETED'
		#And path taskUuid()
		And request { board_id : '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', status: 'IN_PROGRESS', task_id: '99001ef7-db95-4efe-acd4-740f06c754d7'}
		When method patch
		Then status 200
		And match response contains { status: 'IN_PROGRESS'}
		#And match response contains { end_date: '#ignore', start_time: '#ignore', board_id: '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', end_time: '#ignore', name: '#ignore', description: '#ignore', task_id: '99001ef7-db95-4efe-acd4-740f06c754d7', priority: '#ignore', status: 'COMPLETED', start_date: '#ignore' }
	
		
	