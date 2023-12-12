package Module.DataOperations;

import java.io.File;
import java.util.ArrayList;
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
}
