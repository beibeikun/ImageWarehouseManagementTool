package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;

/**
 * 用于创建临时目标文件夹的类。
 */
public class CreateTemporaryDestinationFolder
{

    /**
     * 创建临时目标文件夹。
     *
     * @param sourceFolder 源文件夹路径
     * @return 临时目标文件夹路径
     */
    public static String createTemporaryFolder(String sourceFolder, String suffix)
    {
        // 构建临时目标文件夹路径
        String temporaryFolder = sourceFolder + suffix;

        // 创建临时目标文件夹对象
        File tempFolder = new File(temporaryFolder);

        // 如果临时目标文件夹不存在，则创建
        if (! tempFolder.exists())
        {
            File directory = new File(temporaryFolder);
            boolean created = directory.mkdirs();
        }

        // 返回临时目标文件夹路径
        return temporaryFolder;
    }
}
