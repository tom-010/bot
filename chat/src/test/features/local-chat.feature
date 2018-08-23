Feature: Local Chat

  Scenario: Echo-Script
    When I create a echo script
    And I enter "echo Thomas"
    Then the bot returns "Thomas"

  Scenario: Message not detected so now answer
    When I create a echo script
    And I enter "Thomas"
    Then no answer comes from the chat

  Scenario: Script registration while chatting
    When I enter "echo Thomas"
    And I create a echo script
    And I enter "echo Thomas"
    Then the bot responded only once