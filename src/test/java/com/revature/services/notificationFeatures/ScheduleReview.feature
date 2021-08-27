Feature: Schedule a review as a scrum master

	Scenario: Scrum master logs in, selects a product, selects a board, and schedules a review
		Given url baseUrl + '/users/schedulereview'
		And def scrumSelect = call read('classpath:com/revature/services/userFeatures/SelectScrumBoard.feature')
		And request {message : "Testing if scrum review works"}
		And cookie SESSION = scrumSelect.productSelect.signin.sessionCookie
		When method post
		Then status 200