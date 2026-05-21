package tests;

import Utils.ConfigReader;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        // بننادي الـ Methods ورا بعض (Chain) بفضل الـ return this اللي عملتيه
        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin()
                .assertSuccessfulLogin();
    }
}