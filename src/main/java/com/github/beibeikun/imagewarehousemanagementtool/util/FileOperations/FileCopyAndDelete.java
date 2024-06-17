package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.FilePathChecker.checkFilePath;
import static com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister.getFileNamesInList;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件拷贝和删除操作类。
 */
public class FileCopyAndDelete
{
    /**
     * 拷贝单个文件到目标文件夹。
     *
     * @param sourceFilePath 文件路径
     * @param destFolderPath 目标文件夹路径
     */
    public static void copyFile(String sourceFilePath, String destFolderPath) throws IOException
    {
        if (! isSystemOrHiddenFile(new File(sourceFilePath)))
        {
            Path targetPath = Paths.get(destFolderPath, Paths.get(sourceFilePath).getFileName().toString());
            Files.copy(Paths.get(sourceFilePath), targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * 根据文件前缀批量拷贝整理文件。
     *
     * @param firstfolderpath 源文件夹路径
     * @param lastfolderpath  目标文件夹路径
     * @param prefixnumbers   前缀数
     * @throws IOException 复制文件时可能发生的IO异常
     */
    public static void copyFilesAndOrganize(String firstfolderpath, String lastfolderpath, int prefixnumbers) throws IOException
    {
        SystemChecker systemIdentifier = new SystemChecker();
        File filesFolder = new File(firstfolderpath);
        File[] filesList = filesFolder.listFiles();

        if (filesFolder.exists() && filesFolder.isDirectory())
        {
            systemPrintOut("Start to copy", 3, 0);

            for (File image : filesList)
            {
                Path path = Paths.get(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName());
                // 将 Path 对象转换为 File 对象
                File file = path.toFile();
                if (! isSystemOrHiddenFile(file))
                {
                    int prefixNumbersTest = image.getName().indexOf("-");
                    // 在这里执行对非隐藏文件的操作
                    if (prefixNumbersTest != 0)
                    {
                        String filepath = lastfolderpath + systemIdentifier.identifySystem_String() + image.getName().substring(0, prefixNumbersTest);
                        systemPrintOut("Upload:" + image.getName(), 1, 0);
                        int i = checkFilePath(filepath, false);
                        if (i == 4)
                        {
                            File directory = new File(filepath);
                            boolean created = directory.mkdirs();
                        }
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), filepath);
                    }
                    else
                    {
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), lastfolderpath);
                        systemPrintOut("Upload:" + image.getName(), 1, 0);
                    }
                }
            }
            systemPrintOut(null, 0, 0);
        }
    }

    /**
     * 根据文件列表批量拷贝文件。
     *
     * @param filelist      源文件列表
     * @param targetDirPath 目标文件夹路径
     * @throws IOException 复制文件时可能发生的IO异常
     */
    public static List<File> moveFilesWithList(List<File> filelist, String targetDirPath) throws IOException
    {
        File directory = new File(targetDirPath);
        boolean deleted = DeleteDirectory.deleteDirectory(directory);
        boolean created = directory.mkdirs();
        // 复制文件到目标文件夹
        for (File file : filelist)
        {
            // 创建目标文件
            File targetFile = new File(targetDirPath, file.getName());

            // 复制文件
            org.apache.commons.io.FileUtils.copyFile(file, targetFile);
        }
        return getFileNamesInList(targetDirPath);
    }
}
