package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static Module.CheckOperations.FilePathChecker.checkFilePath;
import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.DataOperations.FileLister.getFileNamesInList;
import static Module.FileOperations.DeleteDirectory.deleteDirectory;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件拷贝和删除操作类。
 */
public class FileCopyAndDelete {
    /**
     * 拷贝单个文件到目标文件夹。
     *
     * @param sourceFilePath 文件路径
     * @param destFolderPath 目标文件夹路径
     */
    public static void copyFile(String sourceFilePath, String destFolderPath) throws IOException {
        if (!isSystemOrHiddenFile(new File(sourceFilePath))) {
            Path targetPath = Paths.get(destFolderPath, Paths.get(sourceFilePath).getFileName().toString());
            Files.copy(Paths.get(sourceFilePath), targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
/*
        SystemChecker systemIdentifier = new SystemChecker();

        String newFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(systemIdentifier.identifySystem_String()) + 1); // 目标文件名
        String destFilePath = destFolderPath + File.separator + newFileName; // 目标文件路径
        if (!isSystemOrHiddenFile(new File(sourceFilePath))) {
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

 */
    }

    /**
     * 根据文件前缀批量拷贝整理文件。
     *
     * @param firstfolderpath 源文件夹路径
     * @param lastfolderpath  目标文件夹路径
     * @param prefixnumbers 前缀数
     * @throws IOException 复制文件时可能发生的IO异常
     */
    public static void copyFiles(String firstfolderpath, String lastfolderpath, int prefixnumbers) throws IOException {
        SystemChecker systemIdentifier = new SystemChecker();
        File imageFolder = new File(firstfolderpath);
        File[] imageList = imageFolder.listFiles();

        if (imageFolder.exists() && imageFolder.isDirectory()) {
            systemPrintOut("Start to copy", 3, 0);

            for (File image : imageList) {
                Path path = Paths.get(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName());
                // 将 Path 对象转换为 File 对象
                File file = path.toFile();
                if (!isSystemOrHiddenFile(file)) {
                    // 在这里执行对非隐藏文件的操作
                    if (prefixnumbers != 0) {
                        String filepath = lastfolderpath + systemIdentifier.identifySystem_String() + image.getName().substring(0, prefixnumbers);
                        systemPrintOut("Upload:" + image.getName(), 1, 0);
                        int i = checkFilePath(filepath, false);
                        if (i == 4) {
                            File directory = new File(filepath);
                            boolean created = directory.mkdirs();
                        }
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), filepath);
                    } else {
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
     * @param filelist 源文件列表
     * @param targetDirPath  目标文件夹路径
     * @throws IOException 复制文件时可能发生的IO异常
     */
    public static List<File> moveFilesWithList(List<File> filelist,String targetDirPath) throws IOException {
        File directory = new File(targetDirPath);
        boolean deleted = deleteDirectory(directory);
        boolean created = directory.mkdirs();
        // 复制文件到目标文件夹
        for (File file : filelist) {
            // 创建目标文件
            File targetFile = new File(targetDirPath, file.getName());

            // 复制文件
            org.apache.commons.io.FileUtils.copyFile(file, targetFile);
        }
        return getFileNamesInList(targetDirPath);
    }
}
