package tests;

import Utils.ConfigReader;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginNegativeTests extends BaseTest {

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidData() {
        return new Object[][]{
                {"WrongUser", "admin123", "Invalid credentials"},
                {"Admin", "WrongPass", "Invalid credentials"},
                {"Invalid", "Invalid", "Invalid credentials"}
        };
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Verify error message with invalid credentials")
    @Test(dataProvider = "invalidCredentials")
    public void testLoginWithInvalidData(String user, String pass, String expectedError) {
        loginPage.enterUsername(user)
                .enterPassword(pass)
                .clickLogin();

        String actualError = loginPage.getErrorMessageText();
        Assert.assertEquals(actualError, expectedError);
    }

    @Severity(SeverityLevel.MINOR)
    @Description("Verify 'Required' message when fields are empty")
    @Test
    public void testLoginWithEmptyFields() {
        
        
        loginPage.clickLogin();

        String validationMsg = loginPage.getFieldValidationMessage();
        Assert.assertEquals(validationMsg, "Required");
    }
}
