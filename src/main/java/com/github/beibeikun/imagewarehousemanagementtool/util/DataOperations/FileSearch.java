package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件搜索类
 */
public class FileSearch
{

    /**
     * 在指定文件夹中查找包含关键词的文件。
     *
     * @param folderPath 文件夹路径
     * @param keyword    关键词
     * @return 包含关键词的文件列表
     */
    public static List<File> searchFiles(String folderPath, String keyword)
    {
        // 存储符合条件的文件列表
        List<File> resultFiles = new ArrayList<>();

        // 创建文件对象表示指定文件夹
        File folder = new File(folderPath);

        // 判断文件夹是否存在
        if (folder.isDirectory())
        {
            // 获取文件夹下的文件列表
            File[] files = folder.listFiles();

            // 判断文件列表是否为空
            if (files != null)
            {
                // 遍历文件列表
                for (File file : files)
                {
                    // 判断文件是否为普通文件且文件名包含关键词
                    if (file.isFile() && file.getName().contains(keyword))
                    {
                        // 将符合条件的文件添加到结果列表
                        resultFiles.add(file);
                    }
                }
            }
        }

        // 返回包含关键词的文件列表
        return resultFiles;
    }

    /**
     * 查找指定路径的文件是否存在
     *
     * @param filePath 需要查找的文件路径名
     * @return 存在返回true，不存在返回false
     * @throws IOException
     */
    public static boolean isFileExists(String filePath) throws IOException
    {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    //TODO:检测不到隐藏文件
    public static boolean isDirectoryEmpty(String dirPath)
    {
        File dir = new File(dirPath);
        if (! dir.isDirectory())
        {
            return false;
        }

        String[] files = dir.list();
        return files == null || files.length == 0;
    }

}
