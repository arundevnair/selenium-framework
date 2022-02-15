package reports;


import java.io.File;
import org.openqa.selenium.Platform;

public class PlatformDetection {
    private static org.openqa.selenium.Platform platform;
    private static String extentreportname = "ExtentReport";
    private static String csvreportname = "CSV_Report";
    private static String linuxlocation = System.getProperty("user.dir") + "/executionResults";
    private static String linuxscreenshotlocation = linuxlocation + "/Screenshot/";
    private static String windowslocation = System.getProperty("user.dir") + "\\executionResults";
    private static String windowsscreenshotlocation = windowslocation + "\\Screenshot\\";
    private static String maclocation = System.getProperty("user.dir") + "/executionResults";
    private static String macscreenshotlocation = maclocation + "/Screenshot/";
    private static String extentlinuxreport = linuxlocation + "/" + extentreportname;
    private static String extentwindowsreport = windowslocation + "\\" + extentreportname;
    private static String extentmacreport = maclocation + "/" + extentreportname;
    private static String csvlinuxreport = linuxlocation + "/" + csvreportname;
    private static String csvwindowsreport = windowslocation + "\\" + csvreportname;
    private static String csvmacreport = maclocation + "/" + csvreportname;

    //Get the OS type
    public static org.openqa.selenium.Platform getOS(){
        if (platform == null){
            String os = System.getProperty("os.name").toUpperCase();
            if (os.contains("WIN")){
                platform = org.openqa.selenium.Platform.WINDOWS;
            }
            else if (os.contains("MAC")){
                platform = org.openqa.selenium.Platform.MAC;
            }
            else if (os.contains("NUX")||os.contains("NIX")){
                platform = org.openqa.selenium.Platform.LINUX;
            }
        }
        return platform;
    }

    //Create report path
    private static void createpath(String path){
        File location = new File(path);
        if (location.exists()==false){
            location.mkdir();
            if (location.exists()==true){
                System.out.println("Directory created at" + location);
            }
            else {
                System.out.println("Failed to create the directory");
            }
        }
//    else {
//      System.out.println("Location already exists");
//    }
    }

    //Set report location
    public static String[] getlocation(Platform platform){
        String extentreportname = "";
        String csvreportname = "";
        String screenshotlocation = "";
        switch (platform){
            case WINDOWS:
                extentreportname = extentwindowsreport;
                screenshotlocation = windowsscreenshotlocation;
                csvreportname = csvwindowsreport;
                createpath(windowslocation);
                createpath(windowsscreenshotlocation);
//        System.out.println("Platform detected as windows. extend report path is " + windowslocation + "\n");
//        System.out.println("Platform detected as windows. extend report path is " + windowsscreenshotlocation + "\n");
                break;
            case MAC:
                extentreportname = extentmacreport;
                screenshotlocation = macscreenshotlocation;
                csvreportname = csvmacreport;
                createpath(maclocation);
                createpath(macscreenshotlocation);
//        System.out.println("Platform detected as Mac. extend report path is " + maclocation + "\n");
//        System.out.println("Platform detected as Mac. extend report path is " + macscreenshotlocation + "\n");
                break;
            case LINUX:
                extentreportname = extentlinuxreport;
                screenshotlocation = linuxscreenshotlocation;
                csvreportname = csvlinuxreport;
                createpath(linuxlocation);
                createpath(linuxscreenshotlocation);
//        System.out.println("Platform detected as Linux. extend report path is " + linuxlocation + "\n");
//        System.out.println("Platform detected as Linux. extend screenshot path is " + linuxscreenshotlocation + "\n");
                break;
            default:
                System.out.println("Report location is not set");
                break;

        }
        String[] platformdetails = {extentreportname,screenshotlocation,csvreportname};
        return platformdetails;
    }



}