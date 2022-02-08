package reports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportTrail {

    public static Logger logger = LogManager.getLogger(ReportTrail.class);


    public static void info(String message){ logger.info("Info: " + message); }

    public static void error(String message, Object p0, Object p1){
        logger.error("Error: " + message);
        logger.error(message,p0,p1);
    }

    public static void errorScreen(String message, Object p0){
        logger.error("Error: " + message);
        logger.error(message,p0);
    }

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
