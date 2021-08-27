Feature: Schedule a build presentation as a product owner

	Scenario: Product owner logs in, selects a product, and schedules a presentation
		Given url baseUrl + '/users/buildpresentation'
		And def productSelect = call read('classpath:com/revature/services/userFeatures/ProductSelect.feature')
		And request {message : "Testing if product presentation works"}
		And cookie SESSION = productSelect.signin.sessionCookie
		When method post
		Then status 200
		