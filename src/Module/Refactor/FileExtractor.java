package Module.Refactor;

import Module.Others.IdentifySystem;
import java.io.IOException;
import java.nio.file.*;

/**
 * 文件提取工具类，用于从源文件夹中提取特定文件名的文件并复制到目标文件夹中。
 */
public class FileExtractor {
    /**
     * 提取文件的方法，从源文件夹中提取特定文件名的文件并复制到目标文件夹中。
     *
     * @param sourceFolderPath    源文件夹路径
     * @param targetFolderPath    目标文件夹路径
     * @param fileNamesToExtract  要提取的文件名数组
     * @throws IOException        如果文件操作失败
     */
    static void extractFiles(String sourceFolderPath, String targetFolderPath, String[] fileNamesToExtract) throws IOException {
        IdentifySystem system = new IdentifySystem();

        for (String fileName : fileNamesToExtract) {
            // 构建源文件的路径
            Path sourcePath = Paths.get(sourceFolderPath, system.identifySystem_String() + fileName.substring(0, 6) + system.identifySystem_String() + fileName + ".zip");

            // 检查文件是否存在
            if (fileExists(sourcePath)) {
                // 复制文件到目标文件夹
                copyFileToFolder(sourcePath, targetFolderPath);
            }
        }
    }

    /**
     * 复制文件到目标文件夹的方法。
     *
     * @param sourcePath         源文件路径
     * @param targetFolderPath   目标文件夹路径
     * @throws IOException       如果文件复制失败
     */
    private static void copyFileToFolder(Path sourcePath, String targetFolderPath) throws IOException {
        // 构建目标文件的路径
        Path targetPath = Paths.get(targetFolderPath, sourcePath.getFileName().toString());

        // 使用Files.copy方法复制文件
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("文件复制成功：" + targetPath);
    }

    /**
     * 检查文件是否存在的方法。
     *
     * @param filePath  文件路径
     * @return          true 如果文件存在，否则 false
     */
    private static boolean fileExists(Path filePath) {
        return Files.exists(filePath);
    }


}
