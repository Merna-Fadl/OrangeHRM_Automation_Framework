package Utils;

import DriverManager.GUIDriver;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;


public class ElementActions {
    private GUIDriver driver;
    private  Scrolling scrolling;
    private Waits waits;
    private static final Logger logger = LogManager.getLogger(ElementActions.class);
    public ElementActions(GUIDriver driver){
        this.driver = driver;
        waits = new Waits(driver);
        scrolling = new Scrolling(driver);
    }
    @Attachment(value = "{0}", type = "image/png")
    public static byte[] saveScreenshotToAllure(String name, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    public WebElement findElement(By locator){
        return driver.getDriver().findElement(locator);

    }
    @Step("Type [{key}] into element: {locator}")
    public void sendKey(By locator,String key){
        try {
            waits.waitForElementVisible(locator);
            scrolling.ScrollToElement(locator);
            WebElement element = findElement(locator);
            element.clear();
            element.sendKeys(key);
            LogsUtil.info("data entered: ",key, "in the field: ", locator.toString());
        } catch (Exception e) {
            LogsUtil.error("Failed to type " ,key," into element: ",locator.toString() );
            throw e;
        }
    }
    @Step("Click on element: {locator}")
    public void clickElement(By locator) {
        try {
            waits.waitForElementClickable(locator);
            scrolling.ScrollToElement(locator);

            try {
                findElement(locator).click();
            } catch (Exception e) {
                LogsUtil.warn("Normal click failed, trying JavaScript click for: " + locator);
                ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", findElement(locator));
            }

            LogsUtil.info("Successfully clicked on element: ", locator.toString());
        } catch (Exception e) {
            LogsUtil.error("Failed to click on element: ", locator.toString(), ". Error: ", e.getMessage());
            throw e;
        }
    }

    public String getText(By locator){
        try {
            String text = findElement(locator).getText();
            LogsUtil.info("getting text from the element: ",locator.toString(), "Text: ",findElement(locator).getText());

            return text;
        } catch (Exception e) {
            LogsUtil.error("Failed to get text from element: " + locator.toString());
            throw e;
        }
    }
    public static String takeScreenShot(WebDriver driver, String screenshotName){
        try {
            File folder = new File(System.getProperty("user.dir") + "/screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File source =takesScreenshot.getScreenshotAs(OutputType.FILE);
            String destination = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";

             File finalDestination = new File(destination);
            FileHandler.copy(source, finalDestination);
            saveScreenshotToAllure(screenshotName, driver);
            LogsUtil.info("Screenshot saved at: " + destination);
            return destination;
        } catch (IOException e){
            System.out.println("error when take screenshot  "+ e.getMessage() );
            return null;
        }
    }


}
