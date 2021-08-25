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
	Scenario: As a developer move task on the scrumboard with patch request
	
		Given url 'http://localhost:8080/tasks/move/6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd/99001ef7-db95-4efe-acd4-740f06c754d7/IN_PROGRESS'
		#And path taskUuid()
		And request { boardId : '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', status: 'COMPLETED', taskId: '99001ef7-db95-4efe-acd4-740f06c754d7'}
		When method patch
		Then status 200
		And match response contains { status: 'COMPLETED'}		
		
	@Tag3
	Scenario: As a developer move task on the scrumboard with patch request
	
		Given url 'http://localhost:8080/tasks/move/6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd/99001ef7-db95-4efe-acd4-740f06c754d7/COMPLETED'
		#And path taskUuid()
		And request { boardId : '6cbd7dc1-3be6-4dfc-b25e-b0f33ba8c3cd', status: 'IN_PROGRESS', taskId: '99001ef7-db95-4efe-acd4-740f06c754d7'}
		When method patch
		Then status 200
		And match response contains { status: 'IN_PROGRESS'}
	
	@Tag4
	Scenario: As a Produdct Owner I can set priorities to existing backlog tasks
	
		Given url 'http://localhost:8080/tasks/priority/99001ef7-db95-4efe-acd4-740f06c754d7/HIGH'
		And request { masterBoardId : 'd7167cb6-bb25-496e-b83a-b7222c9aca4a' }
		When method patch
		Then status 200
		And match response contains { priorityStatus: 'HIGH' }
		
	