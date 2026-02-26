	Feature: Heroku Internet App - Login
  This demo validates Form Authentication using Cucumber + Selenium and produces HTML reports.

  @smoke
  Scenario: Valid login should PASS
    Given I open the Heroku Internet App login page
    When I login with username "tomsmith" and password "SuperSecretPassword!"
    Then I should see a flash message containing "You logged into a secure area!"

  @rerun
  Scenario: Invalid login should FAIL (intentional for rerun demo)
    Given I open the Heroku Internet App login page
    When I login with username "tomsmith" and password "wrongPassword"
    Then I should see a flash message containing "Your password is invalid!"  