package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ReadCsvFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.data.ArrayExtractor.extractRow;
import static com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister.getFileNamesInString;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor.processFileNames;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

public class ExtractMainImage
{

    /**
     * 从源文件夹中提取主图像到目标文件夹。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     */
    public static void extractMainImage(String sourceFolder, String destinationFolder, boolean useCsvCheck, String csvPath) throws IOException
    {
        systemPrintOut("Start to take main img from source path", 3, 0);
        destinationFolder = CreateFolder.createFolderWithTime(destinationFolder);
        File folder = new File(sourceFolder);

        if (! folder.exists() || ! folder.isDirectory())
        {
            systemPrintOut("Source folder does not exist or is not a directory.", 2, 0);
            return;
        }
        //获取源文件夹内所有文件名,处理文件名数组，去除文件后缀名、去除 "(x)" 后缀并删除重复项，只保留一个
        String[] FileNames = processFileNames(getFileNamesInString(sourceFolder));

        if (useCsvCheck)
        {
            FileNames = getDuplicateElements(FileNames,extractRow(ReadCsvFile.csvToArray(csvPath), 0));
        }


        Arrays.sort(FileNames);
        for (String fileName : FileNames)
        {
            File file = new File(sourceFolder + identifySystem_String() + fileName + ".JPG");
            if (file.exists()) {
                FileCopyAndDelete.copyFile(sourceFolder + identifySystem_String() + fileName + ".JPG", destinationFolder);
                systemPrintOut("Copy:" + fileName, 1, 0);
            }
            else
            {
                int counter = 1;
                while (true)
                {
                    File fileInCount = new File(sourceFolder + identifySystem_String() + concatenateFilenameStrings(fileName, counter));
                    if (fileInCount.exists())
                    {
                        FileCopyAndDelete.copyFile(sourceFolder + identifySystem_String() + concatenateFilenameStrings(fileName, counter), destinationFolder);
                        systemPrintOut("Copy:" + concatenateFilenameStrings(fileName, counter), 1, 0);
                        break;
                    }
                    else
                    {
                        counter++;
                    }
                }
            }
        }
        systemPrintOut(null, 0, 0);
    }
    private static String[] getDuplicateElements(String[] array1, String[] array2) {
        Set<String> set1 = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        // 将第一个数组的元素加入到set1中
        for (String element : array1) {
            set1.add(element);
        }

        // 检查第二个数组的元素是否在set1中，若在则为重复元素
        for (String element : array2) {
            if (set1.contains(element)) {
                duplicates.add(element);
            }
        }

        // 将Set转为String[]
        return duplicates.toArray(new String[0]);
    }
    private static String concatenateFilenameStrings(String fileName, int counter) {
        return fileName + " (" + counter + ").jpg";
    }

}
