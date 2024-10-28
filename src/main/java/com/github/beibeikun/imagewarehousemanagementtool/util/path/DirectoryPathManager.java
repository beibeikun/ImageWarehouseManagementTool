package com.github.beibeikun.imagewarehousemanagementtool.util.path;

import com.github.beibeikun.imagewarehousemanagementtool.constant.files;
import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;

import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.WriteToProperties.writeToPropertiesSingle;

/**
 * Path 类用于管理文件仓库的源目录和目标目录路径。
 */
public class DirectoryPathManager
{
    // 定义源目录路径。
    private static String sourcePath = printOutMessage.NULL;
    // 定义目标目录路径。
    private static String targetPath = printOutMessage.NULL;

    /**
     * 获取源目录路径。
     * @return 源目录路径
     */
    public static String getSourcePath()
    {
        return sourcePath;
    }

    /**
     * 设置源目录路径并将其写入到配置文件中。
     * @param sourcePath 新的源目录路径
     */
    public static void setSourcePath(String sourcePath)
    {
        writeToPropertiesSingle(files.SETTINGS, files.SOURCE_FOLDER_PATH, sourcePath);
        DirectoryPathManager.sourcePath = sourcePath;
    }

    /**
     * 不写入配置文件的情况下设置源目录路径。
     * @param sourcePath 新的源目录路径
     */
    public static void setSourcePathWithoutWrite(String sourcePath) {
        DirectoryPathManager.sourcePath = sourcePath;
    }

    /**
     * 获取目标目录路径。
     * @return 目标目录路径
     */
    public static String getTargetPath()
    {
        return targetPath;
    }

    /**
     * 设置目标目录路径并将其写入到配置文件中。
     * @param targetPath 新的目标目录路径
     */
    public static void setTargetPath(String targetPath)
    {
        writeToPropertiesSingle(files.SETTINGS, files.TARGET_FOLDER_PATH, targetPath);
        DirectoryPathManager.targetPath = targetPath;
    }

    /**
     * 不写入配置文件的情况下设置目标目录路径。
     * @param targetPath 新的目标目录路径
     */
    public static void setTargetPathWithoutWrite(String targetPath)
    {
        DirectoryPathManager.targetPath = targetPath;
    }
}