package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.HiddenFilesChecker.isSystemOrHiddenFile;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister.getFileNamesInString;
import static com.github.beibeikun.imagewarehousemanagementtool.util.data.GetPrefix.getPrefix;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

public class FolderCopy
{
    /**
     * 复制文件夹及其内容到目标文件夹。
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 复制操作失败时抛出异常
     */
    public static void copyFolder(String sourceFolderPath, String targetFolderPath) throws IOException
    {
        Path sourcePath = Paths.get(sourceFolderPath);
        Path targetPath = Paths.get(targetFolderPath);

        // 使用 Files.walkFileTree 遍历源文件夹中的所有文件和子文件夹
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                Path relativePath = sourcePath.relativize(file);
                Path targetFile = targetPath.resolve(relativePath);

                // 确保目标文件夹存在
                Files.createDirectories(targetFile.getParent());

                if (! isSystemOrHiddenFile(file.toFile()))
                {
                    systemPrintOut("Copy:" + file + "-->" + targetFile, 1, 0);
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                }
                // 复制文件


                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
            {
                System.err.println("无法访问文件: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
            {
                Path relativePath = sourcePath.relativize(dir);
                Path targetDir = targetPath.resolve(relativePath);

                // 确保目标文件夹存在
                Files.createDirectories(targetDir);

                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 按列表复制指定文件到目标文件夹。
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @param nameList         文件名列表
     */
    public static void copyFolderWithList(String sourceFolderPath, String targetFolderPath, List<String> nameList) throws IOException
    {
        String[] sourceFiles = getFileNamesInString(sourceFolderPath);
        Arrays.sort(sourceFiles);

        for (String sourceFile : sourceFiles)
        {
            String filecheck = getPrefix(sourceFile);
            if (nameList.contains(filecheck))
            {
                copyFile(sourceFolderPath + identifySystem_String() + sourceFile, targetFolderPath);
                systemPrintOut("Copy:" + sourceFile + "-->" + targetFolderPath, 1, 0);
            }
        }
    }
}
