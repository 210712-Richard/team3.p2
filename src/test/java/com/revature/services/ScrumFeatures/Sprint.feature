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
@tag
Feature: Sprints

  @tag1
  Scenario: As a scrum master i can create a sprint
    Given I want to write a step with precondition
    And some other precondition
    When I complete action
    And some other action
    And yet another action
    Then I validate the outcomes
    And check more outcomes

  @tag2
  Scenario: As a Scrum Master send a patch request to users and receive the user with task
		Background:
			* def uuid = function(){ return java.util.UUID.fromString('99001ef7-db95-4efe-acd4-740f06c754d7')}

		Given url 'http://localhost:8080/scrums/assign/'
		And path uuid()
		And path 'users', 'test_username'
		When method patch
		Then status 200
		And match response contains { username: 'test_username'}
