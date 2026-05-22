package tests;

import Pages.AdminPage;
import Pages.PIMPage;
import Utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {
    @Test(description = "E2E: Create Employee and then Create Admin User Account")
    public void testUserCreationFlow() {
        
        String firstName = "Merna";
        String middleName = "QA";
        String lastName = "Automation";
        String fullName = firstName + " " + middleName + " " + lastName; // هنجيبه من هنا للـ Admin
        String userName = "merna.test" + System.currentTimeMillis();
        String password = "P@ssword123";

        
        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin()
                .assertSuccessfulLogin();
        PIMPage pim = new PIMPage(driver);
        pim.navigateToPIM()
                .navigateToAddEmployee()
                .fillFullName(firstName, middleName, lastName)
                .fillEmployeeId("M201")
                .clickSave();
        AdminPage admin = new AdminPage(driver);
        admin.navigateToAdmin()
                .fillAddUserForm("ESS", firstName, "Enabled", userName, password)
                .searchForUser(userName)
                .verifyUserIsCreated(userName);
    }

    @Test(description = "Negative: Verify validation message for duplicate username")
    public void testDuplicateUsernameValidation() {
        AdminPage adminPage = new AdminPage(driver);

        // 1. Login
        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin();

        // 2. Navigation & Action
        adminPage.navigateToAdmin()
        .clickAddButton() 
         .enterJustUsername("Admin");


       // adminPage.fillAddUserForm("ESS", "Mer", "Enabled", "Admin", "P@ssword123");

        // 3. Validation
        String errorMsg = adminPage.getUsernameExistsErrorMessage();
        Assert.assertEquals(errorMsg, "Already exists");
    }
    }
