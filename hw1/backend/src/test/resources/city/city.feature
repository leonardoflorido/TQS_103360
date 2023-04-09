Feature: City

  Scenario: Air Pollution
    Given the user is on the homepage
    When the user searches air pollution for "Porto"
    And the user selects "Portugal" as country
    And the user clicks on search button
    Then the user should see the air pollution for "Porto"

    Given the user is on the cachepage
    Then the user should see the air pollution for "Porto" in the cache
