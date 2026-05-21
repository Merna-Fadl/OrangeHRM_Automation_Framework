package DriverManager;

import Utils.ElementActions;
import Utils.LogsUtil;
import Utils.Waits;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

public class GUIDriver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver(String browserName) {
        setDriver(browserName);
    }

    // لإنشاء الدريفير
    private void setDriver(String browserName) {
        driverThreadLocal.set(DriverManager.BrowserFactory.getBrowser(browserName));
    }

    // الميثود الأساسية لجلب الـ WebDriver
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            LogsUtil.error("Driver is null! Make sure it's initialized.");
            fail("Driver is null");
        }
        return driverThreadLocal.get();
    }
    // فتح URL بشكل واضح
    public void navigateTo(String url) {
        getDriver().get(url);
    }

    // ميثود لربط الأكشنز بالدرايفر الحالي
    public ElementActions elementActions() {
        return new ElementActions(this); // نمرر 'this' لأن الـ Constructor يتوقع GUIDriver
    }
    public Waits waits(){
        return new Waits(this);
    }

    // لغلق المتصفح وتنظيف الـ ThreadLocal
    public void quit() {
        if (driverThreadLocal.get() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
            LogsUtil.info("Driver closed and ThreadLocal removed.");
        }
    }
}
