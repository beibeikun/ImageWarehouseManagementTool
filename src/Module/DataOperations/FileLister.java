package Module.DataOperations;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;

public class FileLister {
    /**
     * 获取文件夹内所有文件名的数组，排除了 macOS 系统的 .DS_Store 文件和 Windows 系统的隐藏文件和缩略图文件
     *
     * @param folderPath 文件夹路径
     * @return 包含文件夹内所有文件名的数组
     */
    public static String[] getFileNames(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            return new String[0]; // 如果文件夹不存在或不是文件夹，则返回空数组
        }

        List<String> fileNameList = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!isSystemOrHiddenFile(file)) {
                    fileNameList.add(file.getName());
                }
            }
        }

        return fileNameList.toArray(new String[0]);
    }

    /**
     * 获取指定文件夹下所有文件的文件名
     *
     * @param folderPath 文件夹路径
     * @return 文件名列表
     */
    public static List<File> getFileNamesInList(String folderPath) {
        // 创建 File 对象
        File file1 = new File(folderPath);

        // 检查文件夹是否存在
        if (!file1.exists() || !file1.isDirectory()) {
            return Collections.emptyList();
        }

        // 获取文件夹内所有文件
        File[] filesArray = file1.listFiles();

        // 将 File 数组添加到 List 中
        List<File> files = new ArrayList<>();
        if (filesArray != null) {
            Collections.addAll(files, filesArray);
        }

        return files;
    }
}
