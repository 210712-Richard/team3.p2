Feature: Logout a User

Scenario: Send a logout request and logout successfully

Background:
	* def signin = call read('Login.feature')
	
	Given url baseUrl + "/users/logout"
	And cookie SESSION = signin.sessionCookie
	When method delete
	Then status 204

	# lets see if this works