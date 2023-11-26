#Author: muhammedsaidsyed215@gmail.com


Feature: User explores Books then manipulate Orders
  The User requests authorization then explores Books Apis after that goes through Orders APIs.

  Background: User generates token for Authorisation
    Given I am an authorized User
    Then Lets Check the Status From API

  Scenario: the Authorized User request all Books then request some Book by Id
    Given A list of Books are available
    Then The User stores the data of a certain Book
    Given The User request Single Book APi
    Then The Validation of response data retrieved from ALL Books and Single Books APIs are the Identical

    When The User submits a Book to be added to his Orders list
    Then The API request informs that the Book is added and Order Id is stored

    Given A list of all  Orders are available
    Then The User Checks the recently Submitted Order to be found in the response, the Order is found
    Given The User request Single Order APi
    Then The User Checks the recently Submitted Order to be found in the Single Order response, the Order is found

    When The User updates a Book from his Orders list using the Book s Order id
    Then The Book is updated

    When The User removes a Book from his Orders list using the Book s Order id
    Then The Book is deleted

    Given A list of all  Orders are available Again
    Then The User Checks Again the recently Submitted Order to be found in the response, the Order is Missing
    Given The User request Single Order APi Again
    Then The User Checks Again the recently Submitted Order to be found in the Single Order response, the Order is Missing
