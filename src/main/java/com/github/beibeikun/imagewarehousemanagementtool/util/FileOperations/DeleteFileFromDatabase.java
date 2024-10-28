package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;
import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.DeleteTypeCheck.deleteTypeCheck;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch.isDirectoryEmpty;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch.isFileExists;

public class DeleteFileFromDatabase
{
    public static void deleteFileFromDatabase(String destinationFolder, String deleteNum) throws IOException
    {
        //TODO
        if (deleteTypeCheck(deleteNum)) // 完整
        {

        }
        else //单个
        {
            String deletePath = destinationFolder + identifySystem_String() + deleteNum.substring(0, 6) + identifySystem_String() + deleteNum + ".zip";
            String folderPath = destinationFolder + identifySystem_String() + deleteNum.substring(0, 6);
            String thumbnailPath = destinationFolder + identifySystem_String() + "thumbnail" + identifySystem_String() + deleteNum + ".JPG";
            if (isFileExists(deletePath)) //文件存在，执行删除
            {
                DeleteDirectory.deleteFile(deletePath);//删除压缩包
                if (isDirectoryEmpty(folderPath)) // 如果文件夹内已经没有其他文件，则删除文件夹
                {
                    DeleteDirectory.deleteDirectory(new File(folderPath));
                }
                DeleteDirectory.deleteFile(thumbnailPath);//删除压缩图
            }
            else
            {

            }
        }
    }
}
