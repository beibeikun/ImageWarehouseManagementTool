package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.GetCorrectTime.getCorrectTimeToFolderName;

public class CreateFolder
{
    /**
     * 使用时间和四位随机数为名字生成一个文件夹
     * 格式：yyMMdd_HHmmss_****
     * @param folderPath 生成路径
     * @return 生成的文件夹路径
     */
    public static String createFolderWithTime(String folderPath)
    {
        int randomIntInRange = ThreadLocalRandom.current().nextInt(1000, 9999);  // [50, 100]
        folderPath = folderPath + identifySystem_String() + getCorrectTimeToFolderName() + "_" + randomIntInRange;
        File file = new File(folderPath);
        file.mkdir();
        return folderPath;
    }

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
