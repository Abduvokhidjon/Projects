package step_definitions;

import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.en.*;
import org.junit.Assert;
import utilities.ApiRunner;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {
    Faker faker = new Faker();
    String oldEmail;
    String oldName;
    String updatedEmail;
    String updatedName;
    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String string) {
      ApiRunner.runGET(string, 5767);
        oldEmail = ApiRunner.getCustomResponse().getEmail();
        oldName = ApiRunner.getCustomResponse().getSeller_name();
    }
    @Then("verify email is not empty")
    public void verify_email_is_not_empty() {
        Assert.assertFalse(oldEmail.isEmpty());
    }

    @Given("user hits get all sellers api with {string}")
    public void user_hits_get_all_sellers_api_with(String endPoint) {
       Map<String, Object> params = new HashMap<>();
       params.put("isArchived", false);
       params.put("page", 1);
       params.put("size", 100);

       ApiRunner.runGET(endPoint, params);
    }
    @Then("verify seller ids are not equal to {int}")
    public void verify_seller_ids_are_not_equal_to(int int1) {
        int size = ApiRunner.getCustomResponse().getResponses().size();
        for(int i = 0; i < size; i++) {
            Assert.assertNotEquals(0, ApiRunner.getCustomResponse().getResponses().get(i).getSeller_id());
        }
    }

    @Then("user hits update seller api with {string}")
    public void user_hits_update_seller_api_with(String endPoint) {

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());
       ApiRunner.runPut(endPoint, requestBody, 5064);
    }

    @Then("user verifies that email has been changed")
    public void user_verifies_that_email_has_been_changed() {
        updatedEmail = ApiRunner.getCustomResponse().getEmail();
       Assert.assertEquals(updatedEmail, oldEmail);

    }

    @Then("user verifies that first name has been changed")
    public void user_verifies_that_first_name_has_been_changed() {
        updatedName = ApiRunner.getCustomResponse().getSeller_name();
        Assert.assertEquals(updatedEmail, oldName);
    }

}
