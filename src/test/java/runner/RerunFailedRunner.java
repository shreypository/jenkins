package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "@target/rerun.txt",
    glue = {"com.training.steps", "com.training.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/rerun.html",
        "json:target/cucumber-reports/rerun.json"
    },
    monochrome = true
)
public class RerunFailedRunner extends AbstractTestNGCucumberTests { }