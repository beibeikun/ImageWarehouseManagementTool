package Module.DataOperations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;

public class FileListWithoutExtension {

    /**
     * 获取文件夹内所有文件的名称（去除后缀）
     *
     * @param folderPath 文件夹路径
     * @return 包含文件名（去除后缀）的字符串数组
     */
    public static String[] getFileNamesWithoutExtension(String folderPath) {
        // 创建File对象表示文件夹
        File folder = new File(folderPath);

        // 检查文件夹是否存在且为文件夹
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("无效的文件夹路径。");
            return new String[0];
        }

        // 获取文件夹下的所有文件
        File[] files = folder.listFiles();
        List<String> fileNamesWithoutExtension = new ArrayList<>();

        // 遍历文件列表
        if (files != null) {
            for (File file : files) {
                // 判断是否为文件
                if (file.isFile()) {
                    // 判断是否为隐藏文件
                    if (!isSystemOrHiddenFile(file)) {
                        // 获取文件名
                        String fileName = file.getName();
                        // 去除文件名的后缀
                        String fileNameWithoutExtension = removeFileExtension(fileName);
                        // 将处理后的文件名加入列表
                        fileNamesWithoutExtension.add(fileNameWithoutExtension);
                    }
                }
            }
        }

        // 将列表转换为字符串数组并返回
        return fileNamesWithoutExtension.toArray(new String[0]);
    }

    /**
     * 去除文件名的后缀
     *
     * @param fileName 原始文件名
     * @return 去除后缀的文件名
     */
    private static String removeFileExtension(String fileName) {
        // 查找最后一个点的位置
        int lastDotIndex = fileName.lastIndexOf('.');
        // 如果存在点并且不在开头，则截取文件名
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        } else {
            // 否则，返回原始文件名
            return fileName;
        }
    }
}