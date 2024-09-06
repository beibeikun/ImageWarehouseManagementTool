package com.github.beibeikun.imagewarehousemanagementtool.util.Test;

import java.io.File;

//TODO:现在还没啥用，可以统一一下后缀名？我不知道
public class FileExtensionConverter
{
    /**
     * 将指定目录下所有文件的扩展名转换为大写
     *
     * @param directoryPath 目录路径
     */
    public static void convertFileExtensionsToUppercase(String directoryPath)
    {

        // 检查目录是否存在
        File directory = new File(directoryPath);
        if (! directory.isDirectory())
        {
            System.out.println("无效目录路径: " + directoryPath);
            return;
        }

        // 获取目录下所有文件
        File[] files = directory.listFiles();
        if (files == null)
        {
            System.out.println("无法访问目录中的文件: " + directoryPath);
            return;
        }

        // 遍历文件并转换扩展名
        for (File file : files)
        {
            // 判断是否为文件
            if (file.isFile())
            {
                // 获取文件名
                String fileName = file.getName();
                // 获取文件扩展名
                String extension = getFileExtension(fileName);

                // 转换扩展名
                if (! extension.equals(extension.toUpperCase()))
                {
                    // 构造新的文件名
                    String newFileName = fileName.substring(0, fileName.lastIndexOf('.') + 1) + extension.toUpperCase();
                    // 创建新的 File 对象
                    File newFile = new File(directory, newFileName);

                    // 重命名文件
                    if (file.renameTo(newFile))
                    {
                        // 输出重命名成功信息
                        System.out.println("重命名文件: " + fileName + " -> " + newFileName);
                    }
                    else
                    {
                        // 输出重命名失败信息
                        System.out.println("重命名文件失败: " + fileName);
                    }
                }
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    private static String getFileExtension(String fileName)
    {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1)
        {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
