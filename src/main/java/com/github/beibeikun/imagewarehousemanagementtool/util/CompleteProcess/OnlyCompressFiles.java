package com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess;

import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;

import java.io.IOException;

public class OnlyCompressFiles
{
    public static void onlyCompressFiles(String sourceFolderPath, String targetFolderPath, int imgSize) throws IOException
    {
        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        ImageCompression.compressImgWithFileListUseMultithreading(FileLister.getFileNamesInList(targetFolderPath), imgSize);
    }
}
