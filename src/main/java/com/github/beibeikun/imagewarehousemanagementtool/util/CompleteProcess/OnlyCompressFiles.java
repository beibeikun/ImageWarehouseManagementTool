package com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess;

import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;

import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

public class OnlyCompressFiles
{
    public static void onlyCompressFiles(String sourceFolderPath, String targetFolderPath, int imgSize) throws IOException
    {
        if (!checkFolder(sourceFolderPath,targetFolderPath,false,"",false,"",false))
        {
            systemPrintOut("Invalid path detected, terminating the task",2,0);
            systemPrintOut("",0,0);
            return;
        }
        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        ImageCompression.compressImgWithFileListUseMultithreading(FileLister.getFileNamesInList(targetFolderPath), imgSize);
    }
}
