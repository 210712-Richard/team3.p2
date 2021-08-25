Feature: A user can view the products that they are affiliated with

	Scenario: A user logs in and checks their products
	
	Given url 'http://localhost:8080/users/products'
	And def signin = call read('Login.feature')
	And cookie SESSION = signin.sessionCookie
	When method get
	Then status 200
	