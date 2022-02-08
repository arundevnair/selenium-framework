package framepack.utils;

import reports.ReportTrail;

public class GetEnvironment {

    private static String runEnv;

    public static String getEnv(){
        ReportTrail.info("The execution environment is " + runEnv);
        return runEnv;
    }

    public static void setEnv(String env){
        ReportTrail.info("Setting the the execution environment is " + env);
        runEnv =env;
    }

}
