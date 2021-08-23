
Feature: As a product owner I can login, add new products, and add or remove developers from products
Background:
 * def productUuid = function(){ return java.util.UUID.fromString('b7b49fc0-02ca-11ec-a3ea-0800200c9a66')}

@tag1
Scenario: Log in as a product owner
	Given url loginUrl
	And request { username : 'test_username', password : 'test_password', type : "PRODUCT_OWNER" }
	When method POST
	Then status 200
	And match response contains { username : 'test_username', password : 'test_password' }
	And match responseCookies contains { SESSION : '#notnull' }
	And def sessionCookie = responseCookies.SESSION

@tag2
Scenario: As a product owner I can add new product
    Given url baseUrl + '/products'
    And def signin = call read('classpath:login/login.feature')
    And request { }
    And cookie SESSION = signin.sessionCookie
    When method POST
    Then status 200
    #Test passes when logged in 
    
#
#Scenario: As a product owner I can add developers to products
  #Given url 'http://localhost:8080/products/add/'
 #


#Scenario: As a product owner I can remove developers from products
  #Given url 'http://localhost:8080/products/remove/'


