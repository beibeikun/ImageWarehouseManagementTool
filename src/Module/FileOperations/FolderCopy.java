package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.DataOperations.FileLister.getFileNames;
import static Module.DataOperations.GetPrefix.getPrefix;
import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class FolderCopy {
    /**
     * 复制文件夹及其内容到目标文件夹。
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 复制操作失败时抛出异常
     */
    public static void copyFolder(String sourceFolderPath, String targetFolderPath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        Path targetPath = Paths.get(targetFolderPath);

        // 使用 Files.walkFileTree 遍历源文件夹中的所有文件和子文件夹
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = sourcePath.relativize(file);
                Path targetFile = targetPath.resolve(relativePath);

                // 确保目标文件夹存在
                Files.createDirectories(targetFile.getParent());

                if (!isSystemOrHiddenFile(file.toFile())) {
                    systemPrintOut("Copy:" + file + "-->" + targetFile, 1, 0);
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                }
                // 复制文件


                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.err.println("无法访问文件: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
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
     * @param nameList 文件名列表
     */
    public static void copyFolderWithList(String sourceFolderPath, String targetFolderPath, List<String> nameList) throws IOException {
        String[] sourceFiles = getFileNames(sourceFolderPath);
        SystemChecker system = new SystemChecker();

        for (String sourceFile : sourceFiles) {
            String filecheck = getPrefix(sourceFile);
            if (nameList.contains(filecheck))
            {
                copyFile(sourceFolderPath+system.identifySystem_String()+sourceFile,targetFolderPath);
                systemPrintOut("Copy:" + sourceFile + "-->" + targetFolderPath, 1, 0);
            }
        }
    }
}
