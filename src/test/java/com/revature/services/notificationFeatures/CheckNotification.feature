#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Check my notifications as a logged in user

Scenario: Log in as a user
	Given url loginUrl
	And request { username : 'test_username', password : 'test_password' }
	When method POST
	Then status 200
	And match response contains { username : 'test_username', password : 'test_password' }
	And match responseCookies contains { SESSION : '#notnull' }
	And def sessionCookie = responseCookies.SESSION

Scenario: Check notifications
	Given url baseUrl + '/users/notifications'
	And def signin = call read('classpath:com/revature/services/userFeatures/Login.feature')
	And request {}
	And cookie SESSION = signin.sessionCookie
	When method GET
	Then status 200
	And match response contains { username : 'test_username', message : 'this is a test notification', id : '#ignore' }

Scenario: Check a particular notification
	Given url baseUrl + '/users/notifications/cb8632cd-bbe9-4908-a5ba-6510f6b82644'
	And def signin = call read('classpath:com/revature/services/userFeatures/Login.feature')
	And cookie SESSION = signin.sessionCookie
	When method GET
	Then status 200
	And match response contains { username : 'test_username', message : 'this is a test notification', id : 'cb8632cd-bbe9-4908-a5ba-6510f6b82644' }
	
	
	
