package Module.CheckOperations;

import javax.swing.*;
import java.io.File;

import static Module.FileOperations.DeleteDirectory.deleteDirectory;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 用于检查指定路径的合法性和正确性。
 */
public class FilePathChecker {
    /**
     * 检查指定路径的合法性和正确性。
     *
     * @param checkpath           指定路径
     * @param shouldCheckContents 是否检查文件夹内容是否为空
     * @return 检查结果的整数值
     * 1 - 检查通过
     * 2 - 未选取路径
     * 3 - 路径名存在中文字符
     * 4 - 文件路径不存在
     * 5 - 文件路径内容不为空
     */
    public static int checkFilePath(String checkpath, boolean shouldCheckContents) {
        if (checkpath.isEmpty()) {
            // 未选取路径
            return 2;
        } else {
            ChineseCharactersChecker CNcheck = new ChineseCharactersChecker();
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

    /**
     * 检查路径是否合法，并根据不同情况弹出不同的提示信息
     *
     * @param renamecsvpathcheck CSV 文件路径检查结果（1: 合法, 2: 未选取路径, 3: 路径名存在中文字符, 4: CSV 文件不存在）
     * @param firstpathcheck     源文件夹路径检查结果（1: 合法, 2: 未选取路径, 3: 路径名存在中文字符, 4: 路径不存在）
     * @param lastpathcheck      目标文件夹路径检查结果（1: 合法, 2: 未选取路径, 3: 路径名存在中文字符, 4: 路径不存在, 5: 路径非空）
     * @param lastpath           目标文件夹路径
     * @return 检查通过返回 true，否则返回 false
     */
    public static boolean checkback(int renamecsvpathcheck, int firstpathcheck, int lastpathcheck, String lastpath) {
        if (renamecsvpathcheck == 1 && firstpathcheck == 1 && lastpathcheck == 1) {
            JOptionPane.showMessageDialog(null, "检查通过");
            systemPrintOut("Inspection passed", 1, 1);
            return true;
        } else if (renamecsvpathcheck == 2 || firstpathcheck == 2 || lastpathcheck == 2) {
            JOptionPane.showMessageDialog(null, "未选取路径", "路径错误", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (renamecsvpathcheck == 3 || firstpathcheck == 3 || lastpathcheck == 3) {
            JOptionPane.showMessageDialog(null, "路径名存在中文字符", "路径错误", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (renamecsvpathcheck == 4) {
            JOptionPane.showMessageDialog(null, "CSV文件不存在", "路径错误", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (firstpathcheck == 4) {
            JOptionPane.showMessageDialog(null, "源文件夹路径不存在", "路径错误", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (lastpathcheck == 4) {
            int n = JOptionPane.showConfirmDialog(null, "目标文件夹不存在,是否创建对应文件夹", "标题", JOptionPane.YES_NO_OPTION); //返回值为0或1
            if (n == 0) {
                File directory = new File(lastpath);
                boolean created = directory.mkdirs();
                JOptionPane.showMessageDialog(null, "文件夹已创建，检查通过");
                systemPrintOut("Folder created, inspection passed", 1, 1);
                return true;
            }
        } else if (lastpathcheck == 5) {
            int n = JOptionPane.showConfirmDialog(null, "目标文件夹非空,是否清空对应文件夹", "标题", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                File directory = new File(lastpath);
                boolean deleted = deleteDirectory(directory);
                boolean created = directory.mkdirs();
                JOptionPane.showMessageDialog(null, "文件夹已清空，检查通过");
                systemPrintOut("Folder emptied, inspection passed", 1, 1);
                return true;
            }
        }
        return false;
    }
}
