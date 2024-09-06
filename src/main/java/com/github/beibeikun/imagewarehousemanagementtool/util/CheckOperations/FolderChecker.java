package com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

public class FolderChecker
{

    /**
     * 检查源文件夹中的文件名是否符合规定的格式，并将不符合格式的文件名和原因输出。
     *
     * @param sourceFolder                    源文件夹路径
     * @param targetFolder                    目标文件夹路径
     * @param fileNameCheckQualifier          判定是否检查文件名
     * @param filePath                        文件路径，只有当fileNameCheckQualifier为true时生效
     * @param filenameExtensionCheckQualifier 判定是否检查文件后缀名，只有当fileNameCheckQualifier为true时生效
     * @param filenameExtension               用于检测的指定文件后缀名，只有当fileNameCheckQualifier与filenameExtensionCheckQualifier为true时生效
     * @return 如果所有文件都符合要求，则返回 true；如果有不符合要求的文件，则返回 false
     */
    public static boolean checkFolder(String sourceFolder, String targetFolder, boolean fileCheckQualifier, String filePath, boolean filenameExtensionCheckQualifier, String filenameExtension, boolean fileNameCheckQualifier)
    {
        systemPrintOut("Start checking the validity of the path",3,0);
        // 用于存储返回值，true 表示所有文件符合格式，false 表示有文件不符合格式
        boolean[] returnValue = {true};
        // 用于存储不符合格式的文件名及其原因
        List<String[]> invalidFilesList = new ArrayList<>();

        // 创建 File 对象，表示源文件夹
        File sourceDirectoryCheck = new File(sourceFolder);
        File targetDirectoryCheck = new File(targetFolder);
        //不存在源文件夹
        if (!sourceDirectoryCheck.exists())
        {
            systemPrintOut("Non-existent source folder path",2,0);
            return false;
        }
        //不存在目标文件夹
        else if (!targetDirectoryCheck.exists())
        {
            systemPrintOut("Non-existent target folder path",2,0);
            return false;
        }
        //源文件夹于目标文件夹存在，下一步
        else
        {
            //检查文件路径
            if (fileCheckQualifier)
            {
                File fileCheck = new File(filePath);
                //如果文件不存在
                if (!fileCheck.exists())
                {
                    systemPrintOut("Non-existent file path: "+filePath,2,0);
                    return false;
                }
                //如果为文件夹
                if (fileCheck.isDirectory())
                {
                    systemPrintOut("This is a folder: "+filePath,2,0);
                    return false;
                }
                //检查文件后缀名
                if (filenameExtensionCheckQualifier)
                {
                    //文件扩展名不匹配
                    if (!getFileExtension(filePath).equals(filenameExtension))
                    {
                        systemPrintOut("Illegal file extension: " + getFileExtension(filePath) +" Should be: "+ filenameExtension,2,0);
                        return false;
                    }
                }
            }
            if (fileNameCheckQualifier)
            {
                // 获取文件夹中的所有文件和子文件夹
                File[] files = sourceDirectoryCheck.listFiles();
                if (files != null)
                {
                    // 对文件数组按文件名排序（按字母顺序）
                    Arrays.sort(files, Comparator.comparing(File::getName));

                    // 使用并行流处理文件和子文件夹，提高性能
                    Stream.of(files)
                            .parallel()  // 并行处理文件数组
                            .forEach(file ->
                            {
                                // 如果是子文件夹，标记为不合法
                                if (file.isDirectory())
                                {
                                    invalidFilesList.add(new String[]{file.getName(), "This is a folder"});
                                    returnValue[0] = false;
                                }
                                else if (file.isFile())
                                {
                                    // 定义正则表达式，检查文件名格式

                                    //这个正则表达式的作用是匹配符合以下格式的文件名：
                                    //前缀：必须以大写字母开头，并且只能包含大写字母和数字
                                    //编号：破折号后跟一串数字
                                    //可选的序号部分：可以有一个括号包裹的序号（数字），前面有一个空格
                                    //扩展名：文件扩展名可以是任意的合法字符，但不能包含点号 .
                                    String regex = "^[A-Z][A-Z0-9]*-\\d+( \\(\\d+\\))?\\.[^.]+$";
                                    Pattern pattern = Pattern.compile(regex);
                                    // 判断文件是否是系统文件或隐藏文件
                                    if (!isSystemOrHiddenFile(file))
                                    {
                                        String fileName = file.getName();
                                        Matcher matcher = pattern.matcher(fileName);

                                        // 如果文件名不符合正则表达式格式
                                        if (!matcher.matches())
                                        {
                                            invalidFilesList.add(new String[]{file.getName(), "Illegal file name"});
                                            returnValue[0] = false;
                                        }
                                    }
                                }
                                else
                                {
                                    // 如果既不是文件也不是文件夹，记录为未知原因
                                    invalidFilesList.add(new String[]{file.getName(), "Unknown reason"});
                                    returnValue[0] = false;
                                }
                            });
                }
            }
        }

        // 如果存在不符合格式的文件或文件夹，则将其信息输出
        if (!invalidFilesList.isEmpty())
        {
            systemPrintOut("Invalid files list",3,0);
            // 输出List<String>的内容
            invalidFilesList.forEach(entry -> System.out.println(entry[0] + " Reason: " + entry[1]));
        }
        systemPrintOut("",0,0);
        // 返回是否所有文件都符合格式要求
        return returnValue[0];
    }

    // 提取文件扩展名的方法
    private static String getFileExtension(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            int lastIndexOfDot = filePath.lastIndexOf('.');
            int lastIndexOfSeparator = filePath.lastIndexOf(File.separator);

            // 确保点在文件路径中最后一个分隔符之后，且不是最后一个字符
            if (lastIndexOfDot > lastIndexOfSeparator && lastIndexOfDot < filePath.length() - 1) {
                return filePath.substring(lastIndexOfDot + 1);
            }
        }
        return null; // 返回null表示没有找到扩展名
    }

    public static void main(String[] args)
    {
        // 调用 checkFolderName 方法，传入源文件夹和目标文件夹路径
        //boolean result = checkFolder("/Users/bbk/Downloads/test", "/Users/bbk/Downloads/test2",true);
        // 输出结果，表示是否所有文件都符合要求
        System.out.println("All files valid: ");
    }
}
