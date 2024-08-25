package com.github.beibeikun.imagewarehousemanagementtool.util.Test;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.RenameFiles;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.IOException;
import java.util.Arrays;

public class MoveNumberForward
{
    /**
     * 将指定文件夹中的文件编号前移
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void moveNumberForwardt(String sourceFolderPath, String targetFolderPath) throws IOException
    {
        // 创建系统检查器
        SystemChecker system = new SystemChecker();

        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        SystemPrintOut.systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = FileNameProcessor.processFileNames(FileLister.getFileNames(targetFolderPath));

        // 对文件名列表进行排序
        Arrays.sort(fileNameList);

        // 遍历每个文件名
        for (String s : fileNameList)
        {
            int number = 1;

            // 循环查找同名文件
            while (true)
            {
                // 构造文件路径
                String filePath = targetFolderPath + system.identifySystem_String() + s + " (" + number + ").jpg";

                // 判断文件是否存在
                boolean fileExists = FileSearch.isFileExists(filePath);

                // 文件存在
                if (fileExists)
                {
                    // 如果是第一个文件，则重命名为原文件名
                    if (number == 1)
                    {
                        RenameFiles.renameFileWithName(filePath, s);
                    }
                    else
                    {
                        // 否则将文件重命名为原文件名 + (number - 1)
                        int newNum = number - 1;
                        RenameFiles.renameFileWithName(filePath, s + " (" + newNum + ")");
                    }
                }
                else
                {
                    // 文件不存在，跳出循环
                    break;
                }

                // 递增编号
                number++;
            }
        }
    }
}
