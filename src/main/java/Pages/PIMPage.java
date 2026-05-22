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
        
        WebElement successToast = driver.waits().waitForElementVisible(successMessage);

        Assert.assertNotNull(successToast, "Employee was NOT saved successfully or success message didn't appear!");
        Assert.assertTrue(successToast.isDisplayed());
        return this;
    }

    @Step("Searching for the added employee by ID")
    public PIMPage searchForAddedEmployee() {
        driver.waits().waitForElementClickable(employeeListTab);
        
        driver.elementActions().clickElement(employeeListTab);
        
        driver.waits().waitForElementVisible(idSearchField);
        
        driver.elementActions().sendKey(idSearchField, this.lastUsedId);

        
        driver.elementActions().clickElement(searchButton);

   
        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.waits().waitForElementVisible(firstRowIdResult);
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
                System.out.println("Attempt " + attempts + ": Element went stale, retrying...");
            }
        }
        return this;
    }
    @Step("Verifying employee exists in the table")
    public PIMPage validateEmployeeInTable() {
        
        WebElement result = driver.waits().waitForElementVisible(firstRowIdResult);

        String actualId = result.getText();
        Assert.assertEquals(actualId, this.lastUsedId, "The Employee ID in the table doesn't match the added one!");

        return this;
    }
    @Step("Selecting employee and clicking delete selected")
    public PIMPage deleteSelectedEmployee(String id) {
        
        By dynamicCheckbox = By.xpath("//div[contains(text(),'" + this.lastUsedId + "')]/preceding::span[contains(@class,'oxd-checkbox-input')][1]");
    
        driver.elementActions().clickElement(dynamicCheckbox);
        driver.elementActions().clickElement(deleteSelectedBtn);

        driver.elementActions().clickElement(confirmDeleteBtn);

        return this;
    }
    @Step("Validating deletion success message")
    public PIMPage validateDeletionSuccess() {
        WebElement toast = driver.waits().waitForElementVisible(successMessage);

        String messageText = toast.getText();
        System.out.println("Appearance of success message: " + messageText);

        Assert.assertTrue(messageText.contains("Successfully Deleted"),
                "Success message was not as expected! Found: " + messageText);

        return this;
    }
}
