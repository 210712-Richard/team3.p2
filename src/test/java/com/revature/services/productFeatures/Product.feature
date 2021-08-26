
Feature: As a product owner I can add new products, and add or remove developers from products
Background:
 * def productId = function(){ return java.util.UUID.fromString('b7b49fc0-02ca-11ec-a3ea-0800200c9a66')}


Scenario: As a product owner I can add new product
    Given url 'http://localhost:8080/products/'
    And request { }
    When method POST
    Then status 200


Scenario: As a product owner I can add developers to products
  Given url 'http://localhost:8080/products/add/'
  And path productId()
  And path  'users', 'test_username'
  When method PUT
  Then status 200
  
  
  


Scenario: As a product owner I can remove developers from products
  Given url 'http://localhost:8080/products/remove/'
  And path productId()
  And path  'users', 'test_username'
  When method PUT
  Then status 200
