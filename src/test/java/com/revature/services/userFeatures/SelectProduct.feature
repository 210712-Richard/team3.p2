Feature: A Product can be selected after login, from which a user can continue performing actions

	Scenario: A developer logs in and selects a product
		
	Background:
		* def signin = call read('classpath:Login.feature')	
		
		Given url loginUrl + '/products/b7b49fc0-02ca-11ec-a3ea-0800200c9a66'
		And cookie SESSION = signin.sessionCookie
		When method post 
		Then status 200
		And match response contains {product_id : 'b7b49fc0-02ca-11ec-a3ea-0800200c9a66'}
