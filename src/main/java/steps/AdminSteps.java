package steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.AdminPage;

public class AdminSteps {
    AdminPage adminPage;
    int initialRecordCount;
    String search;

    @When("User clicks on Admin tab")
    public void user_clicks_on_admin_tab() {
        adminPage = new AdminPage(Hooks.driver);
        adminPage.clickAdminTab();
    }

    @When("User checks the number of records")
    public void user_checks_the_number_of_records() {
        initialRecordCount = adminPage.getRecordCount();
    }

    @When("User adds a new user")
    public void user_adds_a_new_user() {
        adminPage.clickAddButton();
        search = adminPage.addNewUser();
        adminPage.getRecordCount();
    }

    @Then("The number of records should increase by 1")
    public void the_number_of_records_should_increase_by_one() {
        int newRecordCount = adminPage.getRecordCount();
        assert newRecordCount == initialRecordCount + 1;
    }

    @When("search For User added")
    public void user_searches_for_user() {
        adminPage.searchUser(search);
    }

    @When("User deletes the user")
    public void user_deletes_the_user() {
        adminPage.deleteUser(search);
    }

    @Then("The number of records should decrease by 1")
    public void the_number_of_records_should_decrease_by_one() {
        int newRecordCount = adminPage.getRecordCount();
        assert newRecordCount == initialRecordCount;
    }
}
