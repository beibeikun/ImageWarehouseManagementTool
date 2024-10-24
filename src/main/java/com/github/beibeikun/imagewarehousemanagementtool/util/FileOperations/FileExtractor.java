package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件提取工具类，用于从源文件夹中提取特定文件名的文件并复制到目标文件夹中。
 */
public class FileExtractor
{
    /**
     * 提取文件的方法，从源文件夹中提取特定文件名的文件并复制到目标文件夹中。
     *
     * @param sourceFolderPath   源文件夹路径
     * @param targetFolderPath   目标文件夹路径
     * @param fileNamesToExtract 要提取的文件名数组
     * @throws IOException 如果文件操作失败
     */
    public static List<String> extractFiles(String sourceFolderPath, String targetFolderPath, String[] fileNamesToExtract) throws IOException
    {
        SystemChecker system = new SystemChecker();
        List<String> nameList = new ArrayList<>();
        for (String fileName : fileNamesToExtract)
        {
            int position = fileName.indexOf("-");
            // 构建源文件的路径
            Path sourcePath = Paths.get(sourceFolderPath, system.identifySystem_String() + fileName.substring(0, position) + system.identifySystem_String() + fileName + ".zip");
            // 检查文件是否存在
            if (fileExists(sourcePath))
            {
                // 复制文件到目标文件夹
                copyFile(sourcePath.toString(), targetFolderPath);
                systemPrintOut("Get:" + targetFolderPath + system.identifySystem_String() + sourcePath.getFileName().toString(), 1, 0);
                nameList.add(fileName);
            }
        }
        return nameList;
    }
    /**
     * 检查文件是否存在的方法。
     *
     * @param filePath 文件路径
     * @return true 如果文件存在，否则 false
     */
    private static boolean fileExists(Path filePath)
    {
        return Files.exists(filePath);
    }


}
