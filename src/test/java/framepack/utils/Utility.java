package framepack.utils;

import org.apache.commons.codec.binary.Base64;
import reports.ReportTrail;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Utility {

    public WebDriver driver;
    static LocalDateTime startTime;
    static LocalDateTime endTime;
    static long timeDifference;
    public Utility(WebDriver driver){
        this.driver = driver;
    }

    public static void getTodayDate() {
        String region = PropertyReader.getProperty("country");
        String toFormat = PropertyReader.getProperty("dateFormat");
        getTodayDateByRegion(region,toFormat);
    }

    public static String getTodayDateByRegion(String region, String toFormat) {

        String timeZone = null;
        region = region.toLowerCase();

        if (region.equals("india") | region.equals("in") | region.equals("ist")) {
            timeZone = "Asia/Kolkata";
        }else if (region.equals("us")) {
            timeZone = "US/Eastern";
        } else if (region.equals("uk")) {
            timeZone = "Europe/London";
        } else if (region.equals("ca")){
            timeZone = "Canada/Eastern";
        }else if (region.equals("pst")) {
            timeZone = "America/Los_Angeles";
        } else if (region.equals("cst")){
            timeZone = "US/Central";
        } else if (region.equals("est")){
            timeZone = "US/Eastern";
        }else {
            ReportTrail.info("Your input region " + region + " is not valid . Hence Time Zone defaulted to IST");
            timeZone = "Asia/Kolkata";
        }

        ZoneId zoneId = ZoneId.of(timeZone);
//        Getting time at zone in the given timeZone in +/-GMT format
        ZonedDateTime timeAsZoned = ZonedDateTime.now(zoneId);
//        Converting the time to localDate time format
//        LocalDateTime localDateTimeOfZone = timeAsZoned.toLocalDateTime();

//        Converting the calculated time the given time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(toFormat);
        String formattedDate = timeAsZoned.format(formatter);
        System.out.println(formattedDate);
        return formattedDate;

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

    public static String getADateOlderThan(String years, String toFormat){
        String date = "";
        Random random = new Random();
        int yearUbound = 60-Integer.parseInt(years);
        int rYears = random.nextInt(yearUbound)+Integer.parseInt(years);
        int rMonths = random.nextInt(11)+1;
        int rDays = random.nextInt(27)+1;

        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.minusYears(rYears);
        ldt = ldt.minusMonths(-rMonths);
        ldt = ldt.minusDays(rDays);

        date = convertDateToString(ldt,toFormat);

        return date;
    }

    public static String convertDateToString(LocalDateTime ldt, String toFormat){
        //        Converting the calculated time the given time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(toFormat);
        String formattedDate = ldt.format(formatter);
        return formattedDate;
    }

    public static String getAnAdultDOB() {
        String years = PropertyReader.getProperty("adultYears");
        String toFormat = PropertyReader.getProperty("dateFormat");
        ReportTrail.info("Getting an adult DOB using utility with property as adultYears=" + years + " dateFormat=" + toFormat);
        String dob = getADateOlderThan(years,toFormat);
        ReportTrail.info("DOB got as " + dob);
        return dob;
    }

    public static String getAChildDOB() {
        String years = PropertyReader.getProperty("childMaxYears");
        String toFormat = PropertyReader.getProperty("dateFormat");
        ReportTrail.info("Getting an child DOB using utility with property as childMaxYears=" + years + " dateFormat=" + toFormat);
        String dob = getADateOlderThan(years,toFormat);
        ReportTrail.info("DOB got as " + dob);
        return dob;
    }

    public static LocalDateTime addTime(LocalDateTime time, int timeToAdd, String periodType) {
        periodType = periodType.toLowerCase(Locale.ENGLISH);
        LocalDateTime newTime = null;
        switch (periodType) {
            case "hours":
                newTime = time.plusHours(timeToAdd);
                break;
            case "days":
                newTime = time.plusDays(timeToAdd);
                break;
            case "minutes":
                newTime = time.plusMinutes(timeToAdd);
                break;
            case "months":
                newTime = time.plusMonths(timeToAdd);
                break;
            case "years":
                newTime = time.plusYears(timeToAdd);
                break;
        }
        return newTime;
    }

    public static int getNumberOfDaysInMonth(String year, String month) {
        ReportTrail.info("Getting the number of days in the month " + month + " of the year " + year);
        int numberOfDays = 0;
        try {
            YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
            numberOfDays = yearMonthObject.lengthOfMonth();
        } catch (NumberFormatException e) {
            ReportTrail.fail("Unable to parse the parameters.");
        }
        ReportTrail.info("The number of days are " + numberOfDays);
        return numberOfDays;
    }

    public static LocalDateTime getLastDayOfMonth(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        LocalDate lastDateOfMonth =
                localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
        ReportTrail.info("The last day is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        LocalDate lastDateOfMonth =
                localDate.withDayOfMonth(1);
        ReportTrail.info("The first day of the month is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static LocalDateTime getFirstDayOfNextMonth(LocalDateTime date) {
        LocalDateTime sameDayNextMonth = date.plusMonths(1);
        LocalDate localDate = sameDayNextMonth.toLocalDate();
        LocalDate lastDateOfMonth = localDate.withDayOfMonth(1);
        ReportTrail.info("The first day of the next month is  " + lastDateOfMonth.atStartOfDay());
        return lastDateOfMonth.atStartOfDay();
    }

    public static void waitUntilFound(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for " + elementFriendlyName + " element to be found");
        startTime = LocalDateTime.now();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.visibilityOf(el));
            endTime = LocalDateTime.now();
            ReportTrail.info("The element " + elementFriendlyName + " found in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + elementFriendlyName + " to be found");
//            ReportTrail.addError(e.getMessage());
        }
    }

    public static void waitUntilClickable(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for " + elementFriendlyName + " element to be clickable");
        startTime = LocalDateTime.now();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.elementToBeClickable(el));
            endTime = LocalDateTime.now();
            ReportTrail.info("The element " + elementFriendlyName + " found as to be clickable in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + elementFriendlyName + " to be clickable");
//            ReportTrail.addError(e.getMessage());
        }
    }


    public static void waitUntilNotPresent(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for " + elementFriendlyName + " element to disappear");
        startTime = LocalDateTime.now();
        try {
            if(el.isDisplayed()){
                new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.invisibilityOf(el));
                endTime = LocalDateTime.now();
                ReportTrail.info("The element " + elementFriendlyName + " disappeared in " + getTimeDifference() + " seconds");
            }
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + elementFriendlyName + " to disappear");
//            ReportTrail.addError(e.getMessage());
        }
    }

    public static void waitUntilNotPresent(WebDriver driver, List<WebElement> el, String elementFriendlyName, int timeoutInSeconds) {
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for " + elementFriendlyName + " element to disappear");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    if(!el.isEmpty()) {
                        try{
                            (new WebDriverWait(driver, Duration.ofSeconds(1))).until(ExpectedConditions.invisibilityOfAllElements(el));
                            return true;
                        }catch (WebDriverException e){
                            return false;
                        }
                    } else {
                        return true;
                    }
                }
            });
        } catch (Exception e) {
            ReportTrail.error("Encountered error for waitUntilNotPresent");
            ReportTrail.error(e.getMessage());
        }

    }

    public static String captureScreenshot(WebDriver driver) {

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
                    "Failure ScreenPrint");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    public static String captureScreenshotB64FullPage(WebDriver driver) {

        String fileName = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            String stringDate = dateFormat.format(new Date());
            fileName = "executionResults/pics/screenshotFullPage-" + stringDate + ".png" ;

            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(),"PNG", new File(fileName));

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
                    "Failure ScreenPrint");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    public static long getTimeDifference(){
        long diff = 0;
        try{
            diff = Duration.between(startTime,endTime).toSeconds();
            startTime = null;
            endTime = null;
            return diff;
        }catch (Exception e){
            ReportTrail.error("Couldn't calculate the time difference correctly. startTime was set as "
                    + startTime + " and endTime was set as " + endTime);
            return 999999999;
        }

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
