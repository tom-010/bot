Feature: Linux-Commands

  Scenario: I enter ls
    When I am in a folder with the file "hallo.txt"
    When I enter "sh ls"
    Then the bot returns "txt"