Feature: Assign a task to user in a scrum board
Scenario: As a Scrum Master send a patch request to users and receive the user with task

Given url 'http://localhost:8080/scrums/assign/id/users/username'
And request { id : '123', username : 'test'}
When method patch
Then status 200

