package Pages;
import DriverManager.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;


public class LoginPage {
    private GUIDriver driver;

    public LoginPage(GUIDriver driver){
        this.driver = driver;
    }
    // 1. Locators 
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    
    private By userDropdown = By.xpath("//p[@class='oxd-userdropdown-name']");
    // (Invalid credentials)
    private By errorMessage = By.xpath("//div[@role='alert']//p");
    
    private By inputValidationError = By.xpath("//span[contains(@class,'oxd-input-group__message')]");

    // 2. Methods (Actions) - Fluent Design 
    @Step("Entering username: {user}")
    public LoginPage enterUsername(String user) {
        
        driver.elementActions().sendKey(usernameField,user);
        return this;
    }

    @Step("Entering password")
    public LoginPage enterPassword(String pass) {
        driver.elementActions().sendKey(passwordField, pass);
        return this;
    }

    @Step("Clicking Login button")
    public LoginPage clickLogin() {
        
        driver.elementActions().clickElement(loginButton);
        return this;
    }
    // 3. Validation 

    @Step("Verifying successful login by URL and Header")
    public void assertSuccessfulLogin() {
        
        driver.waits().waitForElementVisible(userDropdown);
        String actualUrl = driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("dashboard"), "Login failed - URL mismatched! Actual: " + actualUrl);

        boolean isUserMenuDisplayed = driver.getDriver().findElement(userDropdown).isDisplayed();
        Assert.assertTrue(isUserMenuDisplayed, "Login failed - User dropdown not displayed!");

        String actualUserName = driver.getDriver().findElement(userDropdown).getText();
        Assert.assertFalse(actualUserName.isEmpty(), "User name is empty! Login might have failed.");
    }

    @Step("Get error message text")
    public String getErrorMessageText() {
        return driver.waits().waitForElementVisible(errorMessage).getText();
    }

    @Step("Get field validation message")
    public String getFieldValidationMessage() {
        return driver.waits().waitForElementVisible(inputValidationError).getText();
    }
}
