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
    private static String renameCsvPath = printOutMessage.NULL;
    private static String checkCsvPath= printOutMessage.NULL;
    private static String pdfCsvPath= printOutMessage.NULL;

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

    /**
     * 获取重命名用csv文件路径。
     * @return 重命名用csv文件路径
     */
    public static String getRenameCsvPath()
    {
        return renameCsvPath;
    }

    /**
     * 设置重命名用csv文件路径并将其写入到配置文件中。
     * @param renameCsvPath 新的重命名用csv文件路径
     */
    public static void setRenameCsvPath(String renameCsvPath)
    {
        writeToPropertiesSingle(files.SETTINGS, files.RENAME_CSV_PATH, renameCsvPath);
        DirectoryPathManager.renameCsvPath = renameCsvPath;
    }

    /**
     * 不写入配置文件的情况下设置重命名用csv文件路径。
     * @param renameCsvPath 新的重命名用csv文件路径
     */
    public static void setRenameCsvPathWithoutWrite(String renameCsvPath)
    {
        DirectoryPathManager.renameCsvPath = renameCsvPath;
    }

    /**
     * 获取检查用csv文件路径。
     * @return 检查用csv文件路径
     */
    public static String getCheckCsvPath()
    {
        return checkCsvPath;
    }

    /**
     * 设置检查用csv文件路径并将其写入到配置文件中。
     * @param checkCsvPath 新的检查用csv文件路径
     */
    public static void setCheckCsvPath(String checkCsvPath)
    {
        writeToPropertiesSingle(files.SETTINGS, files.CHECK_CSV_PATH, checkCsvPath);
        DirectoryPathManager.checkCsvPath = checkCsvPath;
    }

    /**
     * 不写入配置文件的情况下设置重检查csv文件路径。
     * @param checkCsvPath 新的检查用csv文件路径
     */
    public static void setCheckCsvPathWithoutWrite(String checkCsvPath)
    {
        DirectoryPathManager.checkCsvPath = checkCsvPath;
    }

    /**
     * 获取PDF用csv文件路径。
     * @return PDF用csv文件路径
     */
    public static String getPdfCsvPath()
    {
        return pdfCsvPath;
    }

    /**
     * 设置PDF用csv文件路径并将其写入到配置文件中。
     * @param pdfCsvPath 新的PDF用csv文件路径
     */
    public static void setPdfCsvPath(String pdfCsvPath)
    {
        writeToPropertiesSingle(files.SETTINGS, files.PDF_CSV_PATH, pdfCsvPath);
        DirectoryPathManager.pdfCsvPath = pdfCsvPath;
    }

    /**
     * 不写入配置文件的情况下设置PDF用csv文件路径。
     * @param pdfCsvPath 新的PDF用csv文件路径
     */
    public static void setPdfCsvPathWithoutWrite(String pdfCsvPath)
    {
        DirectoryPathManager.pdfCsvPath = pdfCsvPath;
    }
}