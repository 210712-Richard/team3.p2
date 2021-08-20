Feature: Assign a task to user in a scrum board
Scenario: As a Scrum Master send a patch request to users and receive the user with task
Background:
* def uuid = function(){ return java.util.UUID.fromString('99001ef7-db95-4efe-acd4-740f06c754d7')}

Given url 'http://localhost:8080/scrums/assign/'
And path uuid()
And path 'users', 'test_username'
When method patch
Then status 200
And match response contains { username: 'test_username'}