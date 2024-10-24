package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetCorrectTime.getCorrectTimeToFolderName;

public class CreateFolder
{
    public static String createFolderWithTime(String folderPath)
    {
        int randomIntInRange = ThreadLocalRandom.current().nextInt(1000, 9999);  // [50, 100]
        folderPath = folderPath + identifySystem_String() + getCorrectTimeToFolderName() + "_" + randomIntInRange;
        File file = new File(folderPath);
        file.mkdir();
        return folderPath;
    }
}
