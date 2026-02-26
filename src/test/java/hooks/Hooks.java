package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import web.factory.DriverFactory;
import web.reports.ExtentManager;
import web.reports.ExtentTestHolder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Base64;

public class Hooks {

    private static final ExtentReports extent = ExtentManager.getExtent();

    @Before
    public void beforeScenario(Scenario scenario) {
        DriverFactory.initDriver();
        ExtentTest test = extent.createTest(scenario.getName());
        scenario.getSourceTagNames().forEach(test::assignCategory);
        ExtentTestHolder.set(test);
        test.info("Browser launched");
    }

    @After
    public void afterScenario(Scenario scenario) {
        ExtentTest test = ExtentTestHolder.get();

        if (scenario.isFailed()) {
            byte[] png = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(png, "image/png", "Failure Screenshot");

            String base64 = Base64.getEncoder().encodeToString(png);
            if (test != null) {
                test.fail("❌ FAILED");
                try {
                    test.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
                } catch (Exception ignored) {}
            }
        } else {
            if (test != null) test.pass("PASSED");
        }

        extent.flush();
        ExtentTestHolder.remove();
        DriverFactory.quitDriver();
    }
}