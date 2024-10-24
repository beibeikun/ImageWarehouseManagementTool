package com.github.beibeikun.imagewarehousemanagementtool.util.Test;

import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.WriteToProperties.writeToPropertiesSingle;

public class Path
{

    private static String sourcePath = "";
    private static String targetPath = "";

    public static String getSourcePath()
    {
        return sourcePath;
    }

    public static void setSourcePath(String sourcePath)
    {
        writeToPropertiesSingle("settings", "sourceFolderPath", sourcePath);
        Path.sourcePath = sourcePath;
    }
    public static void setSourcePathWithoutWrite(String sourcePath) {
        Path.sourcePath = sourcePath;
    }

    public static String getTargetPath()
    {
        return targetPath;
    }

    public static void setTargetPath(String targetPath)
    {
        writeToPropertiesSingle("settings", "targetFolderPath", targetPath);
        Path.targetPath = targetPath;
    }

    public static void setTargetPathWithoutWrite(String targetPath)
    {
        Path.targetPath = targetPath;
    }
}
