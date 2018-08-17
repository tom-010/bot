Feature: Basic Chat Stuff

  Scenario: I enter echo
    When I enter "echo hello"
    Then the bot returns "hello"