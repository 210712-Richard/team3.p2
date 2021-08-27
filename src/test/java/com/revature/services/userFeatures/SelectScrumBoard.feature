Feature: A Scrum board can be selected after product selection, from which 
				 a user can continue performing actions

	Scenario: A user logs in, selects a product, and then selects a scrum board
		
		Given url 'http://localhost:8080/users/scrumboards/d7167cb6-bb25-496e-b83a-b7222c9aca4a'
		And def productSelect = call read('classpath:com/revature/services/userFeatures/ProductSelect.feature')
		And cookie SESSION = productSelect.signin.sessionCookie
		When method post
		Then status 200
		And match response contains {id : 'd7167cb6-bb25-496e-b83a-b7222c9aca4a'}