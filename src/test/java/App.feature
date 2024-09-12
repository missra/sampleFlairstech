Feature: OrangeHRM Demo

  Scenario: Successful login with valid credentials
    Given User is on login page
    When User enters "Admin" and "admin123"
    And User clicks login button
    And User is logged in
    And User clicks on Admin tab
    And User checks the number of records
    And User adds a new user
    Then The number of records should increase by 1
    And search For User added
    Then User deletes the user
    Then The number of records should decrease by 1
