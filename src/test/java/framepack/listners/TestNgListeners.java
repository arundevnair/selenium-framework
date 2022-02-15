package framepack.listners;


import com.aventstack.extentreports.ExtentReports;
import framepack.uipieces.drivers.OmniDriver;
import framepack.utils.Utility;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import reports.ExtentReportTrail;
import reports.ReportTrail;

import java.util.ArrayList;
import java.util.List;

public class TestNgListeners implements ITestListener {

    public  static ExtentReports extent = ExtentReportTrail.getReport();

    @BeforeSuite
    public void beforeSuiteActions(){
        System.out.println("Printing ");
        ExtentReportTrail.startReport();
        ReportTrail.info("\n\n\n\n\n\n ====== EXECUTION START - SUITE LEVEL =========== \n\n");
    }
    

    @AfterSuite
    public void afterSuiteActions(){
        ReportTrail.info("\n\n\n\n\n\n ====== EXECUTION END - SUITE LEVEL =========== \n\n");
    }

    public void onTestStart(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName();
        ReportTrail.info("Test Start: " + testCaseName);
        String methodName = "";
        try {
            if(result.getTestContext().getAttribute("testName").toString()!="") {
                methodName = result.getTestContext().getAttribute("testName").toString();
            }else {
                methodName = result.getMethod().getMethodName();
            }
        }catch (Exception e) {
            methodName = result.getMethod().getMethodName();
        }
        ExtentReportTrail.addTest(methodName,result);
        ExtentReportTrail.addInfo("Test " + methodName + " has begun");
        extent.flush();
    }

    public void onTestSuccess(ITestResult result) {
        ReportTrail.info("Test Success: " + result.getMethod().getMethodName() + "\n\n\n\n");
        extent.flush();
    }

    public void onTestFailure(ITestResult result) {
        ReportTrail.info("Test Failed: " + result.getMethod().getMethodName() + "\n\n\n\n");
        String fileNameFull = Utility.captureScreenshotB64FullPage(OmniDriver.getDriver());
        ReportTrail.errorScreen("Adding screenshot of error screen full page", fileNameFull);
        extent.flush();
        Utility.sleep(2000);
        System.out.println("Flushed extent");
    }

    public void onTestSkipped(ITestResult result) {
        List<String> methodNames = new ArrayList<>();
        result.getSkipCausedBy().stream().map(ITestNGMethod::getMethodName).forEach(methodNames::add);
        ReportTrail.skip("The test method " + result.getMethod().getMethodName() + " was kipped."  + "\n\n\n\n");
        if (methodNames.size() > 0) {
            ReportTrail.skip(
                    "The skip was caused by one/all of the following methods " + methodNames.toString());
        }
        Utility.captureScreenshot(OmniDriver.getDriver());
        extent.flush();
    }
}
