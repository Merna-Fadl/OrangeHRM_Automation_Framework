package Utils;

import DriverManager.GUIDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Scrolling {
    private GUIDriver driver;
    public Scrolling(GUIDriver driver) { this.driver = driver; }

    public void ScrollToElement(By locator) {
        WebElement element = driver.getDriver().findElement(locator);
        // استخدام JavaScript للسكرول في منتصف الشاشة بالضبط
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }
}