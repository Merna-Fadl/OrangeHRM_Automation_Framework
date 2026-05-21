package Utils;

import DriverManager.GUIDriver;
import org.testng.*;

import java.io.File;

public class TestListener implements ITestListener, IExecutionListener, IInvokedMethodListener {
    // تعريف المسارات بناءً على هيكلية مشروعك
    File allure_results = new File("test-outputs/allure-results");
    File logs = new File("test-outputs/Logs");
    File screenshots = new File("screenshots");


    @Override
    public void onExecutionStart() {
        LogsUtil.info("--- Test Execution Started ---");
// تنظيف الملفات القديمة لضمان تقارير دقيقة
        FilesUtils.deleteFiles(allure_results);
        FilesUtils.cleanDirectory(logs);
        FilesUtils.cleanDirectory(screenshots);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test Case PASSED: " + result.getName());
    }
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()){
            try{
                CustomSoftAssertion.customAssertAll();
            }catch (AssertionError e){
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(e);
            }
            switch (testResult.getStatus()){
                case ITestResult.SUCCESS ->  ElementActions.takeScreenShot(GUIDriver.getDriver(),"passed - "+ testResult.getName());
                case ITestResult.FAILURE ->  ElementActions.takeScreenShot(GUIDriver.getDriver(),"failed - "+ testResult.getName());
                case ITestResult.SKIP ->  ElementActions.takeScreenShot(GUIDriver.getDriver(),"skipped - "+ testResult.getName());
            }

            // إرفاق الـ Logs بتقرير Allure في نهاية كل ميثود (سواء نجحت أو فشلت)

            AllureUtils.attacheLogsToAllureReport();
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.info("test case "+ result.getName() , "failed");

    }
    @Override
    public void onExecutionFinish() {
        LogsUtil.info("--- Test Execution Finished ---");
    }
}
