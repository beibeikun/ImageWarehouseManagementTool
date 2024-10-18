package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

public class OrganizeFiles
{
    /**
     * 整理指定文件夹中的文件编号
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void organizeFileNumbers(String sourceFolderPath, String targetFolderPath) throws IOException
    {
        // 创建系统检查器
        SystemChecker system = new SystemChecker();
        //targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = FileNameProcessor.processFileNames(FileLister.getFileNames(targetFolderPath));

        // 对文件名列表进行排序
        Arrays.sort(fileNameList);

        // 遍历每个文件名
        for (String s : fileNameList)
        {
            int[] getMatchingNumbers = getMatchingNumbers(targetFolderPath, s);
            Arrays.sort(getMatchingNumbers);
            int num = 1;
            for (int x : getMatchingNumbers)
            {
                systemPrintOut(s + " (" + x + ").jpg  ->  " + s + " (" + num + ").jpg",1,0);
                RenameFiles.renameFileWithName(targetFolderPath + system.identifySystem_String() + s + " (" + x + ").jpg", s + " (" + num + ")");
                num++;
            }
        }
        systemPrintOut(null, 0, 0);
    }
    /**
     * 将指定文件夹中的文件编号前移
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void moveNumberForward(String sourceFolderPath, String targetFolderPath) throws IOException
    {
        // 创建系统检查器
        SystemChecker system = new SystemChecker();

        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = FileNameProcessor.processFileNames(FileLister.getFileNames(targetFolderPath));

        // 对文件名列表进行排序
        Arrays.sort(fileNameList);

        // 遍历每个文件名
        for (String s : fileNameList)
        {
            int number = 1;

            // 循环查找同名文件
            while (true)
            {
                // 构造文件路径
                String filePath = targetFolderPath + system.identifySystem_String() + s + " (" + number + ").jpg";

                // 判断文件是否存在
                boolean fileExists = FileSearch.isFileExists(filePath);

                // 文件存在
                if (fileExists)
                {
                    // 如果是第一个文件，则重命名为原文件名
                    if (number == 1)
                    {
                        systemPrintOut(s + " (" + number + ").jpg  ->  " + s + ".jpg",1,0);
                        RenameFiles.renameFileWithName(filePath, s);
                    }
                    else
                    {
                        // 否则将文件重命名为原文件名 + (number - 1)
                        int newNum = number - 1;
                        systemPrintOut(s + " (" + number + ").jpg  ->  " + s + " (" + newNum + ").jpg",1,0);
                        RenameFiles.renameFileWithName(filePath, s + " (" + newNum + ")");
                    }
                }
                else
                {
                    // 文件不存在，跳出循环
                    break;
                }

                // 递增编号
                number++;
            }
        }
        systemPrintOut(null, 0, 0);
    }
    public static int[] getMatchingNumbers(String folderPath, String prefix)
    {
        List<Integer> numberList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            Pattern pattern = Pattern.compile("^" + Pattern.quote(prefix) + " \\((\\d+)\\)\\.[^\\\\.]+$");
            for (File file : files)
            {
                if (file.isFile())
                {
                    String fileName = file.getName();
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.matches())
                    {
                        int number = Integer.parseInt(matcher.group(1));
                        numberList.add(number);
                    }
                }
            }
        }
        else
        {
            System.out.println("Folder is empty or does not exist.");
        }

        // Convert list to array
        int[] numbers = new int[numberList.size()];
        for (int i = 0; i < numberList.size(); i++)
        {
            numbers[i] = numberList.get(i);
        }
        return numbers;
    }
}
