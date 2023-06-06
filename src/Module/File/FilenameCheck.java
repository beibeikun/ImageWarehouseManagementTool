package Module.File;

import java.io.*;

/**
 * 用于检查源文件夹、目标文件夹和CSV文件夹路径的合法性和正确性。
 * 返回值说明：
 * 1  - 检查通过
 * 2  - 未选取路径
 * 3  - 路径名存在中文字符
 * 41 - CSV文件不存在
 * 42 - 源文件路径不存在
 * 43 - 目标文件路径不存在
 * 5  - 目标文件夹不为空
 */
public class FilenameCheck {
    /**
     * 检查源文件夹、图片文件夹和拷贝文件夹路径的合法性和正确性。
     *
     * @param filepath  源文件夹路径
     * @param imgpath   图片文件夹路径
     * @param copypath  拷贝文件夹路径
     * @return 检查结果的整数值
     */
    public int namecheck(String filepath, String imgpath, String copypath) {
        if (filepath.equals("") || imgpath.equals("") || copypath.equals("")) {
            // 没选路径
            return 2;
        } else {
            IsContainChinese CNcheck = new IsContainChinese();
            // 路径里有中文
            if (CNcheck.isContainChinese(filepath) || CNcheck.isContainChinese(imgpath) || CNcheck.isContainChinese(copypath)) {
                return 3;
            } else {
                File file1 = new File(filepath);
                File file2 = new File(imgpath);
                File file3 = new File(copypath);
                if (!file1.exists()) {
                    return 41;
                } else if (!file2.exists()) {
                    return 42;
                } else if (!file3.exists()) {
                    return 43;
                }
                File file = new File(copypath); // 图片目录
                File[] copypathlist = file.listFiles();
                if (copypathlist.length != 0) {
                    return 5;
                }

                WriteToProperties writeToProperties = new WriteToProperties();
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0] = "firstpath";
                filenamelist[1][0] = imgpath;
                filenamelist[0][1] = "renamecsvpath";
                filenamelist[1][1] = filepath;
                filenamelist[0][2] = "lastpath";
                filenamelist[1][2] = copypath;
                writeToProperties.writeToProperties("settings", filenamelist);

                return 1;
            }
        }
    }
}
