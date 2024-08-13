Feature: Login Functionality

  Background:
    Given user is on login page "https://www.saucedemo.com"

@login
    Scenario: Positive test case
       Then user enters "standard_user" for username
      Then user enters "secret_sauce" for password
      Then user clicks on login button