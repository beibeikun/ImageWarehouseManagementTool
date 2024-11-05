package com.github.beibeikun.imagewarehousemanagementtool.filter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.HiddenFilesChecker.isSystemOrHiddenFile;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

public class FolderChecker
{

    /**
     * 检查源文件夹中的文件名是否符合规定的格式，并将不符合格式的文件名和原因输出。
     *
     * @param sourceFolder                    源文件夹路径，会检测路径是否为空
     * @param fileNameCheckQualifier          判定是否检查源文件夹中的文件名
     * @param fileNameRegex                   检查源文件夹中的文件名用的正则表达式，只有当fileNameCheckQualifier为true时生效
     * @param targetFolder                    目标文件夹路径，会检测路径是否为空
     * @param fileCheckQualifier              判定是否检查后面的文件路径是否合法
     * @param filePath                        文件路径，只有当fileCheckQualifier为true时生效
     * @param fileExtensionCheckQualifier 判定是否检查文件后缀名，只有当fileCheckQualifier为true时生效
     * @param fileExtension               用于检测的指定文件后缀名，只有当fileCheckQualifier与fileExtensionCheckQualifier为true时生效
     * @return 如果所有文件都符合要求，则返回 true；如果有不符合要求的文件，则返回 false
     */
    public static boolean checkFolder(String sourceFolder, boolean fileNameCheckQualifier, String fileNameRegex, String targetFolder, boolean fileCheckQualifier, String filePath, boolean fileExtensionCheckQualifier, String fileExtension)
    {
        systemPrintOut("开始检查路径的有效性",3,0);
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
            systemPrintOut("源文件夹路径不存在",2,0);
            return false;
        }
        //不存在目标文件夹
        else if (!targetDirectoryCheck.exists())
        {
            systemPrintOut("目标文件夹路径不存在",2,0);
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
                    systemPrintOut("文件路径不存在: "+filePath,2,0);
                    return false;
                }
                //如果为文件夹
                if (fileCheck.isDirectory())
                {
                    systemPrintOut("指定的文件路径不是一个有效文件: "+filePath,2,0);
                    return false;
                }
                //检查文件后缀名
                if (fileExtensionCheckQualifier)
                {
                    //文件扩展名不匹配
                    if (!getFileExtension(filePath).equals(fileExtension))
                    {
                        systemPrintOut("非法的文件扩展名: " + getFileExtension(filePath) +" 应为: "+ fileExtension,2,0);
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
                                    invalidFilesList.add(new String[]{file.getName(), "存在子文件夹"});
                                    returnValue[0] = false;
                                }
                                else if (file.isFile())
                                {
                                    Pattern pattern = Pattern.compile(fileNameRegex);
                                    // 判断文件是否是系统文件或隐藏文件
                                    if (!isSystemOrHiddenFile(file))
                                    {
                                        String fileName = file.getName();
                                        Matcher matcher = pattern.matcher(fileName);

                                        // 如果文件名不符合正则表达式格式
                                        if (!matcher.matches())
                                        {
                                            invalidFilesList.add(new String[]{file.getName(), "非法的文件名"});
                                            returnValue[0] = false;
                                        }
                                    }
                                }
                                else
                                {
                                    // 如果既不是文件也不是文件夹，记录为未知原因
                                    invalidFilesList.add(new String[]{file.getName(), "未知原因"});
                                    returnValue[0] = false;
                                }
                            });
                }
            }
        }

        // 如果存在不符合格式的文件或文件夹，则将其信息输出
        if (!invalidFilesList.isEmpty())
        {
            systemPrintOut("无效文件列表",3,0);
            // 输出List<String>的内容
            invalidFilesList.forEach(entry -> System.out.println(entry[0] + " 原因: " + entry[1]));
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
}
