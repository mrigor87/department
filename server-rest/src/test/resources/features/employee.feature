Feature: test REST employee
  Background:
    Given REST server has just started

  Scenario: update employee
    When the client updates employee
    Then employee is updated

  Scenario: create new employee
    When the client creates new employee
    Then employee is created
