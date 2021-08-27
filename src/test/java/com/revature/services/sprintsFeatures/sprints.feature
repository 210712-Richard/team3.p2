#Author: MuckJosh
#Feature: Sprints
#Scenario Outline: create a sprint, assign task to user, remove task from user, delete sprint.
Feature: sprints
    
  @tag1
  Scenario: As a Scrum Master send a post request to create a sprint
		
		Given url 'http://localhost:8080/sprints/'
		And def signin = call read('classpath:com/revature/services/userFeatures/SelectScrumBoard.feature')
		And request {startDate:'2021-08-26', endDate:'2021-09-01', status:'CURRENT'}
		And cookie SESSION = signin.sessionCookie
		When method post
		Then status 200
		And match response contains { status : 'current'}
		
	@Tag2
	Scenario: As a Scrum Master send a patch request to end a sprint
		
		Given url 'http://localhost:8080/sprints/endCurrentSprint'
		When method patch
		Then status 200
		And match response contains { status : 'PAST' }

	#@Tag2
#	Scenario: As a Scrum Master send a patch request to end a sprint
		
	#	Given url 'http://localhost:8080/sprints/d0230d19-96bf-4a9d-bcd8-ae8e107be26b'
	#	When method patch
	#	Then status 200
	#	And match response contains { status : 'PAST' }	