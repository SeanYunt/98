Feature: Basic Game


  Scenario: Can start game
    Given I go to the landing page
    And I click the play button
    Then I am at the new game page
    And I click the start game button
    Then I am at the game in progress page

  Scenario: first move (3,0)
    Then I am at the game in progress page
    And eventually the third play button is visible
    Then the player clicks the 1st play button
    Then there is an "X" in position 3,0
    Then I click the reset button