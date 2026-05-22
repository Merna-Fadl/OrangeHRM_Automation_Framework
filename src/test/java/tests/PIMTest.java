package tests;

import Utils.ConfigReader;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Pages.PIMPage;
import Utils.JsonReader;

@Epic("OrangeHRM Management")
@Feature("PIM - Employee Management")
public class PIMTest extends BaseTest {

    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        return JsonReader.getJsonData("src/test/resources/testData.json");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new employee can be added successfully with login credentials")
    @Test(dataProvider = "employeeData", description = "Add New Employee Flow")
    public void testAddNewEmployee(String fName, String mName, String lName, String id, String user, String pass) {

        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin()
                .assertSuccessfulLogin();

        PIMPage pimPage = new PIMPage(driver);

        pimPage.navigateToPIM()            
                .navigateToAddEmployee()     
                .fillFullName(fName, mName, lName)
                .fillEmployeeId(id)
                .setLoginCredentials(user, pass)
                .clickSave()
                .validateEmployeeSaved()
                //  Search & Verify Flow
                .searchForAddedEmployee()   
                .validateEmployeeInTable()  
                .deleteSelectedEmployee(id)
                .validateDeletionSuccess();
    }

    }
