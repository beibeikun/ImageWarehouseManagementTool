package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.io.IOException;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch.isFileExists;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.GetFileCreationTime.getFileCreationTime;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;
import static com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister.getFileNamesInString;

public class SearchInfoFromDatabase {
    public static void searchInfoFromDatabase(String warehouseAddressText, String fileName) throws IOException {
        // 获取文件名前缀
        int position = fileName.indexOf("-");
        systemPrintOut("开始查询信息",3,0);
        //查询单个文件
        if (position != -1)
        {
            String fileNamePrefix = fileName.substring(0, position);
            // 建立文件路径
            String filePath = warehouseAddressText + identifySystem_String() + fileNamePrefix + identifySystem_String() + fileName + ".zip";
            if (!isFileExists(filePath))
            {
                systemPrintOut("文件不存在", 2, 0);
                systemPrintOut(null, 0, 0);
                return;
            }
            systemPrintOut("文件名: " + fileName + " 创建时间: " + getFileCreationTime(filePath),1,0);

        }
        //查询完整文件
        else
        {
            // 建立文件路径
            String filePath = warehouseAddressText + identifySystem_String() + fileName;
            if (!isFileExists(filePath))
            {
                systemPrintOut("文件不存在", 2, 0);
                systemPrintOut(null, 0, 0);
                return;
            }
            String[] fileNames = getFileNamesInString(filePath);
            systemPrintOut("文件夹: " + fileName + " 创建时间: " + getFileCreationTime(filePath) + " 共计: " + fileNames.length + "件",1,0);
            systemPrintOut(null, 0, 0);
            for (String filename : fileNames)
            {
                String fileNamePrefix = filename.substring(0, filename.indexOf("."));
                filePath = warehouseAddressText + identifySystem_String() + fileName + identifySystem_String() + filename;
                systemPrintOut("文件名: " + fileNamePrefix + " 创建时间: " + getFileCreationTime(filePath),1,0);
            }
        }
        systemPrintOut(null, 0, 0);
    }
}
