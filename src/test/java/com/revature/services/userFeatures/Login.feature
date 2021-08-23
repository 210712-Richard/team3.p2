Feature: Login as test_username

  Scenario: send a request and login successfully
    Given url loginUrl + '/login'
    And request {username : 'test_username', password : 'test_password'}
    When method post
    Then status 200
    And match response contains {username: 'test_username'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def sessionCookie = responseCookies.SESSION

  Scenario: send a bad request and login fails
    Given url loginUrl + '/login'
    And request {username : 'awkjdw', password : 'dfejkfejk'}
    When method post
    Then status 401
  