package Pages;

import DriverManager.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AdminPage {
    private GUIDriver driver;

    public AdminPage(GUIDriver driver) {
        this.driver = driver;
    }

    // ---  Locators  ---
    private By adminSideMenu = By.xpath("//span[text()='Admin']");
    private By usernameSearchField = By.xpath("//div[contains(@class,'oxd-input-group') and .//label[text()='Username']]//input");
   // private By usernameSearchField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By searchButton = By.xpath("//button[@type='submit' and contains(.,'Search')]");
   // private By searchButton = By.xpath("//button[@type='submit']");
    private By addButton = By.xpath("//button[contains(.,'Add')]");
    private By userRoleDropdown = By.xpath("(//div[@class='oxd-select-text--after'])[1]");
    private By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private By statusDropdown = By.xpath("(//div[@class='oxd-select-text--after'])[2]");
    private By usernameInput = By.xpath("//div[contains(@class,'oxd-input-group') and .//label[text()='Username']]//input");
   // private By usernameInput = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By passwordInput = By.xpath("(//input[@type='password'])[1]");
    private By confirmPasswordInput = By.xpath("(//input[@type='password'])[2]");
    private By saveButton = By.xpath("//button[@type='submit' and contains(.,'Save')]");
    //private By saveButton = By.xpath("//button[@type='submit']");

    // لوكيتور الـ Hint اللي بيظهر (مهم جداً)
    private By firstHintResult = By.xpath("//div[@role='listbox']//div[1]");
    // لوكيتور الرسالة اللي بتظهر لما اليوزر يكون موجود
    private By usernameExistsError = By.xpath("//span[contains(@class,'oxd-input-group__message') and text()='Already exists']");

    // --- الـ Methods ---


    @Step("Navigate to Admin Module")
    public AdminPage navigateToAdmin() {
        driver.elementActions().clickElement(adminSideMenu);
        return this;
    }

    @Step("Fill Add User Form")
    public AdminPage fillAddUserForm(String role, String empName, String status, String user, String pass) {

        driver.elementActions().clickElement(addButton);

        // 1. اختيارات الـ Dropdowns
        selectFromDropdown(userRoleDropdown, role);

        // 2. التعامل مع الـ Employee Name (الحل الصح)
        driver.waits().waitForElementVisible(employeeNameInput);

        // اكتبي أول 3 حروف بس
        String partialName = empName.substring(0, 3);
        driver.elementActions().sendKey(employeeNameInput, partialName);

        // انتظري الـ Hint اللي فيه الاسم الكامل واضغطيه
        By specificHint = By.xpath("//div[@role='listbox']//*[contains(text(),'" + empName + "')]");
        driver.waits().waitForElementVisible(specificHint);
        driver.elementActions().clickElement(specificHint);


        // 3. كملي باقي الفورم
        selectFromDropdown(statusDropdown, status);
        driver.elementActions().sendKey(usernameInput, user);
        driver.elementActions().sendKey(passwordInput, pass);
        driver.elementActions().sendKey(confirmPasswordInput, pass);

        // 4. الحفظ والانتظار
        driver.elementActions().clickElement(saveButton);
        driver.waits().waitForElementVisible(By.xpath("//h5[text()='System Users']"));
       // driver.waits().waitForElementVisible(By.id("oxd-toaster_1"));

        return this;
    }

    private void selectFromDropdown(By dropdownLocator, String optionText) {
        driver.elementActions().clickElement(dropdownLocator);
        driver.elementActions().clickElement(By.xpath("//div[@role='listbox']//*[text()='" + optionText + "']"));
    }

    @Step("Search for user: {user}")
    public AdminPage searchForUser(String user) {
        driver.elementActions().sendKey(usernameSearchField, user);
        driver.elementActions().clickElement(searchButton);
        return this;
    }
    @Step("Verify User is created successfully")
    public AdminPage verifyUserIsCreated(String username) {
        // استني الجدول يظهر فيه الداتا بناءً على البحث اللي حصل قبل الميثود دي
        By recordFound = By.xpath("//div[@class='oxd-table-card']//div[contains(.,'" + username + "')]");
        driver.waits().waitForElementVisible(recordFound);
        return this;
    }
    // for only Negative: Verify validation message for duplicate username
    @Step("Click Add button")
    public AdminPage clickAddButton() {
        driver.elementActions().clickElement(addButton);
        return this;
    }
    @Step("Enter username without saving: {user}")
    public AdminPage enterJustUsername(String user) {
        driver.elementActions().sendKey(usernameInput, user);
        // ممكن تضغطي Tab أو كليك في أي حتة فاضية عشان الـ Validation يظهر
        return this;
    }
    @Step("Check if 'Already exists' error appears for username")
    public String getUsernameExistsErrorMessage() {
        return driver.waits().waitForElementVisible(usernameExistsError).getText();
    }
}