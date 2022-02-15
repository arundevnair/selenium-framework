package reports;

import com.aventstack.extentreports.ExtentReports;
import framepack.utils.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportTrail {

    public static Logger logger = LogManager.getLogger(ReportTrail.class);
    ExtentReports extent = ExtentReportTrail.getReport();


    public static void info(String message){
//        logger.info("Info: " + message);
        ExtentReportTrail.addInfo(message);
    }

    public static void error(String message, Object p0, Object p1){
//        logger.error("Error: " + message);
//        logger.error(message,p0,p1);
        ExtentReportTrail.addFail( message, p0, p1);
    }

    public static void errorScreen(String message, Object p0){
//        logger.error("Error: " + message);
//        logger.error(message,p0);
        ExtentReportTrail.addFailScreen(message, p0);
        System.out.println("filepath");

    }

    public static void fail(String message){
//        logger.fatal("Fail: " + message);
        ExtentReportTrail.addFail(message);
    }

    public static void error(String message){
//        logger.fatal("Error: " + message);
        ExtentReportTrail.addFail(message);
    }

    public static void pass(String message){
//        logger.info("Pass: " + message);
        ExtentReportTrail.addPass(message);
    }

    public static void skip(String message){
//        logger.error("Skip: " + message);
        ExtentReportTrail.addSkip(message);
    }


    
}
