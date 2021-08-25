Feature: A Scrum board can be selected after product selection, from which 
				 a user can continue performing actions

	Scenario: A user logs in, selects a product, and then selects a scrum board
		
	Background:
		* def select = call read('Login.feature')
		* def select = call read('ProductSelect.feature')
		
		Given url 'http://localhost:8080/users/products/b7b49fc0-02ca-11ec-a3ea-0800200c9a66'
		And cookie SESSION = signin.sessionCookie
		When method post 
		Then status 200
		And match response contains {product_id : 'b7b49fc0-02ca-11ec-a3ea-0800200c9a66'}
		
		Given url 'http://localhost:8080/users/scrumboards/d7167cb6-bb25-496e-b83a-b7222c9aca4a'
		And cookie SESSION = select.productSessionCookie
		When method post
		Then status 200
		And match response contains {boardid : 'd7167cb6-bb25-496e-b83a-b7222c9aca4a'}