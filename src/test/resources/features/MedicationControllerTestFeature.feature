Feature: Rest API functionalities

  Scenario: User able to add, edit, and remove medication
    Given A list of medications are available
    When I add a medication to my list
    Then The medication is added
    When I get a specific medication
    Then The specific medication is available
    When I edit a medication from my list
    Then The medication content is edited
    When I remove medication from my list
    Then The medication is removed
