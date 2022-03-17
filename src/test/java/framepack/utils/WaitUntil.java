package framepack.utils;

import framepack.uipieces.drivers.OmniDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reports.ReportTrail;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WaitUntil {

    static LocalDateTime startTime;
    static LocalDateTime endTime;

    public static long getTimeDifference(){
        long diff = 0;
        try{
            diff = Duration.between(startTime,endTime).toSeconds();
            startTime = null;
            endTime = null;
            return diff;
        }catch (Exception e){
            ReportTrail.info("Error: Couldn't calculate the time difference correctly. startTime was set as "
                    + startTime + " and endTime was set as " + endTime);
            return 999999999;
        }

    }

    @Deprecated
    public static void found(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
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

    public static void found(WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
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

    @Deprecated
    public static void clickable(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
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

    public static void clickable(WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for " + elementFriendlyName + " element to be clickable");
        startTime = LocalDateTime.now();
        try {
//            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.elementToBeClickable(el));
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(driver1 -> ExpectedConditions.elementToBeClickable(el).apply(driver));
            endTime = LocalDateTime.now();
            ReportTrail.info("The element " + elementFriendlyName + " found as to be clickable in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + elementFriendlyName + " to be clickable");
//            ReportTrail.addError(e.getMessage());
        }
    }

    public static void pageLoads( int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for page to load");
        startTime = LocalDateTime.now();
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeoutInSeconds));
            endTime = LocalDateTime.now();
            ReportTrail.info("The page loaded in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for page to load");
        }
    }

    @Deprecated
    public static void notPresent(WebDriver driver, WebElement el, String elementFriendlyName, int timeoutInSeconds) {
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

    public static void notPresent( WebElement el, String elementFriendlyName, int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
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


    public static void attributeChange( WebElement el, String attribute, String expectedValue, int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for attribute " + attribute + " to change as " + expectedValue);
        startTime = LocalDateTime.now();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    String attrValue = el.getAttribute(attribute);
                    if (attrValue.equals(expectedValue))
                        return true;
                    else
                        return false;
                }
            });
            endTime = LocalDateTime.now();
            ReportTrail.info("The attribute changed in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + attribute + " attribute to change");
        }
    }

    public static void attributePartChange( WebElement el, String attribute, String expectedValue, int timeoutInSeconds) {
        WebDriver driver = OmniDriver.getDriver();
        ReportTrail.info("Waiting for " + timeoutInSeconds + " seconds for a part of attribute " + attribute + " to change as " + expectedValue);
        startTime = LocalDateTime.now();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    String attrValue = el.getAttribute(attribute);
                    if (attrValue.contains(expectedValue))
                        return true;
                    else
                        return false;
                }
            });
            endTime = LocalDateTime.now();
            ReportTrail.info("The attribute changed in " + getTimeDifference() + " seconds");
        } catch (Exception e) {
            ReportTrail.error("Encountered error while waiting for " + attribute + " attribute to change");
        }
    }
}
