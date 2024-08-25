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
    public static void copyFilesAndOrganize(String firstfolderpath, String lastfolderpath) throws IOException
    {
        SystemChecker systemIdentifier = new SystemChecker();
        File filesFolder = new File(firstfolderpath);
        File[] filesList = filesFolder.listFiles();

        if (filesFolder.exists() && filesFolder.isDirectory())
        {
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
        }
    }

    /**
     * 根据文件列表批量拷贝文件。
     *
     * @param filelist      源文件列表
     * @param targetDirPath 目标文件夹路径
     * @param resetDeterminer 清空文件夹判定
     * @throws IOException 复制文件时可能发生的IO异常
     */
    public static List<File> copyFilesWithList(List<File> filelist, String targetDirPath, boolean resetDeterminer) throws IOException
    {
        File directory = new File(targetDirPath);
        if (resetDeterminer)
        {
            DeleteDirectory.deleteDirectory(directory);
            directory.mkdirs();
        }
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
    public static void moveFilesWithList(List<File> fileList, String targetDirectoryPath) {
        Path targetDirectory = Paths.get(targetDirectoryPath);
        try {
            // 确保目标目录存在
            Files.createDirectories(targetDirectory);
        } catch (IOException e) {
            return;
        }

        for (File file : fileList) {
            Path sourcePath = file.toPath();
            Path targetPath = targetDirectory.resolve(file.getName());
            try {
                // 移动文件到目标目录，如果目标文件存在则替换它
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ignored) {
            }
        }
    }
}
