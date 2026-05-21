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
      //  options.addArguments("--start-maximized");              // فتح المتصفح في وضع ملء الشاشة
        options.addArguments("--headless=new");                     // تشغيل بدون واجهة
        options.addArguments("--window-size=1920,1080");        // حجم نافذة معين
        options.addArguments("--incognito");                    // وضع التصفح الخفي
        options.addArguments("--disable-notifications");        // منع الإشعارات
        options.addArguments("--disable-extensions");           // تعطيل الإضافات
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-features=PasswordLeakDetection");
//      options.addArguments("--lang=en");                      // تعيين لغة المتصفح
//      options.addArguments("--ignore-certificate-errors");    // تجاهل أخطاء الشهادات
        options.addArguments("--no-sandbox");                   // مناسب للـ Linux و Docker
        options.addArguments("--disable-dev-shm-usage");        // حل مشاكل الذاكرة في Linux
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return options;
    }
    private static EdgeOptions getEdgeOptions(){
        EdgeOptions option1 = new EdgeOptions();
        option1.addArguments("--headless=new");                     // تشغيل بدون واجهة
        option1.addArguments("--window-size=1920,1080");        // حجم نافذة معين
        option1.addArguments("--no-sandbox");
        option1.addArguments("--disable-gpu");                   // إلغاء الاعتماد على كارت الشاشة لمنع الـ Lag
        option1.addArguments("--disable-dev-shm-usage");        // حل مشاكل استهلاك الذاكرة المشتركة على الويندوز والـ CI

      //  option1.addArguments("--start-maximized");              // فتح المتصفح في وضع ملء الشاشة
        //  options.addArguments("--incognito");                    // وضع التصفح الخفي
        option1.addArguments("--disable-notifications");        // منع الإشعارات
        option1.addArguments("--disable-extensions");           // تعطيل الإضافات
        option1.addArguments("--disable-infobars");
        option1.addArguments("--disable-features=PasswordLeakDetection");
//                options.addArguments("--lang=en");                      // تعيين لغة المتصفح
//                options.addArguments("--ignore-certificate-errors");    // تجاهل أخطاء الشهادات
//                options.addArguments("--no-sandbox");                   // مناسب للـ Linux و Docker
//                options.addArguments("--disable-dev-shm-usage");        // حل مشاكل الذاكرة في Linux
        //options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return option1;
    }
    private static FirefoxOptions getFirefoxOptions(){
        FirefoxOptions option = new FirefoxOptions();
       // option.addArguments("--start-maximized");              // فتح المتصفح في وضع ملء الشاشة
        option.addArguments("--headless");
       //السطر ده هو "السر" عشان يشتغل صح في وضع الـ Headless على بعض النسخ
        option.addArguments("--window-size=1920,1080");
       // option.addArguments("--disable-notifications");        // منع الإشعارات
      //  option.addArguments("--disable-extensions");           // تعطيل الإضافات
      //  option.addArguments("--disable-infobars");
        option.addArguments("--no-sandbox");
        // 🔥 الأسطر السحرية لحل تهنيج وبطء الفايرفوكس في الـ Headless Mode:
        option.addArguments("--disable-gpu");                   // إلغاء الاعتماد على كارت الشاشة لمنع الـ Lag
        option.addArguments("--disable-dev-shm-usage");        // حل مشاكل استهلاك الذاكرة المشتركة على الويندوز والـ CI
        option.addPreference("layers.acceleration.disabled", true); // إيقاف الـ Hardware Acceleration المسؤول عن البطء

        option.addArguments("--disable-features=PasswordLeakDetection");
        // منع الإشعارات والـ Extensions والـ Infobars بالطريقة اللي بيحبها الفايرفوكس
        option.addPreference("dom.webnotifications.enabled", false);
        option.addPreference("geo.enabled", false);
        option.addPreference("extensions.enabledScopes", 0);
//        option.addArguments("--width=1920"); // ضيفي السطر ده
//        option.addArguments("--height=1080");
        option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        option.setAcceptInsecureCerts(true);

        return option;
    }


}

