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
        // بنحدد مسار ملف الـ JSON اللي فيه بيانات الموظفين
        return JsonReader.getJsonData("src/test/resources/testData.json");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a new employee can be added successfully with login credentials")
    @Test(dataProvider = "employeeData", description = "Add New Employee Flow")
    public void testAddNewEmployee(String fName, String mName, String lName, String id, String user, String pass) {

        // 1. تسجيل الدخول (باستخدام الـ Fluent Design)
        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin()
                .assertSuccessfulLogin();

        // 2. التعامل مع صفحة الـ PIM
        PIMPage pimPage = new PIMPage(driver);

        pimPage.navigateToPIM()            // الضغط على PIM في القائمة الجانبية
                .navigateToAddEmployee()     // الضغط على Add Employee من فوق
                .fillFullName(fName, mName, lName)
                .fillEmployeeId(id)
                .setLoginCredentials(user, pass)
                .clickSave()
                .validateEmployeeSaved()   // التأكد من ظهور رسالة النجاح
                // 3. Search & Verify Flow
                .searchForAddedEmployee()     // يرجع للـ Employee List ويبحث بالـ ID
                .validateEmployeeInTable()  // يتأكد إن الـ ID ظاهر في أول صف في الجدول
                .deleteSelectedEmployee(id)
                .validateDeletionSuccess();
    }

    }
