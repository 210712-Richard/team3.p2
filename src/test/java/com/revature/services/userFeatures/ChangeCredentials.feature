Feature: As an Admin, I can view a users credentials

	Scenario: An Admin changes a users credentials
	
	Background:
		* def signin = call read('Login.feature')
		
		Given url loginUrl + "/test_username/newRole/DEVELOPER"
		And cookie SESSION = signin.adminSessionCookie
		When method post
		Then status 200
		And match response contains {username: "test_username"}
		