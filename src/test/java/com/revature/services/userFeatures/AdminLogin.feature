Feature: Login a User

  Scenario: send a request and login successfully
    Given url loginUrl 
    And request {username : 'admin', password : 'admin'}
    When method post
    Then status 200
    And match response contains {username: 'admin'}
    And match responseCookies contains {SESSION: '#notnull'}
    And def adminSessionCookie = responseCookies.SESSION

