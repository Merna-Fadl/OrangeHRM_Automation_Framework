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
    // تعريف الـ Logger
    private static final Logger logger = LogManager.getLogger(ElementActions.class);
    public ElementActions(GUIDriver driver){
        this.driver = driver;
        waits = new Waits(driver);
        scrolling = new Scrolling(driver);
    }
    // 1. ميثود الـ Allure الخاصة بالصور (دي اللي بتعرض الصور في التقرير)
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
            // إضافة Log عند النجاح
            LogsUtil.info("data entered: ",key, "in the field: ", locator.toString());
        } catch (Exception e) {
            // إضافة Log عند الفشل
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
                // محاولة الضغط العادي
                findElement(locator).click();
            } catch (Exception e) {
                // لو فشل (زي حالة Firefox headless)، نستخدم JS كـ Backup
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
            // إنشاء الفولدر لو مش موجود
            File folder = new File(System.getProperty("user.dir") + "/screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 1. تحويل الـ Driver لـ TakeScreenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            // 2. التقاط الصورة كملف
            File source =takesScreenshot.getScreenshotAs(OutputType.FILE);
            // 3. تحديد المسار (فولدر اسمه screenshots)
            String destination = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";

             File finalDestination = new File(destination);
            // 4. حفظ الملف
            FileHandler.copy(source, finalDestination);
            // نداء ميثود الـ Allure عشان تبعت الصورة للتقرير برضه
            saveScreenshotToAllure(screenshotName, driver);
            // لوج للتأكيد إن الصورة اتسجلت
            LogsUtil.info("Screenshot saved at: " + destination);
            return destination;
        } catch (IOException e){
            System.out.println("error when take screenshot  "+ e.getMessage() );
            return null;
        }
    }


}
