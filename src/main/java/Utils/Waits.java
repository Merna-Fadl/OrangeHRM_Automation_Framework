package Utils;

import DriverManager.GUIDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions; // ضيفي الـ Import ده
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waits {
    private GUIDriver driver;
    public Waits(GUIDriver driver) { this.driver = driver; }

    // wait for element present
    public WebElement waitForElementPresent(By locator) {
        return new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // wait for element visible
    public WebElement waitForElementVisible(By locator) {
        return new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // wait for element clickable
    public WebElement waitForElementClickable(By locator) {
        return new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
}