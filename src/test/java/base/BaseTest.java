package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import factory.DriverFactory;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        System.out.println("SETUP RUNNING");

        driver.set(DriverFactory.initDriver());

        getDriver().get("https://qabrains.com/practice-site");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}