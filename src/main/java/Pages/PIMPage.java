package Pages;

import DriverManager.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PIMPage {
    private GUIDriver driver;
    private String lastUsedId;
    public PIMPage(GUIDriver driver) { this.driver = driver; }

    // 1. Locators
    // --- Navigation & Menu Elements ---
    private By pimMenuButton = By.xpath("//span[text()='PIM']");
    private By addEmployeeTab = By.linkText("Add Employee");
    private By employeeListTab = By.linkText("Employee List");

    // --- Add Employee Form Fields ---
    private By firstNameField = By.name("firstName");
    private By middleNameField = By.name("middleName");
    private By lastNameField = By.name("lastName");
    private By employeeIdField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By createLoginDetailsSwitch = By.xpath("//span[contains(@class,'oxd-switch-input')]");
    private By usernameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]");
    private By passwordField = By.xpath("(//input[@type='password'])[1]");
    private By confirmPasswordField = By.xpath("(//input[@type='password'])[2]");
    private By saveButton = By.xpath("//button[@type='submit']");



    // --- Search & Filter Elements ---
    private By employeeNameSearchField = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By idSearchField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");

    // --- Table Results & Post-Action Elements ---
    private By successMessage = By.xpath("//div[contains(@class,'oxd-toast-content')]");
    
    private By firstRowIdResult = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][2]");
    private By deleteSelectedBtn = By.xpath("//button[contains(.,'Delete Selected')]");
    private By confirmDeleteBtn = By.xpath("//button[contains(.,'Yes, Delete')]");




    // 2. Methods (Fluent Design)
    @Step("Navigating to PIM Module")
    public PIMPage navigateToPIM() {
        driver.elementActions().clickElement(pimMenuButton);
        return this;
    }
    @Step("Navigating to Add Employee screen")
    public PIMPage navigateToAddEmployee() {
        driver.elementActions().clickElement(addEmployeeTab);
        return this;
    }
    @Step("Filling Name: {first} {middle} {last}")
    public PIMPage fillFullName(String first, String middle, String last) {
        driver.elementActions().sendKey(firstNameField, first);
        driver.elementActions().sendKey(middleNameField, middle);
        driver.elementActions().sendKey(lastNameField, last);
        return this;
    }



    @Step("Clearing default ID and setting Employee ID: {id}")
    public PIMPage fillEmployeeId(String id) {

        WebElement idField = driver.elementActions().findElement(employeeIdField);
        
        idField.sendKeys(Keys.CONTROL + "a");
        idField.sendKeys(Keys.BACK_SPACE);

       
        String uniqueId = id + (int)(Math.random() * 1000);
        
        idField.sendKeys(uniqueId);
        this.lastUsedId = uniqueId;
        System.out.println("Final ID used for registration and search: " + uniqueId);
        return this;
    }

    @Step("Setting Login Credentials with dynamic username")
    public PIMPage setLoginCredentials(String user, String pass) {
        
        String dynamicUser = user + System.currentTimeMillis();
        driver.elementActions().clickElement(createLoginDetailsSwitch);
        
        driver.elementActions().sendKey(usernameField, dynamicUser);
        driver.elementActions().sendKey(passwordField, pass);
        driver.elementActions().sendKey(confirmPasswordField, pass);
        
        System.out.println("Generated Username for this test: " + dynamicUser);

        return this;
    }

    @Step("Saving Employee")
    public PIMPage clickSave() {
        driver.elementActions().clickElement(saveButton);
        return this;
    }

    @Step("Validating Success")
    public PIMPage validateEmployeeSaved() {
        // تأكدي إن الميثود دي بتنتظر ظهور العنصر مش مجرد بتعمل check سريع
        WebElement successToast = driver.waits().waitForElementVisible(successMessage);

        // التأكد إن العنصر مش null (يعني ظهر فعلاً)
        Assert.assertNotNull(successToast, "Employee was NOT saved successfully or success message didn't appear!");

        // للتأكد أكتر إن الرسالة هي المطلوبة (اختياري)
        Assert.assertTrue(successToast.isDisplayed());
        return this;
    }

    @Step("Searching for the added employee by ID")
    public PIMPage searchForAddedEmployee() {
        //  انتظر أولاً حتى يكون التاب قابل للضغط تماماً (حل مشكلة Firefox Headless)
        driver.waits().waitForElementClickable(employeeListTab);
        // 1. الرجوع للقائمة
        driver.elementActions().clickElement(employeeListTab);
        //  انتظر حتى تظهر خانة البحث عن الـ ID قبل الكتابة فيها
        driver.waits().waitForElementVisible(idSearchField);
        // 2. استخدام الـ ID المحفوظ
        driver.elementActions().sendKey(idSearchField, this.lastUsedId);

        // 3. الضغط على Search
        driver.elementActions().clickElement(searchButton);

        // *** الحل الجذري للـ Stale Element ***
        // هنحاول نستنى العنصر يظهر، ولو المتصفح (زي Firefox) مسح العنصر فجأة، هنحاول مرة كمان
        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.waits().waitForElementVisible(firstRowIdResult);
                break; // لو اشتغل تمام اخرج من الـ loop
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
                System.out.println("Attempt " + attempts + ": Element went stale, retrying...");
            }
        }
        return this;
    }
    @Step("Verifying employee exists in the table")
    public PIMPage validateEmployeeInTable() {
        // استني لحد ما الجدول يحمل والنتيجة تظهر
        WebElement result = driver.waits().waitForElementVisible(firstRowIdResult);

        String actualId = result.getText();
        Assert.assertEquals(actualId, this.lastUsedId, "The Employee ID in the table doesn't match the added one!");

        return this;
    }
    @Step("Selecting employee and clicking delete selected")
    public PIMPage deleteSelectedEmployee(String id) {
        // بناء لوكيتور الـ Checkbox بشكل ديناميكي بناءً على الـ ID اللي اتسجل فعلاً
        By dynamicCheckbox = By.xpath("//div[contains(text(),'" + this.lastUsedId + "')]/preceding::span[contains(@class,'oxd-checkbox-input')][1]");
       // By dynamicCheckbox = By.xpath("//div[@role='row' and .//div[text()='" + this.lastUsedId + "']]//i[contains(@class,'oxd-checkbox-input-icon')]");
        // 2. الضغط على الـ Checkbox (زي ما عملتي في صورة 1_4.PNG)
        driver.elementActions().clickElement(dynamicCheckbox);

        // 3. الضغط على زرار Delete Selected الأحمر
        driver.elementActions().clickElement(deleteSelectedBtn);

        // 4. التأكيد من الـ Pop-up
        driver.elementActions().clickElement(confirmDeleteBtn);

        return this;
    }
    @Step("Validating deletion success message")
    public PIMPage validateDeletionSuccess() {
        // الانتظار حتى تظهر رسالة النجاح
        WebElement toast = driver.waits().waitForElementVisible(successMessage);

        // التأكد من أن الرسالة تحتوي على كلمة 'Successfully Deleted'
        String messageText = toast.getText();
        System.out.println("Appearance of success message: " + messageText);

        Assert.assertTrue(messageText.contains("Successfully Deleted"),
                "Success message was not as expected! Found: " + messageText);

        return this;
    }
}
