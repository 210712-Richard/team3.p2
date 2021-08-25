Feature: A user can view the scrum boards that they are affiliated with

	Scenario: A user logs in and checks their scrum boards
	
	Given url 'http://localhost:8080/users/scrumboards'
	And def signin = call read('Login.feature')
	And cookie SESSION = signin.sessionCookie
	When method get
	Then status 200
	