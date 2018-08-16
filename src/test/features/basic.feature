Feature: Basic Chat Stuff

  Scenario: I enter echo
    When I enter "echo hallo"
    Then the bot returns "hallo"