package Utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtils {
    private static final String ALLURE_RESULTS_PATH = "test-outputs/allure-results";
    private AllureUtils(){
        super();
    }
    public static  void attacheLogsToAllureReport(){
        try {
            File logFile = FilesUtils.getLetestFile(LogsUtil.LOGS_PATH);
            if (!logFile.exists()){
                LogsUtil.warn("log file does not exist: "+ LogsUtil.LOGS_PATH);

                return;
            }
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
            LogsUtil.info("log  attached to allure report");
        } catch (Exception e){
            LogsUtil.error("failed to attach logs to allure report: " +e.getMessage());
        }
    }
    public static void attachScreenshotToAllure(String screenshotName,String screenshotPath){
        try {
            Allure.addAttachment(screenshotName,Files.newInputStream(Path.of(screenshotPath)));
        } catch (Exception e){
            LogsUtil.error("failed to attach screenshot to allure report: " +e.getMessage());
        }
    }
}
