package Pages;

import DriverManager.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;

public class RecruitmentPage {
    private GUIDriver driver;
    public RecruitmentPage (GUIDriver driver){
        this.driver =driver;
    }
    // Locators
    // 1. Navigation & Main Action Buttons
    private By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private By addButton = By.xpath("//button[text()=' Add ']");
    private By saveButton = By.xpath("//button[@type='submit']");

    // 2. Candidate Info Form Fields
    private By firstName = By.name("firstName");
    private By middleName = By.name("middleName");
    private By lastName = By.name("lastName");
    private By email = By.xpath("(//input[@placeholder='Type here'])[1]");
    private By contactNumber = By.xpath("(//input[@placeholder='Type here'])[2]");
    private By resumeUpload = By.xpath("//input[@type='file']"); 
    private By vacancyDropdown = By.xpath("//div[@class='oxd-select-text-input']");
    private By keywordsField = By.xpath("//input[@placeholder='Enter comma seperated words...']");
    private By notesField = By.xpath("//textarea[@placeholder='Type here']");

    // 3. Search & Autocomplete Elements
    private By candidatesTab = By.xpath("//a[text()='Candidates']");
    private By candidateNameSearchField = By.xpath("//input[@placeholder='Type for hints...']");
    private By firstHintOption = By.xpath("//div[@role='listbox']//div[1]");
    private By searchButton = By.xpath("//button[@type='submit']");

    // 4. Table Result & Deletion Actions
    private By deleteSelectedBtn = By.xpath("//button[contains(.,'Delete Selected')]");
    private By deleteIcon = By.xpath("//i[@class='oxd-icon bi-trash']");
    private By confirmDeleteBtn = By.xpath("//button[contains(.,'Yes, Delete')]");
    private By successMessage = By.xpath("//p[text()='Successfully Saved']");
    private By successToastMessage = By.xpath("//div[@id='oxd-toaster_1']//p[contains(@class, 'oxd-text--toast-message')]");
    //method
    public RecruitmentPage navigateToRecruitment() {
        driver.waits().waitForElementClickable(recruitmentMenu);
        driver.elementActions().findElement(recruitmentMenu).click();
        driver.waits().waitForElementClickable(addButton);
        driver.elementActions().findElement(addButton).click();
        return this;
    }
    @Step("Fill Candidate Basic Info")
    public RecruitmentPage fillCandidateInfo(String fName, String mName, String lName, String emailAddr,String vacancyName) {
        driver.waits().waitForElementVisible(firstName);
        driver.elementActions().findElement(firstName).sendKeys(fName);
        driver.elementActions().findElement(middleName).sendKeys(mName);
        driver.elementActions().findElement(lastName).sendKeys(lName);
        driver.elementActions().findElement(email).sendKeys(emailAddr);
        selectVacancy(vacancyName);
        return this;
    }
    @Step("Select Vacancy from Custom Dropdown")
    private void selectVacancy(String vacancyName) {
        driver.elementActions().findElement(vacancyDropdown).click();
        By vacancyOption = By.xpath("//div[@role='listbox']//*[contains(text(), '" + vacancyName + "')]");
        driver.elementActions().findElement(vacancyOption).click();
    }

    @Step("Upload Resume File")
    public RecruitmentPage uploadResume(String fileName) {
        File file = new File("src/test/resources/" + fileName);
        String absolutePath = file.getAbsolutePath();

        System.out.println("Uploading file from path: " + absolutePath);

        driver.elementActions().findElement(resumeUpload).sendKeys(absolutePath);
        return this;
    }

    public RecruitmentPage saveCandidate() {
        driver.elementActions().findElement(saveButton).click();
        return this;
    }
    @Step("Validate Success Message Displayed")
    public RecruitmentPage validateSuccessMessage() {
        WebElement successToast = driver.waits().waitForElementVisible(successMessage);

        Assert.assertNotNull(successToast, "CRITICAL: Success message didn't appear! Candidate might not be saved.");

        org.testng.Assert.assertTrue(successToast.isDisplayed(), "Success message is not visible on the UI!");

        return this;
    }

    @Step("Search for candidate by name with Autocomplete: {name}")
    public RecruitmentPage searchForCandidate(String name) {
        driver.waits().waitForElementClickable(candidatesTab);
        driver.elementActions().clickElement(candidatesTab);
        driver.elementActions().sendKey(candidateNameSearchField, name);
        driver.waits().waitForElementVisible(firstHintOption);
        driver.elementActions().clickElement(firstHintOption);
        driver.elementActions().clickElement(searchButton);

        driver.waits().waitForElementVisible(deleteIcon);
        return this;
    }

    @Step("Delete the found candidate")
    public RecruitmentPage deleteCandidate() {
        driver.elementActions().clickElement(deleteIcon);
        driver.elementActions().clickElement(confirmDeleteBtn);

        return this;
    }
    @Step("Validating deletion success message")
    public RecruitmentPage validateDeletionSuccess() {
        WebElement toast = driver.waits().waitForElementVisible(successToastMessage);

        String messageText = toast.getText();
        System.out.println("Appearance of success message: " + messageText);

        Assert.assertTrue(messageText.contains("Successfully Deleted"),
                "Success message was not as expected! Found: " + messageText);

        return this;
    }

}


