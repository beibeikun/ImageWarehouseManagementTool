package Module.Test;

import Module.Others.IdentifySystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static Module.File.FileNameCheck.checkFilePath;
import static Module.Others.SystemPrintOut.systemPrintOut;
import static Module.Refactor.FileChecker.isSystemOrHiddenFile;

/**
 * 文件拷贝和删除操作类。
 */
public class FileOperations {
    /**
     * 批量拷贝文件。
     * 仅拷贝指定前缀[prefix]的文件
     * @param sourceFolderPath  源文件夹路径
     * @param targetFolderPath   目标文件夹路径
     */
    public void copyFiles( String sourceFolderPath, String targetFolderPath, String prefix) throws IOException {
        File sourceFolder = new File(sourceFolderPath);
        File targetFolder = new File(targetFolderPath);

        File[] files = sourceFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith(prefix)) {
                    File targetFile = new File(targetFolder, file.getName());
                    Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * 将源文件拷贝到目标文件夹。
     *
     * @param sourceFilePath 源文件路径
     * @param destFolderPath 目标文件夹路径
     */
    static void copyFile(String sourceFilePath, String destFolderPath) {
        IdentifySystem systemIdentifier = new IdentifySystem();

        String newFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(systemIdentifier.identifySystem_String()) + 1); // 目标文件名
        String destFilePath = destFolderPath + File.separator + newFileName; // 目标文件路径

        try (InputStream inputStream = new FileInputStream(sourceFilePath); // 创建输入流对象
             OutputStream outputStream = new FileOutputStream(destFilePath)) { // 创建输出流对象
            byte[] buffer = new byte[1024 * 8]; // 创建缓冲区

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) { // 循环读取数据
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹中以指定前缀开头的文件。
     *
     * @param imageFolderPath 图片文件夹路径
     * @param prefix 指定前缀
     */
    public void deleteFiles(String imageFolderPath,String prefix) {
        File imageFolder = new File(imageFolderPath);
        File[] imageList = imageFolder.listFiles();

        System.out.println("\n\n" + "-------------------------Start to delete failed files-------------------------");

        if (imageFolder.exists() && imageFolder.isDirectory()) {
            for (File image : imageList) {
                if (image.getName().startsWith(prefix)) {
                    String name = image.getName();
                    image.delete();
                    System.out.println("succeeded: " + name);
                }
            }
        }

        System.out.println("-------------------------Delete failed files succeeded-------------------------");
    }
}
