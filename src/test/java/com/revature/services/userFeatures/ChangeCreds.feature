Feature: As an Admin, I can CHANGE a users credentials

	Scenario: An Admin makes a request to change user credentials
	
	Background:
		* def signin = call read('Login.feature')
		
		Given url baseUrl + "/users/newCreds/"
		And request {username: "Ellis", password: "newPass", email: "ellisd@gmail.com"}
		And cookie SESSION = signin.adminSessionCookie
		When method put
		Then status 200
		And match response contains {username: "Ellis"}
		
	