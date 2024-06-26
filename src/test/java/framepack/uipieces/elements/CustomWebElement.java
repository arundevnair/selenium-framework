package framepack.uipieces.elements;

import framepack.uipieces.drivers.OmniDriver;
import framepack.utils.WaitForElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reports.ReportTrail;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;


public class CustomWebElement implements Element {

    private final WebElement customElement;

    public CustomWebElement(final WebElement webElement) {
        customElement = webElement;
    }

    @Deprecated     // to discourage the basic click method
    public void click(){
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Clicking on element " + eleName);
            customElement.click();
//            ReportTrail.info("Clicked on " + eleName);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece) {
            ReportTrail.error("Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to click on " + customElement);
        }
    }

    public void doubleClick(WebDriver driver){
        try{
            String eleName = elementName();
            waitUntilClickable(customElement);
            ReportTrail.info("Double clicking on the element " + eleName);
            Actions act = new Actions(driver);
            act.doubleClick(customElement);
//            ReportTrail.info("Double clicked on " + eleName);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece) {
            ReportTrail.error("Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to double click on " + customElement);
        }
    }

    private void click(String elementType){
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Clicking on " + elementType + " " + eleName);
            customElement.click();
//            ReportTrail.info("Clicked on " + elementType + " "+ eleName);
        }catch (NoSuchElementException ne){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Element " + elementType + " not found with the locator " + customElement + "\n");
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Click intercepted exception for " + elementType + " "+ customElement + "\n");
        }catch (ElementNotInteractableException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- ElementNotInteractable Exception for " + elementType + " "+ customElement + "\n");
        }catch  (Exception e){
            e.printStackTrace();
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to click on " + elementType + " "+ customElement + "\n");
        }
    }

    public void clickTextField(){
        click("text field");
    }

    public void clickRadio(){
        click("radio button");
    }

    public void clickButton(){
        click("button");
    }

    public void clickCheckBox(){
        click("check box item");
    }

    public void jsClick(WebDriver driver){
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Clicking on " + eleName + " using JsExecutor");
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", customElement);
//            ReportTrail.info("Clicked on " + eleName + " using JsExecutor");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: --  Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to click on " + customElement);
        }
    }

    public boolean isDisplayed(){
        boolean check = false;
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Checking if the element " + eleName + " is displayed");
            check = customElement.isDisplayed();
            if(check){
                ReportTrail.info("The element " + eleName + " is displayed");
                return true;
            }else {
                ReportTrail.info("The element with locator " + customElement + " is NOT displayed");
            }
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement + "\n ");
        }catch (StaleElementReferenceException ne){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Element became stale " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to find  " + customElement);
        }
        return check;
    }

    public boolean isEnabled(){
        boolean check = false;
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Checking if the element " + eleName + " is enabled");
            check = customElement.isEnabled();
            if(check){
                ReportTrail.info("The element " + eleName + " is enabled");
                return true;
            }else {
                ReportTrail.error("The element with locator " + customElement + " is NOT enabled");
            }
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch (StaleElementReferenceException ne){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Element became stale " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to check if element is enabled for the locator:   " + customElement);
        }
        return check;
    }

    public boolean isChecked(){
        boolean check = false;
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Verifying if the element " + eleName + " is checked");
            String value =  customElement.getAttribute("class");
            check = value.contains("checked");
            if(check){
                ReportTrail.info("The element " + elementName() + " is checked");
                return true;
            }else {
                ReportTrail.error("The element with locator " + customElement + " is NOT checked");
            }
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: --  Element not found with the locator " + customElement);
        }catch (StaleElementReferenceException ne){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Element became stale " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Click intercepted exception for " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to find  " + customElement);
        }
        return check;
    }

    public void selectByIndex(int index){
        try{
            waitUntilClickable(customElement);
            String eleName = elementNameDropdown();
            ReportTrail.info("Selecting item with index as " + index + " from " + eleName + " dropdown");
            Select selector = new Select(customElement);
            selector.selectByIndex(index);
//            ReportTrail.info("Selected item with index as " + index + " from " + eleName + " dropdown");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ece.printStackTrace();
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to select the item with index as " + index + " from " + customElement );
        }catch  (Exception e){
            e.printStackTrace();
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to select the item with index as " + index + " from " + customElement);
        }
    }

    public void selectByVisibleText(String itemName){
        try{
            waitUntilClickable(customElement);
            String eleName = elementNameDropdown();
            ReportTrail.info("Selecting " + itemName + " from " + eleName + " dropdown");
            Select selector = new Select(customElement);
            selector.selectByVisibleText(itemName);
//            ReportTrail.info("Selected " + itemName + " from " + eleName + " dropdown");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- select the item " + itemName + " from " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to select the item " + itemName + " from " + customElement);
        }
    }

    public void selectByValue(String itemValue){
        try{
            waitUntilClickable(customElement);
            String eleName = elementNameDropdown();
            ReportTrail.info("Selecting " + itemValue + " from " + eleName + " dropdown");
            Select selector = new Select(customElement);
            selector.selectByVisibleText(itemValue);
//            ReportTrail.info("Selected " + itemValue + " from " + eleName + " dropdown");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to select the item " + itemValue + " from " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to select the item " + itemValue + " from " + customElement);
        }
    }

    public String getCurrentSelection(){
        String value = null;
        try{
            waitUntilClickable(customElement);
            String eleName = elementName();
            ReportTrail.info("Getting current selected option from " + eleName);
            Select selector = new Select(customElement);
            value =  selector.getFirstSelectedOption().getText();
//            ReportTrail.info("Selected value for " + eleName + " is " + value);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get current selected option from " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get current selected option from " + customElement);
        }
        return value;
    }

    public String getText(){
        String value = null;
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Getting text value from " + elementName());
            value =  customElement.getText();
//            ReportTrail.info("text value from " + elementName() + " is " + value);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get text from " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get text from " + customElement);
        }
        return value;
    }

    public String getAttribute(String attribute){
        String value = null;
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Getting " + attribute + "  value from " + elementName());
            value =  customElement.getAttribute(attribute);
            ReportTrail.info(attribute + " value from " + elementName() + " is " + value);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        } catch (ElementClickInterceptedException ece){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get " + attribute + " from " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to get " + attribute + " from " + customElement);
        }
        return value;
    }

    public void type(String textValue){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Entering the value as  " + textValue + " in text field " + elementName());
            customElement.sendKeys(textValue);
//            ReportTrail.info("Entered the value as  " + textValue + " in text field");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to enter the text value as " + textValue + " in " + customElement);
        }
    }

    public void keyPress(CharSequence... keysToSend){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Pressing the key  " + keysToSend );
            customElement.sendKeys(keysToSend);
//            ReportTrail.info("Pressed the key  " + keysToSend );
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to Press the key " + keysToSend + " in " + customElement);
        }
    }

    public void typeAndDownEnterToSelect(String textValue){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Entering the value as  " + textValue + " in text field");
            customElement.sendKeys(textValue);
            customElement.sendKeys(Keys.ARROW_DOWN);
            customElement.sendKeys(Keys.ENTER);
//            ReportTrail.info("Entered the value as  " + textValue + " in text field");
            selectByVisibleText(textValue);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to enter the text value as " + textValue + " in " + customElement);
        }
    }

    public void typeAndPressEnter(String textValue){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Entering the value as  " + textValue + " in text field");
            customElement.sendKeys(textValue);
            customElement.sendKeys(Keys.ENTER);
//            ReportTrail.info("Entered the value as  " + textValue + " in text field");
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to enter the text value as " + textValue + " in " + customElement);
        }
    }

    public void typeAndPressEnterToSelect(String textValue){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Entering the value as  " + textValue + " in text field");
            customElement.sendKeys(textValue);
            customElement.sendKeys(Keys.ENTER);
//            ReportTrail.info("Entered the value as  " + textValue + " in text field");
            selectByVisibleText(textValue);
        }catch (NoSuchElementException ne){
            ReportTrail.info("\n  >>>>>>>>>  \n ERROR: -- Element not found with the locator " + customElement);
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Unable to enter the text value as " + textValue + " in " + customElement);
        }
    }

    public void clear(){
        try{
            waitUntilClickable(customElement);
            ReportTrail.info("Clearing the value in text field ");
            customElement.clear();
//            ReportTrail.info("Cleared the value in text field ");
        }catch (NoSuchElementException ne){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Not able to clear the text field");
        }catch  (Exception e){
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Not able to clear the text field");
        }
    }

    public void clearAndType(String textValue){
        clear();
        type(textValue);
    }

    private String elementName(){
        String name = "";
        try{
            name = this.customElement.getText();
        }catch (Exception e){
            try{
                if(name.equals("") || name.equals(null)){
                    if (name.equals("") || name.equals((Object)null)) {
                        try{
                            name = this.customElement.getAccessibleName();
                        }catch (UnsupportedCommandException ue){
                            name = this.customElement.getAttribute("name");
                            if(name.equals("") || name.equals(null)){
                                name = "";
                            }
                        }
                    }

                }
            }catch (Exception ex){
                //do nothing
            }
        }


        return name;
    }

    private String elementNameDropdown(){
        String name = this.customElement.getAttribute("name");
        if(name.equals("") || name.equals(null)){
                name = "";
        }
        return name;
    }

    public WebElement getWrappedElement(){
        return customElement;
    }

    private static void waitUntilClickable(WebElement el) {
        WebDriver driver = OmniDriver.getDriver();
        int timeoutInSeconds = WaitForElement.shortTime;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(driver1 -> ExpectedConditions.elementToBeClickable(el).apply(driver));
        } catch (Exception e) {
            ReportTrail.error("\n  >>>>>>>>>  \n ERROR: -- Encountered error while waiting for element to be clickable whose locator is " + el);
        }
    }


}
