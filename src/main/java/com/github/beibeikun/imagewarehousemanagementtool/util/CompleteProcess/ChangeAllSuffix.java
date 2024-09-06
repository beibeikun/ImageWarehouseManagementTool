package com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.HiddenFilesChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.ChangeSuffix;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateTemporaryDestinationFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.File;
import java.io.IOException;

/**
 * 批量更改文件后缀的类
 */
public class ChangeAllSuffix
{

    /**
     * 批量更改文件后缀的方法
     *
     * @param filepath 文件夹路径
     */
    public static void changeAllSuffix(String filepath, String targetpath, int mode) throws IOException
    {
        SystemPrintOut.systemPrintOut("Start to change suffix", 3, 0);
        String suffixfolder;
        if (mode == 1)
        {
            suffixfolder = CreateTemporaryDestinationFolder.createTemporaryFolder(filepath, "_suffix");
        }
        else
        {
            suffixfolder = CreateFolder.createFolderWithTime(targetpath);
        }
        FolderCopy.copyFolder(filepath, suffixfolder);
        SystemPrintOut.systemPrintOut(null, 0, 0);
        // 创建文件夹对象
        File imageFolder = new File(suffixfolder);

        // 获取文件夹下的文件列表
        File[] imageList = imageFolder.listFiles();

        // 检查文件夹是否存在且为目录
        if (imageFolder.exists() && imageFolder.isDirectory())
        {
            // 遍历文件列表
            for (File image : imageList)
            {
                // 检查是否为系统或隐藏文件
                if (HiddenFilesChecker.isSystemOrHiddenFile(image))
                {
                    // 跳过系统或隐藏文件
                    continue;
                }
                else
                {
                    // 调用更改后缀的方法
                    ChangeSuffix.changeSuffix(image);
                }
            }
        }
        SystemPrintOut.systemPrintOut(null, 0, 0);
    }
}
