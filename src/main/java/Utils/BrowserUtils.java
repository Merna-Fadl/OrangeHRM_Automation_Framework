package Utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class BrowserUtils {
    public  static void takeScreenshot(WebDriver driver, String name){
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("");
    }
}
