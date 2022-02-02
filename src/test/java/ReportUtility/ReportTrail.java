package ReportUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportTrail {

    public static Logger logger = LogManager.getLogger(ReportTrail.class);


    public static void info(String message){ logger.info("Info: " + message); }

    public static void fail(String message){
        logger.fatal("Fail: " + message);
    }

    public static void error(String message){
        logger.fatal("Error: " + message);
    }

    public static void pass(String message){
        logger.info("Pass: " + message);
    }

    public static void skip(String message){
        logger.error("Skip: " + message);
    }

    
}
