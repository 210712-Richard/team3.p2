Feature: Login as test_username

  Scenario: send a request and login successfully
    Given url loginUrl
    And request {username : 'test_username', password : 'test_password'}
    When method post
    Then status 200
    And match response contains {username: 'test_username', password : 'test_password'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def sessionCookie = responseCookies.SESSION
