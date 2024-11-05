package com.github.beibeikun.imagewarehousemanagementtool.util.file;

import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;

import java.io.IOException;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

/**
 * DownloadFromDatabase 类用于从数据库下载文件到指定目录。
 */
public class DownloadFromDatabase
{
    /**
     * 从数据库下载指定的文件到指定的输出路径。
     *
     * @param databasePath 数据库的路径
     * @param fileName 要下载的文件名称
     * @param outPath 文件下载的输出目录
     * @throws IOException 当文件操作出现异常时抛出
     */
    public static void downloadFromDatabase(String databasePath, String fileName, String outPath) throws IOException
    {
        // 获取文件名前缀
        int position = fileName.indexOf("-");
        String fileNamePrefix = fileName.substring(0, position);
        // 建立文件路径
        String filePath = databasePath + identifySystem_String() + fileNamePrefix + identifySystem_String() + fileName + ".zip";

        // 检查目标文件夹是否存在并执行相应操作
        if (checkFolder(filePath,false,"", outPath, false, "", false, ""))
        {
            // 创建包含时间标记的目录
            outPath = CreateFolder.createFolderWithTime(outPath);
            // 复制文件到目标目录
            copyFile(filePath, outPath);
            // 打印操作日志
            systemPrintOut("Download:" + fileName, 1, 0);
        }
    }
}
