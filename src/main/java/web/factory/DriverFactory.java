package web.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver() {
        ChromeOptions options = new ChromeOptions();

        // Run headless in CI by passing: -Dheadless=true
        if ("true".equalsIgnoreCase(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1366,768");

        // Selenium Manager (Selenium 4+) will auto-manage the driver
        driver.set(new ChromeDriver(options));
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}