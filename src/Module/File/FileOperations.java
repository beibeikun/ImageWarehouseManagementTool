package Module.File;

import Module.Others.IdentifySystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Module.File.FileNameCheck.checkFilePath;
import static Module.Others.SystemPrintOut.systemPrintOut;
import static Module.Refactor.FileChecker.isSystemOrHiddenFile;

/**
 * 文件拷贝和删除操作类。
 */
public class FileOperations {
    /**
     * 批量拷贝文件。
     * @param firstfolderpath  源文件夹路径
     * @param lastfolderpath   目标文件夹路径
     */
    public void copyFiles( String firstfolderpath, String lastfolderpath, int prefixnumbers) throws IOException {
        IdentifySystem systemIdentifier = new IdentifySystem();
        File imageFolder = new File(firstfolderpath);
        File[] imageList = imageFolder.listFiles();

        if (imageFolder.exists() && imageFolder.isDirectory()) {
            System.out.println("\nSTART TO COPY");
            systemPrintOut(null,0,0);

            for (File image : imageList) {
                Path path = Paths.get(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName());
                // 将 Path 对象转换为 File 对象
                File file = path.toFile();
                if (!isSystemOrHiddenFile(file)) {
                    // 在这里执行对非隐藏文件的操作
                    if (prefixnumbers!=0)
                    {
                        String filepath = lastfolderpath + systemIdentifier.identifySystem_String() + image.getName().substring(0,prefixnumbers);
                        System.out.println(filepath);
                        int i =checkFilePath(filepath,false);
                        if (i==4)
                        {
                            File directory = new File(filepath);
                            boolean created = directory.mkdirs();
                        }
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), filepath);
                    }
                    else
                    {
                        copyFile(firstfolderpath + systemIdentifier.identifySystem_String() + image.getName(), lastfolderpath);
                        systemPrintOut(image.getName(),1,1);
                    }
                }
            }
            System.out.println("\nCOPY SUCCESSED");
            systemPrintOut(null,0,0);
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
