package Module.CheckOperations;

import java.io.File;

public class HiddenFilesChecker {
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

        String fileName = file.getName().toLowerCase();
        return fileName.equals("boot.ini") ||
                fileName.equals("ntldr") ||
                fileName.equals("ntdetect.com") ||
                fileName.equals("thumbs.db")||
                fileName.equals(".ds_store") ||
                fileName.endsWith(".localized");
        // 系统文件
    }
}
