package Module.File;

import java.io.*;

/**
 * 用于检查指定路径的合法性和正确性。
 */
public class FileNameCheck {
    /**
     * 检查指定路径的合法性和正确性。
     *
     * @param checkpath           指定路径
     * @param shouldCheckContents 是否检查文件夹内容是否为空
     * @return 检查结果的整数值
     *         1 - 检查通过
     *         2 - 未选取路径
     *         3 - 路径名存在中文字符
     *         4 - 文件路径不存在
     *         5 - 文件路径内容不为空
     */
    public static int checkFilePath(String checkpath, boolean shouldCheckContents) {
        if (checkpath.isEmpty()) {
            // 未选取路径
            return 2;
        } else {
            IsContainChinese CNcheck = new IsContainChinese();
            // 路径中包含中文字符
            if (CNcheck.isContainChinese(checkpath)) {
                return 3;
            } else {
                File fileCheck = new File(checkpath);
                if (!fileCheck.exists()) {
                    // 文件路径不存在
                    return 4;
                } else {
                    if (shouldCheckContents) {
                        File[] files = fileCheck.listFiles();
                        if (files != null && files.length > 0) {
                            // 目标文件夹不为空
                            return 5;
                        }
                    }
                    return 1;
                }
            }
        }
    }
}
