package tests;

import Pages.LoginPage;
import Utils.ConfigReader;
import org.testng.annotations.*;
import DriverManager.GUIDriver;
import org.openqa.selenium.Dimension;

public class BaseTest {
    protected GUIDriver driver;
    protected LoginPage loginPage;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome")String browser) {
        String Browser = System.getProperty("browser", browser);
        driver = new GUIDriver(Browser);

        driver.getDriver().manage().window().setSize(new Dimension(1920, 1080));
       // driver.getDriver().manage().window().maximize();
        driver.getDriver().get(ConfigReader.getProperty("url"));
        loginPage = new LoginPage(driver);
    }
    

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ConfigReader.clearTotal();
    }
}
