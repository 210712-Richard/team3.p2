Feature: As an Admin, I can view a users credentials

	Scenario: An Admin makes a request to see user credentials
	
	Background:
		* def signin = call read('AdminLogin.feature')
		
		Given url baseUrl + "/users/test_username"
		And cookie SESSION = signin.adminSessionCookie
		When method get
		Then status 200
		And match response contains {username: "test_username"}
		
	