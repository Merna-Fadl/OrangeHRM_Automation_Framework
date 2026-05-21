package tests;

import Pages.LoginPage;
import Utils.ConfigReader;
import org.testng.annotations.*;
import DriverManager.GUIDriver;
import org.openqa.selenium.Dimension;

public class BaseTest {
    protected GUIDriver driver;
    protected LoginPage loginPage;

    @Parameters("browser") // دي بتسمح لـ TestNG يبعت المتصفح من الـ XML
    @BeforeMethod
    public void setUp(@Optional("chrome")String browser) {
        String Browser = System.getProperty("browser", browser);
        driver = new GUIDriver(Browser);

        driver.getDriver().manage().window().setSize(new Dimension(1920, 1080));
       // driver.getDriver().manage().window().maximize();
        driver.getDriver().get(ConfigReader.getProperty("url"));
        loginPage = new LoginPage(driver);
    }
    // ضيفي الميثود دي عشان تفتحي اللينك قبل كل تست من جديد
//    @BeforeMethod
//    public void openUrl() {
//        driver.getDriver().get(ConfigReader.getProperty("url"));
//    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // صَفري العداد الخاص بالـ Thread ده بعد ما يخلص
        ConfigReader.clearTotal();
    }
}