package Module.File;

import Module.Others.IdentifySystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件拷贝和删除操作类。
 */
public class FileOperations {
    /**
     * 批量拷贝文件。
     *
     * @param sourceFolderPath 源文件夹路径
     * @param imageFolderPath  图片文件夹路径
     * @param copyFolderPath   拷贝文件夹路径
     */
    public void copyFiles(String sourceFolderPath, String imageFolderPath, String copyFolderPath) throws IOException {
        IdentifySystem systemIdentifier = new IdentifySystem();
        File imageFolder = new File(imageFolderPath);
        File[] imageList = imageFolder.listFiles();

        if (imageFolder.exists() && imageFolder.isDirectory()) {
            System.out.println("\n\n" + "-------------------------Start to copy-------------------------");

            for (File image : imageList) {
                if (!Files.isHidden(Paths.get(imageFolderPath + systemIdentifier.identifySystem_String() + image.getName()))) {
                    // 在这里执行对非隐藏文件的操作
                    copyFile(imageFolderPath + systemIdentifier.identifySystem_String() + image.getName(), copyFolderPath);
                    System.out.println("succeeded: " + image.getName());
                }
            }

            System.out.println("-------------------------Copy succeeded-------------------------");
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
