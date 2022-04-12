Feature: Search in booking.com
  Scenario: User search for the right hotel
    Given User is on start page
    When User input name of hotel "Bed&Bike Tremola San Gottardo" in search field
    And  Click Search button
    Then Check that right hotel visible on page and check that hotel rating is "9.2"