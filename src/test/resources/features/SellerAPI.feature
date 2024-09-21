Feature: Test Seller API


  @getSellerVerifyEmailNotEmpty @Regression
  Scenario: Get single seller and verify email is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then verify email is not empty

    @getAllSellersVerifyIdNotZero @Regression

    Scenario: Get all sellers and verify seller id is not zero
      Given user hits get all sellers api with "/api/myaccount/sellers"
      Then verify seller ids are not equal to 0

      @getSellerUpdate @Regression
      Scenario: Get single seller and update it
        Given user hits get single seller api with "/api/myaccount/sellers/"
        Then verify email is not empty
        Then user hits update seller api with "/api/myaccount/sellers/"
        Then user verifies that email has been changed
        And user verifies that first name has been changed




