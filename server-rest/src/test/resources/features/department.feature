Feature: test REST department
  Background:
    Given REST server has just started

  Scenario: delete department
    When the client delete existing department
    Then the response has the status OK
    And department is deleted

  Scenario: get all departments
    When the user requests list of departments
    Then the response has the status OK
    And the response body is JSON
    And the response is list of departments

  Scenario: update department
    When the client updates department
    Then department is updated

  Scenario: create new department
    When the client creates new department
    Then department is created
    Then the response contains the created department