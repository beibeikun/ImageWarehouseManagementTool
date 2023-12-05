package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Module.CheckOperations.FilePathChecker.checkFilePath;
import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件拷贝和删除操作类。
 */
public class FileCopyAndDelete {
    /**
     * 将源文件拷贝到目标文件夹。
     *
     * @param sourceFilePath 源文件路径
     * @param destFolderPath 目标文件夹路径
     */
    public static void copyFile(String sourceFilePath, String destFolderPath) {
        SystemChecker systemIdentifier = new SystemChecker();

        String newFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(systemIdentifier.identifySystem_String()) + 1); // 目标文件名
        String destFilePath = destFolderPath + File.separator + newFileName; // 目标文件路径
        if (!isSystemOrHiddenFile(new File(sourceFilePath)))
        {
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
    }

    /**
     * 批量拷贝文件。
     *
     * @param firstfolderpath 源文件夹路径
     * @param lastfolderpath  目标文件夹路径
     */
    public void copyFiles(String firstfolderpath, String lastfolderpath, int prefixnumbers) throws IOException {
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
                        systemPrintOut("Upload:"+image.getName(), 1, 0);
                        int i = checkFilePath(filepath, false);
                        if (i == 4) {
                            File directory = new File(filepath);
                            boolean created = directory.mkdirs();
                        }
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), filepath);
                    } else {
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), lastfolderpath);
                        systemPrintOut(image.getName(), 1, 0);
                    }
                }
            }
            systemPrintOut(null, 0, 0);
        }
    }

    /**
     * 删除指定文件夹中以指定前缀开头的文件。
     *
     * @param imageFolderPath 图片文件夹路径
     * @param prefix          指定前缀
     */
    public void deleteFiles(String imageFolderPath, String prefix) {
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
