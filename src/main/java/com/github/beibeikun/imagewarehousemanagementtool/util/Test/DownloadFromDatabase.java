package com.github.beibeikun.imagewarehousemanagementtool.util.Test;

import com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;

import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

public class DownloadFromDatabase
{
    public static void downloadFromDatabase(String databasePath, String fileName, String outPath) throws IOException
    {
        // 初始化系统检查器，获取系统信息
        SystemChecker system = new SystemChecker();


        int position = fileName.indexOf("-");
        String fileNamePrefix = fileName.substring(0, position);
        String filePath = databasePath + system.identifySystem_String() + fileNamePrefix + system.identifySystem_String() + fileName + ".zip";

        if (checkFolder(filePath,outPath,false,"",false,"",false))
        {
            outPath = CreateFolder.createFolderWithTime(outPath);
            copyFile(filePath,outPath);
            systemPrintOut("Download:"+fileName,1,0);
        }
    }
}