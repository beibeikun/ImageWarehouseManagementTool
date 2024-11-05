package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;
import com.github.beibeikun.imagewarehousemanagementtool.constant.regex;
import com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteDirectory.deleteFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

public class OrganizeFiles
{
    /**
     * 整理指定文件夹中的文件编号
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void organizeFileNumbers(String sourceFolderPath, String targetFolderPath, boolean moveFilesChecker) throws IOException
    {
        Files.createDirectories(Paths.get(targetFolderPath));
        if (!checkFolder(sourceFolderPath,true, regex.REGEX_STANDARD_FILE_NAME,targetFolderPath,false,printOutMessage.NULL,false,printOutMessage.NULL))
        {
            systemPrintOut(printOutMessage.INVALID_PATH_STOP_TASK,2,0);
            systemPrintOut(printOutMessage.NULL,0,0);
            deleteFile(targetFolderPath);
            return;
        }
        //targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        if (moveFilesChecker)
        {
            FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        }
        systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = FileNameProcessor.processFileNames(FileLister.getFileNamesInString(targetFolderPath));

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
                if (x==0)
                {
                    systemPrintOut(s + ".jpg  ->  " + s + " (" + num + ").jpg",1,0);
                    RenameFiles.renameFileWithName(targetFolderPath + identifySystem_String() + s + ".jpg", s + " (" + num + ").");
                }
                else {
                    systemPrintOut(s + " (" + x + ").jpg  ->  " + s + " (" + num + ").jpg",1,0);
                    RenameFiles.renameFileWithName(targetFolderPath + identifySystem_String() + s + " (" + x + ").jpg", s + " (" + num + ").");
                }
                num++;
            }
        }
        replaceDoubleDots(targetFolderPath);
        systemPrintOut(null, 0, 0);
    }
    /**
     * 将指定文件夹中的文件编号前移
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void moveNumberForward(String sourceFolderPath, String targetFolderPath, boolean moveFilesChecker) throws IOException
    {
        // 创建系统检查器
        SystemChecker system = new SystemChecker();


        if (moveFilesChecker == true)
        {
            targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
            FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        }
        systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = FileNameProcessor.processFileNames(FileLister.getFileNamesInString(targetFolderPath));

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
                String filePath = targetFolderPath + identifySystem_String() + s + " (" + number + ").jpg";

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
    private static int[] getMatchingNumbers(String folderPath, String prefix)
    {
        List<Integer> numberList = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            // 修改正则表达式，允许文件名没有数字部分
            Pattern pattern = Pattern.compile("^" + Pattern.quote(prefix) + "(?: \\((\\d+)\\))?\\.[^\\\\.]+$");
            for (File file : files)
            {
                if (file.isFile())
                {
                    String fileName = file.getName();
                    Matcher matcher = pattern.matcher(fileName);
                    if (matcher.matches())
                    {
                        // 如果匹配到了括号中的数字，则提取该数字
                        if (matcher.group(1) != null)
                        {
                            int number = Integer.parseInt(matcher.group(1));
                            numberList.add(number);
                        }
                        else
                        {
                            // 如果没有括号中的数字，默认添加 0
                            numberList.add(0);
                        }
                    }
                }
            }
        }

        // Convert list to array
        int[] numbers = new int[numberList.size()];
        for (int i = 0; i < numberList.size(); i++)
        {
            numbers[i] = numberList.get(i);
        }
        return numbers;
    }
    private static void replaceDoubleDots(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();

                    // 检查文件名是否包含 ".."
                    if (fileName.contains("..")) {
                        // 替换文件名中的 ".." 为 "."
                        String newFileName = fileName.replace("..", ".");

                        // 获取文件的父目录路径，用于重命名
                        File newFile = new File(file.getParent(), newFileName);

                        // 重命名文件
                        file.renameTo(newFile);
                    }
                }
            }
        } else {
            System.out.println(printOutMessage.EMPTY_FOLDER_PATH);
        }
    }
}
