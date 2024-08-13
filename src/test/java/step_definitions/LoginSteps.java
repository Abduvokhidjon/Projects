package step_definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.LoginPage;
import utilities.Driver;

public class LoginSteps extends LoginPage {
WebDriver driver = Driver.getDriver();

    @Given("user is on login page {string}")
    public void user_is_on_login_page(String string) {
        driver.get(string);
    }

    @Then("user enters {string} for username")
    public void user_enters_for_username(String string) {
        username.sendKeys(string);
    }
    @Then("user enters {string} for password")
    public void user_enters_for_password(String string) {
        password.sendKeys(string);
    }
    @Then("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginButton.click();
    }

}
