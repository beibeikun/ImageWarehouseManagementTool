package com.github.beibeikun.imagewarehousemanagementtool.util.process;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;
import com.github.beibeikun.imagewarehousemanagementtool.util.wordflow.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;

import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

public class OnlyCompressFiles
{
    public static void onlyCompressFiles(String sourceFolderPath, String targetFolderPath, int imgSize) throws IOException
    {
        if (!checkFolder(sourceFolderPath,false,"",targetFolderPath,false, printOutMessage.NULL,false,printOutMessage.NULL))
        {
            systemPrintOut(printOutMessage.INVALID_PATH_STOP_TASK,2,0);
            systemPrintOut(printOutMessage.NULL,0,0);
            return;
        }
        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        ImageCompression.compressImgWithFileListUseMultithreading(FileLister.getFileNamesInList(targetFolderPath), imgSize);
    }
}
