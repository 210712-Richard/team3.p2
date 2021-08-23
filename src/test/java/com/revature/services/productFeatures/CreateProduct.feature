
Feature: Create new Products
Scenario: As a product owner I can add new products

    Given url 'http://localhost:8080/products/'
    And request { id : '123', username : 'test'}
    When method POST
    Then status 201

