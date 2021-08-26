#Author: Jasmine
Feature: User can view their tasks

 Scenario: After user logs in they can view the tasks they are associated with
    Given  url 'http://localhost:8080/users/tasks'
   	And def signin = call read('Login.feature')
	  And cookie SESSION = signin.sessionCookie
	  When method GET
	  Then status 200

