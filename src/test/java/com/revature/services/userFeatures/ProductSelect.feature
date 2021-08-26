Feature: A Product can be selected after login, from which a user can continue performing actions

	Scenario: A user logs in and selects a product
		
		Given url 'http://localhost:8080/users/products/b7b49fc0-02ca-11ec-a3ea-0800200c9a66'
		And def signin = call read('Login.feature')
		And cookie SESSION = signin.sessionCookie
		When method post 
		Then status 200
		And match response contains {id : 'b7b49fc0-02ca-11ec-a3ea-0800200c9a66'}
		And def productid = response.id