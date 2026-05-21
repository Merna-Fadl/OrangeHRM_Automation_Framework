package tests;

import Pages.RecruitmentPage;
import Utils.ConfigReader;
import Utils.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("OrangeHRM Management")
@Feature("Recruitment - Candidate Management")
public class RecruitmentTest extends BaseTest {
    @DataProvider(name = "recruitmentData")
    public Object[][] getRecruitmentData() {
        // نستخدم الميثود الجديدة اللي عملناها في الـ JsonReader
        return JsonReader.getRecruitmentJsonData("src/test/resources/recruitmentData.json");
    }
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify adding a new candidate from JSON data and uploading their resume")
    @Test(dataProvider = "recruitmentData", description = "Add Candidate via JSON Flow")
    public void testAddCandidateFlow(String fName, String mName, String lName, String email, String vacancy, String cv){

        loginPage.enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .clickLogin();
        new RecruitmentPage(driver)
                .navigateToRecruitment()
                .fillCandidateInfo(fName, mName, lName, email, vacancy)
                .uploadResume(cv)
                .saveCandidate()
                .validateSuccessMessage()
                .searchForCandidate(fName)
                .deleteCandidate()
                .validateDeletionSuccess();
    }

}
