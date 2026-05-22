package DriverManager;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {
    public static WebDriver getBrowser(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions options = getChromeOptions();
                return new ChromeDriver(options);

            case "firefox":
                FirefoxOptions option = getFirefoxOptions();
                return new FirefoxDriver(option);
            default:
                EdgeOptions option1 = getEdgeOptions();
                return new EdgeDriver(option1);

        }
    }


    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
      //  options.addArguments("--start-maximized");              
        options.addArguments("--headless=new");                    
        options.addArguments("--window-size=1920,1080");        
        options.addArguments("--incognito");                    
        options.addArguments("--disable-notifications");        
        options.addArguments("--disable-extensions");           
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-features=PasswordLeakDetection");
//      options.addArguments("--lang=en");                     
//      options.addArguments("--ignore-certificate-errors");    
        options.addArguments("--no-sandbox");                   
        options.addArguments("--disable-dev-shm-usage");        
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return options;
    }
    private static EdgeOptions getEdgeOptions(){
        EdgeOptions option1 = new EdgeOptions();
        option1.addArguments("--headless=new");                    
        option1.addArguments("--window-size=1920,1080");        
        option1.addArguments("--no-sandbox");
        option1.addArguments("--disable-gpu");                  
        option1.addArguments("--disable-dev-shm-usage");       

      //  option1.addArguments("--start-maximized");       
        //  options.addArguments("--incognito");       
        option1.addArguments("--disable-notifications");
        option1.addArguments("--disable-extensions");   
        option1.addArguments("--disable-infobars");
        option1.addArguments("--disable-features=PasswordLeakDetection");
//                options.addArguments("--lang=en");                 
//                options.addArguments("--ignore-certificate-errors"); 
         
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return option1;
    }
    private static FirefoxOptions getFirefoxOptions(){
        FirefoxOptions option = new FirefoxOptions();
       // option.addArguments("--start-maximized");           
        option.addArguments("--headless");
        option.addArguments("--window-size=1920,1080");
       // option.addArguments("--disable-notifications"); 
      //  option.addArguments("--disable-extensions");      
      //  option.addArguments("--disable-infobars");
        option.addArguments("--no-sandbox");
        option.addArguments("--disable-gpu");                 
        option.addArguments("--disable-dev-shm-usage");      
        option.addPreference("layers.acceleration.disabled", true);

        option.addArguments("--disable-features=PasswordLeakDetection");
        option.addPreference("dom.webnotifications.enabled", false);
        option.addPreference("geo.enabled", false);
        option.addPreference("extensions.enabledScopes", 0);
        option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        option.setAcceptInsecureCerts(true);

        return option;
    }


}

