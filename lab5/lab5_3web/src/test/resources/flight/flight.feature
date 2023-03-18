Feature: Flight

  Scenario: Buy a ticket
    Given the user is on the homepage
    When the user selects "Boston" from the "from" dropdown
    When the user selects "Dublin" from the "to" dropdown
    And the user clicks the "Find Flights" button
    Then the user should see the "reserve" page

    When the user selects the flight number 2 in the list
    Then the user should see the "purchase" page

    When the user enters "John Doe" in the "Name" field
    When the user enters "123 Main St" in the "Address" field
    When the user enters "Boston" in the "City" field
    When the user enters "MA" in the "State" field
    When the user enters "12345" in the "Zip Code" field
    When the user enters "1234567890123456" in the "Credit Card Number" field
    When the user enters "John Doe" in the "Name on Card" field
    And the user clicks the "Remember Me" button
    And the user clicks the "Purchase Flight" button
    Then the user should see the "confirmation" page

