package framepack.uipieces.drivers;

import framepack.utils.Utility;
import org.openqa.selenium.Dimension;
import reports.ReportTrail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.HashMap;
import java.util.Map;

import static io.github.bonigarcia.wdm.WebDriverManager.*;

public class StartDriver {

    WebDriver driver;
    public static Logger logger = LogManager.getLogger();
    public ThreadLocal<WebDriver>  commonDriver = new ThreadLocal<>();

    public ThreadLocal<WebDriver> getCommonDriver() {
        return commonDriver;
    }

    public void setCommonDriver(ThreadLocal<WebDriver> commonDriver) {
        this.commonDriver = commonDriver;
    }

    DesiredCapabilities caps;


    public void launchDriver(DriverType browser, String envUrl){

        switch (browser){
            case CHROME:
                chromedriver().setup();
                driver = new ChromeDriver();
                driver.get(envUrl);
                break;
            case EDGE:
                edgedriver().setup();
                driver = new EdgeDriver();
                driver.get(envUrl);
                break;
            case IE:
                iedriver().setup();
                driver = new InternetExplorerDriver();
                driver.get(envUrl);
                break;
            case SAFARI:
                safaridriver().setup();
                driver = new SafariDriver();
                driver.get(envUrl);
                break;
            case FIREFOX:
                firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.get(envUrl);

        }

        OmniDriver.setDriver(driver);
        OmniDriver.setCommonDriver(driver);

    }


    public WebDriver getDriver(){
        return driver;
    }

    public void closeDriver() {
        ReportTrail.info("Closing the web driver/browser");
        driver.quit();
        ReportTrail.info("Web driver is closed");
    }


    //Not completed yet
    @Deprecated
    public void launchMobileBrowser(DriverType browser, String envUrl){

        switch (browser){
            case CHROME:
                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", 1680);
                deviceMetrics.put("height", 1050);
                deviceMetrics.put("pixelRatio", 1.0);
/*                Map<String,String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("device", "Samsung Galaxy S8+");
                mobileEmulation.put("browserName", "android");
                mobileEmulation.put("realMobile", "false");
                mobileEmulation.put("os_version", "9.0");
                Map<String,Object> chromeOptions = new HashMap<>();
                chromeOptions.put("mobileEmulation",mobileEmulation);*/

                ChromeOptions opt = new ChromeOptions();
//                opt.setExperimentalOption("prefs", chromePreferences);
//                opt.addArguments("--start-maximized");
                opt.addArguments("--kiosk");

//                caps = new DesiredCapabilities();
//                caps.setCapability(ChromeOptions.CAPABILITY,chromeOptions);

                chromedriver().setup();
                driver = new ChromeDriver(opt);
                driver.get(envUrl);
                break;

        }
        driver.manage().window().maximize();

    }
}
