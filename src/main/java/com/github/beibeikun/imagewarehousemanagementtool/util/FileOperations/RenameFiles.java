package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.filter.HiddenFilesChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.data.ArrayExtractor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ReadCsvFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

/**
 * 文件重命名工具类。
 */
public class RenameFiles
{

    /**
     * 根据映射关系从文件夹中的文件重命名。
     *
     * @param excelPath   Excel文件路径
     * @param imagePath   图片文件夹路径
     * @param digitCheck  数字补零检查标志，1表示补零，0表示不补零
     * @param prefixCheck 前缀添加检查标志，1表示添加前缀，0表示不添加前缀
     */
    public static void renameFiles(String excelPath, String imagePath, int digitCheck, int prefixCheck)
    {
        // 从Excel中获取映射关系
        //String[][] JB_LOTArray = csvToArray(excelPath);
        String[] ContractNoArray = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(excelPath), 0);
        String[] LOTArray = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(excelPath), 1);

        // 获取图片文件夹下的文件列表
        File imageFolder = new File(imagePath);
        File[] imageList = imageFolder.listFiles();
        Arrays.sort(imageList);

        // 检查文件夹是否存在且为目录
        if (imageFolder.exists() && imageFolder.isDirectory())
        {
            // 遍历文件列表
            for (File image : imageList)
            {
                if (HiddenFilesChecker.isSystemOrHiddenFile(image))
                {
                    // 跳过系统或隐藏文件
                    continue;
                }
                else
                {
                    // 获取文件名
                    String name = image.getName();

                    // 执行文件重命名
                    renameFile(image, name, ContractNoArray, LOTArray, imagePath, digitCheck, prefixCheck);
                }
            }
        }
    }

    /**
     * 根据映射关系重命名单个文件。
     *
     * @param image       待重命名的文件
     * @param name        文件名
     * @param JBArray     JB 数组
     * @param LOTArray    LOT 数组
     * @param imagePath   图片文件夹路径
     * @param digitCheck  数字补零检查标志，1表示补零，0表示不补零
     * @param prefixCheck 前缀添加检查标志，1表示添加前缀，0表示不添加前缀
     */
    private static void renameFile(File image, String name, String[] JBArray, String[] LOTArray, String imagePath, int digitCheck, int prefixCheck)
    {
        int position = name.indexOf(" ");
        if (position == -1)
        {
            position = name.lastIndexOf(".");
        }
        // 文件名格式检查，确保索引不越界
        String frontName = name.substring(0, position);
        String behindName = name.substring(position);

        // 根据映射关系进行重命名
        boolean renamed = false;
        for (int i = 0; i < JBArray.length; i++)
        {
            if (JBArray[i].equals(frontName))
            {
                String newName = LOTArray[i] + behindName;
                if (digitCheck == 1)
                {
                    try
                    {
                        int num = Integer.parseInt(LOTArray[i]);
                        if (num < 10)
                        {
                            newName = "00" + newName;
                        }
                        else if (num < 100)
                        {
                            newName = "0" + newName;
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        // 处理数字解析异常
                        systemPrintOut("Failed:" + name, 2, 0);
                        return;
                    }
                }
                if (prefixCheck == 1)
                {
                    newName = "Lot" + newName; // 添加前缀
                }

                File dest = new File(imagePath + File.separator + newName);
                if (image.renameTo(dest))
                {
                    systemPrintOut("Renamed:" + name + " --> " + newName, 1, 0);
                    renamed = true;
                    break;
                }
            }
        }
        if (! renamed)
        {
            systemPrintOut("Failed:" + name, 2, 0);
        }
    }

    /**
     * 将文件重命名为指定名称
     *
     * @param filePath 文件路径
     * @param newName  新名称
     * @throws IOException 如果文件不存在或目标文件已存在
     */
    public static void renameFileWithName(String filePath, String newName) throws IOException
    {
        // 获取文件后缀
        String suffix = "";
        int index = filePath.lastIndexOf(".");
        if (index != - 1)
        {
            suffix = filePath.substring(index);
        }

        // 将文件重命名为 newName + 后缀
        File file = new File(filePath);
        File newFile = new File(file.getParentFile(), newName + suffix);
        file.renameTo(newFile);
    }

}
