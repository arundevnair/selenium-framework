package framepack.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Reporter;
import reports.PlatformDetection;
import reports.ReportTrail;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public WebDriver driver;


    public Utility(WebDriver driver){
        this.driver = driver;
    }

    public static WebElement getWebElement(WebDriver driver, String locatorsType, String locatorValue) {
        locatorsType = locatorsType.toLowerCase(Locale.ENGLISH);
        WebElement webElement = null;
        try {
            switch (locatorsType) {
                case "xpath":
                    webElement = driver.findElement(By.xpath(locatorValue));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locatorValue));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locatorValue));
                    break;
            }
        } catch (NoSuchElementException nse) {
            ReportTrail.info("Unable to find the element with the  locator " + locatorValue + " of the"
                    + " locator type " + locatorsType);
        }
        return webElement;
    }

    public static void sleep(long milliSeconds){
        try{
            ReportTrail.info("Sleep start for " + milliSeconds + " milliSeconds");
            Thread.sleep(milliSeconds);
            ReportTrail.info("Sleep completed after " + milliSeconds + " milliSeconds");
        }catch (Exception e){
            System.out.println("Encountered error in sleep method in Utility");
        }

    }

    public static String captureScreenshotOld(WebDriver driver) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = "";
        try {
            // now copy the screenshot to desired location using copyFile //method
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            String saveLocation = "executionResults/pics/screenshot"
                    + stringDate + ".png";
            fileName =
                    saveLocation.replace(
                            System.getProperty("user.dir") + File.separator + "ReportTrails" + File.separator,
                            "");
            FileUtils.copyFile(src, new File(saveLocation));
            Reporter.log("<a href=\"" + "file:///" + System.getProperty("user.dir")
                    + "/screenshots/" + fileName + "\">Screenshot</a>");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return fileName;

    }

    public static String captureScreenshotB64(WebDriver driver) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String fileName = "";
        try {
            // now copy the screenshot to desired location using copyFile //method
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            String saveLocation = "executionResults/pics/screenshot"
                    + stringDate + ".png";
            fileName =
                    saveLocation.replace(
                            System.getProperty("user.dir") + File.separator + "ReportTrails" + File.separator,
                            "");
            FileUtils.copyFile(src, new File(saveLocation));

            Reporter.log("<a href=\"" + "file:///" + System.getProperty("user.dir")
                    + "/screenshots/" + fileName + "\">Screenshot</a>");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File(fileName);
        FileInputStream fileInputStreamReader = null;
        String b64Encodedfile = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            b64Encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
            ReportTrail.error("RP_MESSAGE#BASE64#{}#{}",b64Encodedfile,
                    "Failure ScreenPrint Current window");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    public static String captureScreenshotB64FullPageOld(WebDriver driver) {

//        captureScreenshot(driver);
        String fileName = "";
        String saveLocationb64 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
//            fileName = "executionResults/pics/screenshotFullPage-" + stringDate + ".png" ;
            String saveLocation = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "screenshot"
                    + stringDate + ".png";
            saveLocationb64 = saveLocation;
            System.out.println("saavelocation: " + saveLocation);
            fileName =
                    saveLocation.replace(
                            System.getProperty("user.dir") + File.separator + "ExtentReports" + File.separator,
                            "");
            System.out.println("fileNamee: " + fileName);

            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
//            ImageIO.write(screenshot.getImage(),"PNG", new File(fileName));
            ImageIO.write(screenshot.getImage(),"PNG", new File(saveLocationb64));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File(saveLocationb64);
        FileInputStream fileInputStreamReader = null;
        String b64Encodedfile = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            b64Encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
            ReportTrail.error("RP_MESSAGE#BASE64#{}#{}",b64Encodedfile,
                    "Failure ScreenPrint");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    public static String captureScreenshotB64FullPage(WebDriver driver) {
        ReportTrail.info("captureScreenshotB64FullPage plain called");
        String fileNameTrimmed = "";
        String filePathAndNameb64 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            filePathAndNameb64 = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "screenshot"
                    + stringDate + ".png";
/*            fileNameTrimmed =
                    filePathAndNameb64.replace(
                            System.getProperty("user.dir") + File.separator + "executionResults" + File.separator,
                            "");*/
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(),"PNG", new File(filePathAndNameb64));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File(filePathAndNameb64);
        FileInputStream fileInputStreamReader = null;
        String b64Encodedfile = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            b64Encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
            ReportTrail.error("RP_MESSAGE#BASE64#{}#{}",b64Encodedfile,
                    "Failure ScreenPrint Full page");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return fileNameTrimmed;
        ReportTrail.info("captureScreenshotB64FullPage plain ended");
        return filePathAndNameb64;

    }

    public static String captureScreenshotB64FullPageExtent(WebDriver driver) {
        String fileNameTrimmed = "";
        String filePathAndNameb64 = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            filePathAndNameb64 = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "ExtentSreenshot"
                    + stringDate + ".png";
            fileNameTrimmed =
                    filePathAndNameb64.replace(
                            System.getProperty("user.dir") + File.separator + "executionResults" + File.separator,
                            "");

            Object output = ((JavascriptExecutor) driver).executeScript("return window.devicePixelRatio");
            String value = String.valueOf(output);
            float windowDPR = Float.parseFloat(value);
//            ShootingStrategy shootingStrategyScreen = ShootingStrategies.viewportPasting(ShootingStrategies.scaling(windowDPR), 100);
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(windowDPR),2000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(),"PNG", new File(filePathAndNameb64));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File(filePathAndNameb64);
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReportTrail.info("captureScreenshotB64FullPage LOC ended");
        return fileNameTrimmed;
    }

    public static String captureScreenshot(WebDriver driver) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = "";
        try {
            // now copy the screenshot to desired location using copyFile //method
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            String saveLocation = PlatformDetection.getlocation(PlatformDetection.getOS())[1] + "screenshot"
                    + stringDate + ".png";
            System.out.println("saavelocation: " + saveLocation);
            fileName =
                    saveLocation.replace(
                            System.getProperty("user.dir") + File.separator + "ExtentReports" + File.separator,
                            "");
            System.out.println("fileNamee: " + fileName);
            FileUtils.copyFile(src, new File(saveLocation));
            Reporter.log("<a href=\"" + "file:///" + System.getProperty("user.dir")
                    + "/screenshots/" + fileName + "\">Screenshot</a>");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fileName;
    }

    public static String getRandomAlphaNumericString(int length) {
        final String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getRandomAlphaUpperCase(int length) {
        String temp = "abcdefghijklmopqrstuvwxyz";
        final String ALPHA_NUMERIC_STRING = temp.toUpperCase();
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getRandomSpecialChars(int length) {
        final String ALPHA_NUMERIC_STRING = "!@#$%^&()";
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String getRandomEmail() {
        String invalidEmail = getRandomAlphaNumericString(getRandomInt(5,25)) + "@randomgmail.com";
        ReportTrail.info("A random email generated as : " + invalidEmail);
        return invalidEmail;
    }

    public static String getRandomPassword(int length) {
        String passWd = getRandomAlphaUpperCase(1) + getRandomSpecialChars(1)
                + getRandomAlphaNumericString(3) + getRandomInt(1,5) + getRandomSpecialChars(length-5);
        ReportTrail.info("A random password generated as : " + passWd);
        return passWd;
    }

    public static int getRandomInt(int min, long max) {
        int randVal  = (int)((Math.random()*((max-min)+1))+min);
        return randVal;
    }

    public static long getRandomLong(long min, long max){
        long randVal  = (long)((Math.random()*((max-min)+1))+min);
        return randVal;
    }

    public static String getRandomCellNumber(){
        String cellNumber = "9"+getRandomLong(100000000,999999999);
        return cellNumber;
    }

    public void scrollDownPage(WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0, 350);");
    }

    public void scrollDownToBottom(WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollUpPage(WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0, -350);");
    }


}
