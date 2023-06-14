package Module.Refactor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileChecker {
    /**
     * 检查给定文件是否是系统文件或隐藏文件。
     *
     * @param file 要检查的文件
     * @return 如果文件是系统文件或隐藏文件，则返回 true；否则返回 false
     */
    public static boolean isSystemOrHiddenFile(File file) {
        if (file.isHidden()) {
            return true; // 是隐藏文件
        }

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            // Windows 系统文件检查
            String fileName = file.getName().toLowerCase();
            return fileName.equals("boot.ini") ||
                    fileName.equals("ntldr") ||
                    fileName.equals("ntdetect.com") ||
                    fileName.equals("thumbs.db");
        }
        else if (osName.contains("mac")) {
            // Mac 系统文件检查
            String fileName = file.getName().toLowerCase();
            return fileName.equals(".ds_store") ||
                    fileName.endsWith(".localized");
        }

        return false; // 非系统文件和隐藏文件
    }

    /**
     * 在指定路径的文件夹中查找文件名包含关键词的文件，并将它们的文件名整合成一个字符串数组返回。
     * 如果路径为文件夹，则递归查找所有子文件夹。
     *
     * @param folderPath 文件夹路径
     * @param keyword    关键词
     * @return 符合条件的文件的文件名数组
     */
    public static String[] findFileNamesByKeyword(String folderPath, String keyword) {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return new String[0];  // 如果路径不是文件夹，则返回空数组
        }

        File[] files = folder.listFiles();
        List<String> matchingFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (!isSystemOrHiddenFile(file) && (keyword == null || file.getName().contains(keyword))) {
                    matchingFiles.add(file.getName());
                }
            }
        }

        return matchingFiles.toArray(new String[0]);
    }

    /**
     * 统计文件夹及其子文件夹中的文件数量。
     *
     * @param folderPath 文件夹路径
     * @return 文件数量
     */
    public static int countFiles(String folderPath) {
        File folder = new File(folderPath);
        return countFilesRecursive(folder);
    }

    private static int countFilesRecursive(File folder) {
        int count = 0;
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!isSystemOrHiddenFile(file)) {
                        count++;
                    }
                } else if (file.isDirectory()) {
                    count += countFilesRecursive(file); // 递归调用统计子文件夹中的文件数量
                }
            }
        }

        return count;
    }
}
