package Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
public class FilesUtils {
    private FilesUtils(){super();}

    public static File getLetestFile(String folderPath){
        File folder = new File(folderPath);
        File[] files =folder.listFiles();
        if (files == null || files.length ==0){
            LogsUtil.warn("no file found in directory "+ folderPath);
            return null;
        }
        File latesFile = files[0];
        for (File file :files){
            if (file.lastModified() > latesFile.lastModified()){
                latesFile = file;
            }
        }
        return latesFile;
    }



    public static void deleteFiles(File dirPath){
        if (dirPath == null || !dirPath.exists()){
            LogsUtil.warn("Directory does not exist: " + dirPath);
            return;
        }
        File[] filesList = dirPath.listFiles();
        if (filesList == null){
            LogsUtil.warn("Failed to list files in : " + dirPath);
            return;
        }
        for (File file : filesList){
            if (file.isDirectory()){
                deleteFiles(file);
            }
            else {
                try{
                    Files.delete(file.toPath());
                }catch (IOException e){
                    LogsUtil.error("failed to delete file: " + file);
                }
            }
        }

    }


    public static void cleanDirectory(File file){
        try {
            FileUtils.deleteQuietly(file);
        }
        catch (Exception e){
            LogsUtil.error(e.getMessage());

        }

    }
}
