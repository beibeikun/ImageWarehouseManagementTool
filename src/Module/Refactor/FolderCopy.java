package Module.Refactor;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static Module.Refactor.FileChecker.isSystemOrHiddenFile;

public class FolderCopy {
    /**
     * 复制文件夹及其内容到目标文件夹。
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 复制操作失败时抛出异常
     */
    static void copyFolder(String sourceFolderPath, String targetFolderPath) throws IOException {
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
}
