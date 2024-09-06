package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;

import java.io.File;

import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetCorrectTime.getCorrectTimeToFolderName;

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
