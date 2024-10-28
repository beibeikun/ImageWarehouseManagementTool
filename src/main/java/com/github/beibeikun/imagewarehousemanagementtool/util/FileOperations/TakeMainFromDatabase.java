package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.data.ArrayExtractor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ReadCsvFile;

import java.io.File;
import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

/**
 * 从数据库中获取文件的类
 */
public class TakeMainFromDatabase
{

    /**
     * 从数据库中获取文件，并在特定条件下将文件复制到指定文件夹中
     *
     * @param csvpath      包含映射关系的 CSV 文件路径
     * @param databasepath 数据库文件的根路径
     * @param folderpath   目标文件夹路径
     */
    public static void takeMainFromDatabase(String csvpath, String databasepath, String folderpath) throws IOException
    {
        systemPrintOut("Start to take main img from database", 3, 0);
        folderpath = CreateFolder.createFolderWithTime(folderpath);
        String[] fileNameList = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(csvpath), 0);
        for (int x = 0; x < fileNameList.length; x++)
        {
            File fileCheck = new File(databasepath + identifySystem_String() + "thumbnail" + identifySystem_String() + fileNameList[x] + ".JPG");
            if (fileCheck.exists())
            {
                copyFile(databasepath + identifySystem_String() + "thumbnail" + identifySystem_String() + fileNameList[x] + ".JPG", folderpath);
                systemPrintOut("Copy:" + fileNameList[x], 1, 0);
            }
        }
        systemPrintOut(null, 0, 0);
    }
}
