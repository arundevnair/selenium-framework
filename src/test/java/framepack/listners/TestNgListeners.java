package framepack.listners;


import framepack.uipieces.drivers.OmniDriver;
import framepack.utils.Utility;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import reports.ReportTrail;

import java.util.ArrayList;
import java.util.List;

public class TestNgListeners implements ITestListener {

    public void onTestStart(ITestResult result) {
        ReportTrail.info("Test Start: " + result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        ReportTrail.info("Test Success: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        ReportTrail.info("Test Failed: " + result.getMethod().getMethodName());
//        String fileName = Utility.captureScreenshot(OmniDriver.getDriver());
        String fileName = Utility.captureScreenshotB64(OmniDriver.getDriver());
        ReportTrail.errorScreen("Adding screenshot of error screen", fileName);
    }

    public void onTestSkipped(ITestResult result) {
        List<String> methodNames = new ArrayList<>();
        result.getSkipCausedBy().stream().map(ITestNGMethod::getMethodName).forEach(methodNames::add);
        ReportTrail.skip("The test method " + result.getMethod().getMethodName() + " was kipped.");
        if (methodNames.size() > 0) {
            ReportTrail.skip(
                    "The skip was caused by one/all of the following methods " + methodNames.toString());
        }
        Utility.captureScreenshot(OmniDriver.getDriver());
    }
}
