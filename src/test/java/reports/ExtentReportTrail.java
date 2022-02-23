package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import framepack.uipieces.drivers.OmniDriver;
import framepack.utils.Utility;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExtentReportTrail {

    public static ExtentReports report;
    public static ThreadLocal<ExtentTest> classTest= new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> test= new ThreadLocal<>();
    private static final Logger LOGGER = LogManager.getLogger(ExtentReportTrail.class);

//    @BeforeSuite
    public static ExtentReports startReport(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_dd_MM_yyyy(HH_mm_ss)");
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateTime = ldt.format(formatter);
        System.out.println(formattedDateTime);
        String reportName = System.getProperty("user.dir") + "/executionResults/" + "ExtentReport" + formattedDateTime +".html";
        System.out.println("reportName: " + reportName);
        ExtentSparkReporter testreporter = new ExtentSparkReporter(reportName);
/*        try {
            testreporter.loadXMLConfig( System.getProperty("user.dir") + "/" + "extent-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        report = new ExtentReports();
        report.attachReporter(testreporter);
        return report;
    }

    public static ExtentReports getReport(){
        System.out.println("ExtentReportTrail.getReport() called ... ");
        if (report != null){
            return report;
        } else {
            return startReport();
        }
    }

    public static void addTest(String testName, String testCategory, ITestResult result ){
        if(result==null) {
            test.set(classTest.get().createNode(testName));
        }
        else {
            test.set(classTest.get().createNode(testName+"_"+result.getMethod().getDescription()));
        }
        test.get().assignCategory(testCategory);
    }

    public static void addTest(String testName,  ITestResult result ){
        if(result==null) {
            test.set(classTest.get().createNode(testName));
        } else {
            test.set(classTest.get().createNode(testName+"_"+result.getMethod().getDescription()));
        }
    }

    public static ThreadLocal<ExtentTest> getTest(){
        if (test != null){
            return test;
        } else {
            return classTest;
        }
    }


    public static void addInfo(String message){
        if (test.get() != null){
            test.get().log(Status.INFO, message);
            LOGGER.info(message);
        } else {
            try{
                classTest.get().info(message);
            }catch (NullPointerException e){
            }
            LOGGER.info("TestClassLogs: "+message);
        }
    }

    public static void addInfo(Markup markup){
        if (test.get() != null){
            test.get().log(Status.INFO, markup);
            LOGGER.info(markup.getMarkup());
        } else {
            classTest.get().info(markup);
            LOGGER.info("TestClassLogs: "+markup.getMarkup());
        }
    }

    public static void addFail(String message){
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
            LOGGER.error(message);
        } else {
            classTest.get().fail(message);
            LOGGER.error("TestClassLogs: " + message);
        }
    }

    public static void addFail(String message, Object p0, Object p1){
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
            LOGGER.error(message,p0,p1);
        } else {
            classTest.get().fail(message);
            LOGGER.error(message,p0,p1);

        }
    }

    public static void addFailScreen(String message, Object p0){

        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
            LOGGER.error(message, p0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss_zzz");
            String stringDate = dateFormat.format(new Date());
            String location = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "screenshot"
                    + stringDate + ".png";
            String fileNameTrimmed = Utility.captureScreenshotB64FullPageExtent(OmniDriver.getDriver());
//            LOGGER.error("Extent screenshot: ", fileNameTrimmed);
            test.get().log(Status.INFO,"Extent Screenshot Failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(fileNameTrimmed).build());
        } else {
            classTest.get().fail(message);
            LOGGER.error(message, p0);
            String fileNameTrimmed = Utility.captureScreenshotB64FullPageExtent(OmniDriver.getDriver());
//            LOGGER.error("Extent screenshot: ", fileNameTrimmed);
            classTest.get().log(Status.INFO,"Extent Screenshot Failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(fileNameTrimmed).build());
        }
    }

    public static void addScreenshot(String location){
        try {
            test.get().log(Status.INFO,"Screenshot",
                    MediaEntityBuilder.createScreenCaptureFromPath(location).build());
        } catch (Exception e){
            addInfo("Failed to add screenshot.");
            LOGGER.info("Failed to add screenshot.");
        }
    }


    @Deprecated
    public static void addScreenshotB64(){

        String encodedfile ;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss_zzz");
            String stringDate = dateFormat.format(new Date());
            String location = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "screenshot"
                    + stringDate + ".png";
            File file =
                    new File( location);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
            test.get().log(Status.INFO,"ScreenShot",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(encodedfile).build());
            LOGGER.info("RP_MESSAGE#BASE64#{}#{}",encodedfile,
                    "Failure ScreenPrint");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addPass(String message){
        if (test.get() != null){
            test.get().log(Status.PASS, message);
            LOGGER.debug(message);
        } else{

            classTest.get().pass(message);
            LOGGER.debug("TestClassLogs: "+message);

        }
    }

    public static void addSkip(String message){
        if ( test.get() != null){
            test.get().log(Status.SKIP, message);
            LOGGER.info(message);
        } else {
            classTest.get().skip(message);
            LOGGER.info("TestClassLogs SKIP: "+message);

        }
    }

    public static void addInfo(Throwable e){
        if ( test.get() != null){
            test.get().info(e);
            LOGGER.info(e.getMessage());
        } else {
            classTest.get().info(e);
            LOGGER.info("TestClassLogs: "+e.getMessage());

        }
    }

    public static void addFail(Throwable e){
        if ( test.get() != null){
            test.get().fail(e);
            LOGGER.info(e.getMessage());
        } else {
            classTest.get().fail(e);
        }
    }

    public static void addSkip(Throwable e){
        if ( test.get()!= null){
            test.get().skip(e);
            LOGGER.info(e.getMessage());
        } else {
            classTest.get().skip(e);
            LOGGER.info("SKIP: "+e.getMessage());
        }
    }



}
