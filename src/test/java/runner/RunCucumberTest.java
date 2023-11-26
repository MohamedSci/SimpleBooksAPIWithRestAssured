//package runner;
//import org.junit.runner.RunWith;
//import io.cucumber.testng.CucumberOptions;
//import io.cucumber.junit.CucumberOptions;
//
//import io.cucumber.junit.Cucumber;
//import io.cucumber.testng.*;
//
//@RunWith(Cucumber.class)
//@CucumberOptions(features="src/test/java/features"
//        ,glue= {"steps"},
//                monochrome = true,
//        plugin= {"pretty","html:target/cucumber-html-report"})
//public class BDDRunner 
//{
//
//
//}
package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = {"steps"}, plugin = {"pretty",
        "html:target/cucumber-html-report.html"})
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
