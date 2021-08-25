Feature: A Scrum board can be selected after product selection, from which 
				 a user can continue performing actions

	Scenario: A user logs in, selects a product, and then selects a scrum board
		
	Background:
		* def signin = call read('Login.feature')	
		
		Given url 'http://localhost:8080/users/products/b7b49fc0-02ca-11ec-a3ea-0800200c9a66'
		And cookie SESSION = signin.sessionCookie
		When method post 
		Then status 200
		And match response contains {product_id : 'b7b49fc0-02ca-11ec-a3ea-0800200c9a66'}