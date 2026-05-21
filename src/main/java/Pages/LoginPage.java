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
    // 1. Locators (مواقع العناصر في صفحة OrangeHRM)
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    // العنصر ده بيظهر بس لما اللوجين ينجح (اسم المستخدم فوق عاليمين)
 //   private By userDropdown = By.className("oxd-userdropdown-name");
    private By userDropdown = By.xpath("//p[@class='oxd-userdropdown-name']");
    // لوكيتور رسالة الخطأ (Invalid credentials)
    private By errorMessage = By.xpath("//div[@role='alert']//p");
    // لوكيتور رسالة الـ Required (بتظهر تحت الفيلد نفسه)
    private By inputValidationError = By.xpath("//span[contains(@class,'oxd-input-group__message')]");

    // 2. Methods (Actions) - Fluent Design (بتعمل return this عشان نربط الخطوات)
    @Step("Entering username: {user}")
    public LoginPage enterUsername(String user) {
        // بننادي ميثود الـ sendKey من الـ ElementActions اللي عندك
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
        // بنستخدم ميثود الـ clickElement من كلاس الـ Utils بتاعك
        driver.elementActions().clickElement(loginButton);
        return this;
    }
    // 3. Validation (التحقق من نجاح العملية)

    @Step("Verifying successful login by URL and Header")
    public void assertSuccessfulLogin() {
        // استخدمي الـ waits اللي عندك في الـ ElementActions
        driver.waits().waitForElementVisible(userDropdown);
        // التحقق الأول: إن الرابط الجديد فيه كلمة dashboard (لأن ده ERP حقيقي)
        String actualUrl = driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("dashboard"), "Login failed - URL mismatched! Actual: " + actualUrl);

        // التحقق الثاني: إن الـ Dropdown بتاع اليوزر ظهر (معناه إننا جوه السيستم)
        boolean isUserMenuDisplayed = driver.getDriver().findElement(userDropdown).isDisplayed();
        Assert.assertTrue(isUserMenuDisplayed, "Login failed - User dropdown not displayed!");

        // سحب النص الحقيقي من الـ Dropdown للتحقق منه
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
