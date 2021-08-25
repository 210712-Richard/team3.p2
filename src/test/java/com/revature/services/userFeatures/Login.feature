Feature: Login a User

  Scenario: send a request and login successfully
    Given url loginUrl 
    And request {username : 'test_username', password : 'test_password'}
    When method post
    Then status 200
    And match response contains {username: 'test_username'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def sessionCookie = responseCookies.SESSION

  Scenario: send a bad request and login fails
    Given url loginUrl 
    And request {username : 'awkjdw', password : 'dfejkfejk'}
    When method post
    Then status 401
  
  
  Scenario: Send a request and successfully login as an ADMIN
    Given url loginUrl
    And request {username : 'admin', password : 'admin'}
    When method post
    Then status 200
    And match response contains {username: 'admin'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def adminSessionCookie = responseCookies.SESSION