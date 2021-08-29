Feature: Notifications can be cleared if the user so desires

	Background:
	* def signin = call read('classpath:com/revature/services/userFeatures/Login.feature')

	Scenario: A user logs in and clears their notifications 
	
	Given url baseUrl + '/users/notifications/clear'
	And cookie SESSION = signin.sessionCookie
	When method DELETE
	Then status 200
	
	Scenario: A user logs in and checks their notifications after clearing
	
	Given url baseUrl + '/users/notifications'
	And cookie SESSION = signin.sessionCookie
	When method GET
	Then status 200
	And match response == []
	
	Scenario: A user logs in and sends itself a notification (to restore database state)
	
	Given url baseUrl + '/users/notify'
	And request {username : 'test_username', message : 'this is a test notification'}
	And cookie SESSION = signin.sessionCookie
	When method POST
	Then status 200
	And match response contains 'Your notification was sent to test_username'
	
	