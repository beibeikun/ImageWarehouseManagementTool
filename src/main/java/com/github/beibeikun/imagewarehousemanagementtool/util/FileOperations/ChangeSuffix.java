package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;

import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

/**
 * 更改文件后缀的类
 */
public class ChangeSuffix
{

    /**
     * 更改文件后缀的方法
     *
     * @param file 要更改后缀的文件
     */
    public static void changeSuffix(File file)
    {
        // 获取文件路径的字符串表示
        String filepath = String.valueOf(file);
        if (! extractStringFromFileName(filepath, "(", ")").isEmpty())
        {
            // 从文件名中提取序列号，这里假设序列号在文件名中用括号括起来
            int serialnumber = Integer.parseInt(extractStringFromFileName(filepath, "(", ")"));

            // 如果序列号小于10，将括号后的"0"插入文件名中
            if (serialnumber < 10)
            {
                filepath = FileRenameWithKeyword.renameFileWithKeyword(filepath, "(", "(0");
            }

            // 将文件名中的空格和括号替换为下划线
            filepath = FileRenameWithKeyword.renameFileWithKeyword(filepath, " (", "_");

            // 移除文件名中的右括号
            filepath = FileRenameWithKeyword.renameFileWithKeyword(filepath, ")", "");
            systemPrintOut("Change suffix:" + filepath, 1, 0);
        }
    }

    /**
     * 从文件名中提取字符串的方法
     *
     * @param filePath 文件路径
     * @param keyword1 关键词1
     * @param keyword2 关键词2
     * @return 提取的字符串
     */
    public static String extractStringFromFileName(String filePath, String keyword1, String keyword2)
    {
        // 创建文件对象
        File file = new File(filePath);

        // 获取文件名
        String fileName = file.getName();

        // 初始化结果字符串
        String result = "";

        // 寻找关键词1的位置
        int indexKeyword1 = fileName.indexOf(keyword1);

        // 如果找到关键词1
        if (indexKeyword1 != - 1)
        {
            // 寻找关键词2的位置
            int indexKeyword2 = fileName.indexOf(keyword2, indexKeyword1 + keyword1.length());

            // 如果找到关键词2
            if (indexKeyword2 != - 1)
            {
                // 提取关键词1和关键词2之间的字符串
                result = fileName.substring(indexKeyword1 + keyword1.length(), indexKeyword2);
            }
        }

        // 返回结果字符串
        return result;
    }
}
