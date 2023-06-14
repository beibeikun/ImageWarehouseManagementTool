package Module.File;

import Module.Others.IdentifySystem;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import static Module.File.DeleteDirectory.deleteDirectory;
import static Module.File.FileOperations.copyFile;
import static Module.File.ImageCompression.imageCompression;

/**
 * 文件压缩功能实现类。
 * 根据指定的源文件夹路径和目标文件夹路径，压缩符合条件的文件。
 * 遍历源文件夹中的文件列表，根据文件名中的前缀将文件分类。
 * 使用 Map 数据结构来跟踪每个前缀与对应文件列表的关系。
 * 压缩每个前缀对应的文件列表，将其保存为一个压缩文件。
 * 返回一个包含所有前缀的字符串数组。
 */
public class FileCompression {

    /**
     * 压缩符合条件的文件。
     *
     * @param sourceFolder 源文件夹路径
     * @param destinationFolder 目标文件夹路径
     * @return 包含所有前缀的字符串数组
     */
    public static String[] compressFilesByPrefix(String sourceFolder, String destinationFolder) throws IOException {
        IdentifySystem system = new IdentifySystem();
        String temporaryDestinationFolder = sourceFolder + "TemporaryCompression";
        File folder = new File(sourceFolder);
        File[] files = folder.listFiles();
        File file1 = new File(temporaryDestinationFolder);
        if (!file1.exists()) {
            File directory = new File(temporaryDestinationFolder);
            boolean created = directory.mkdirs();
        }

        // 使用Map来跟踪前缀和文件列表的关系
        Map<String, List<File>> prefixToFileListMap = new HashMap<>();

        // 遍历文件列表，根据前缀将文件分类
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String prefix = getPrefix(fileName);

                if (prefix != null) {
                    prefixToFileListMap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(file);
                }
            }
        }

        String[] prefixArray = prefixToFileListMap.keySet().toArray(new String[0]);

        // 压缩每个前缀对应的文件列表
        for (Map.Entry<String, List<File>> entry : prefixToFileListMap.entrySet()) {
            String prefix = entry.getKey();
            List<File> fileList = entry.getValue();

            String compressedFile = temporaryDestinationFolder + system.identifySystem_String() + prefix + ".zip";
            System.out.println(11111+":"+compressedFile);
            System.out.println(sourceFolder + system.identifySystem_String() + prefix + ".JPG");
            System.out.println(destinationFolder+system.identifySystem_String()+"thumbnail");
            copyFile(sourceFolder + system.identifySystem_String() + prefix + ".JPG",destinationFolder+system.identifySystem_String()+"thumbnail");
            imageCompression(destinationFolder+system.identifySystem_String()+"thumbnail" + system.identifySystem_String() + prefix + ".JPG",2500);
            compressFiles(fileList, compressedFile);
        }
        FileOperations fileOperations = new FileOperations();
        fileOperations.copyFiles(temporaryDestinationFolder,destinationFolder,6);
        File directory = new File(temporaryDestinationFolder);
        boolean deleted = deleteDirectory(directory); //删除临时文件夹

        return prefixArray;
    }

    /**
     * 提取文件名中的前缀。
     *
     * @param fileName 文件名
     * @return 前缀字符串
     */
    public static String getPrefix(String fileName) {
        int startIndex = fileName.indexOf("(");
        int endIndex = fileName.indexOf(")");

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return fileName.substring(0, startIndex).trim();
        } else {
            int pointIndex = fileName.indexOf(".");
            return fileName.substring(0, pointIndex).trim();
        }
    }

    /**
     * 压缩文件列表到指定的压缩文件。
     *
     * @param files 文件列表
     * @param compressedFile 压缩文件路径
     */
    private static void compressFiles(List<File> files, String compressedFile) {
        try {
            FileOutputStream fos = new FileOutputStream(compressedFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            zipOut.setLevel(Deflater.BEST_COMPRESSION);

            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);

                String entryName = file.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }

                fis.close();
            }

            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试方法，用于演示文件压缩并输出前缀数组。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) throws IOException {
        String sourceFolder = "/Users/bbk/Documents/test1";
        String destinationFolder = "/Users/bbk/Documents/test2";
        String[] prefixes = FileCompression.compressFilesByPrefix(sourceFolder, destinationFolder);

        System.out.println("Prefixes:");
        for (String prefix : prefixes) {
            System.out.println(prefix);
        }
    }
}
