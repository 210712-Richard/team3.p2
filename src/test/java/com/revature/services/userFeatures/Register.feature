Feature: As a user, I can REGISTER an account

Scenario: Send a put request and successfully register a new user

Given url baseUrl + "/users/register"
And request {username : 'newUser', email:"newemail@email.com", password : 'coolPassword'}
When method put
Then status 200
And match response contains {username : 'newUser', email:"newemail@email.com", password : 'coolPassword'}