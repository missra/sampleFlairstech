package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    By recordCount = By.xpath("//span[contains(.,'Record')]");
    By addButton = By.xpath("//button[contains(.,' Add ')]");
    By userRole = By.xpath("//div[@class='oxd-select-text-input' and text()='-- Select --']");
    By adminValue = By.xpath("//div[@role='option']//span[contains(.,'Admin')]");
    By userSearch = By.xpath("(//input[@class=\"oxd-input oxd-input--active\"])[2]");

    By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    By statusDropDown = By.xpath("//div[@class='oxd-select-text-input' and text()='-- Select --']");

    By deleteBtn = By.xpath("(//i[@class='oxd-icon bi-trash'])[1]");
    By confirmDelete = By.xpath("//div[@role=\"dialog\"]//button[contains(.,' Yes, Delete ')]");

    By form = By.xpath("//form");
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAdminTab() {
        driver.navigate().to("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
    }

    public int getRecordCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(recordCount));
        String infoText = driver.findElement(recordCount).getText();
        String infoString = infoText.replaceAll(" ","");
        infoString = infoString.split("\\)")[0];
        infoString = infoString.replaceAll("\\(","");
        System.out.println(infoString);
        return Integer.parseInt(infoString);
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        driver.findElement(addButton).click();
    }

    public String addNewUser() {
        String random = System.currentTimeMillis() + "a";
        addAdmin();
        addEmpName();
        addDropDown();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement activeElement = (WebElement) js.executeScript("return document.activeElement");
        activeElement.sendKeys(Keys.ENTER);
        activeElement.sendKeys(Keys.TAB);
        for (int i = 0; i < 4;i++){
            activeElement.sendKeys(random);
            activeElement.sendKeys(Keys.TAB);
            activeElement = (WebElement) js.executeScript("return document.activeElement");
        }
        wait.until(ExpectedConditions.elementToBeClickable(form)).submit();
        return random;
    }

    private void addDropDown() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusDropDown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusDropDown)).sendKeys(Keys.ARROW_DOWN);
    }

    private void addAdmin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userRole)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminValue)).click();
    }

    private void addEmpName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField)).sendKeys("a");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField)).sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField)).sendKeys(Keys.ENTER);
    }

    public void deleteUser(String userName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteBtn));
        driver.findElement(deleteBtn).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(confirmDelete));
        driver.findElement(confirmDelete).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(userSearch)).sendKeys(userName);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement activeElement = (WebElement) js.executeScript("return document.activeElement");
        activeElement.sendKeys(Keys.ENTER);
        try {
            assert wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@role=\"row\"]//div[contains(.,'"+userName+"')]"))).size()==0;
        }catch (Exception ignored){
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(.,'Reset')]"))).click();

    }

    public void searchUser(String username) {
        wait.until(ExpectedConditions.presenceOfElementLocated(userSearch)).sendKeys(username);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement activeElement = (WebElement) js.executeScript("return document.activeElement");
        activeElement.sendKeys(Keys.ENTER);
        assert wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role=\"row\"]//div[contains(.,'"+username+"')]"))).isDisplayed();
        assert wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role=\"row\"]//div[contains(.,'"+username+"')]"))).size()==1;
    }
}
