package framepack.uipieces.drivers;

import org.openqa.selenium.WebDriver;

public class OmniDriver {

    private static ThreadLocal<WebDriver> commonDriver = new ThreadLocal<>();
    public static WebDriver getCommonDriver() {
        return OmniDriver.commonDriver.get();
    }
    public static void setCommonDriver(WebDriver commonDriver) {
        OmniDriver.commonDriver.set(commonDriver);
    }

    private static WebDriver omniDriver;
    public static WebDriver getDriver(){
        return omniDriver;
    }
    public static void setDriver(WebDriver driver){
        omniDriver =driver;
    }
}
