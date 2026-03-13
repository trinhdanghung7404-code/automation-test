package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ScreenshotUtils;
import base.BaseTest;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getName();

        ScreenshotUtils.capture(BaseTest.getDriver(), testName);

        System.out.println("Screenshot captured for failed test: " + testName);
    }
}