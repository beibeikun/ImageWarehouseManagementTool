package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;

import static Module.Others.GetCorrectTime.getCorrectTimeToFolderName;

public class CreateFolder
{
    public static String createFolderWithTime(String folderPath)
    {
        SystemChecker system = new SystemChecker();
        folderPath = folderPath + system.identifySystem_String() + getCorrectTimeToFolderName();
        File file = new File(folderPath);
        file.mkdir();
        return folderPath;
    }
}
