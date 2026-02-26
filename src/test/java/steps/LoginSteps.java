package steps;


import web.factory.DriverFactory;
import web.reports.ExtentLogger;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I open the Heroku Internet App login page")
    public void open_login_page() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ExtentLogger.step("Open URL: https://the-internet.herokuapp.com/login");
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @When("I login with username {string} and password {string}")
    public void login_with_creds(String username, String password) {
        ExtentLogger.step("Enter username: " + username);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);

        ExtentLogger.step("Enter password: " + password);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        ExtentLogger.step("Click Login button");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("I should see a flash message containing {string}")
    public void verify_flash_message(String expectedText) {
        ExtentLogger.step("Verify flash message contains: " + expectedText);
        String actual = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#flash"))
        ).getText();
        Assert.assertTrue(actual.contains(expectedText),
                "Expected to contain: [" + expectedText + "] but actual was: [" + actual + "]");
    }
}