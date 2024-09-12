package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.LoginPage;

public class LoginSteps {
    LoginPage loginPage;

    @Given("User is on login page")
    public void user_is_on_login_page() {
        loginPage = new LoginPage(Hooks.driver);
        Hooks.driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @When("User enters {string} and {string}")
    public void user_enters_credentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("User clicks login button")
    public void user_clicks_login_button() {
        loginPage.clickLogin();
    }

    @Then("User is logged in")
    public void user_is_logged_in() {
        loginPage.verifyOnLoggedInUser();
    }
}
