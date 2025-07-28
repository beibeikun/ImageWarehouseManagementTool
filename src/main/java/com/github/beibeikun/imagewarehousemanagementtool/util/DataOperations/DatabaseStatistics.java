package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;
import com.github.beibeikun.imagewarehousemanagementtool.constant.regex;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch.isFileExists;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;
import static com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister.getFileNamesInString;

public class DatabaseStatistics {
    public static void databaseStatistics(String databaseAddress)
    {
        String[] files = getFileNamesInString(databaseAddress);
        Arrays.sort(files);
        files = removeElement(files, "#recycle");
        files = removeElement(files, "thumbnail");
        System.out.println(Arrays.toString(files));
        System.out.println(files.length);
        int i1 = 0;
        int i2 = 0;
        for (String file : files)
        {

            String[] fileNames = getFileNamesInString(databaseAddress + identifySystem_String() + file);
            Arrays.sort(fileNames);
            i1+=fileNames.length;
            System.out.println(i1);
            /*
            Arrays.sort(fileNames);
            System.out.println(Arrays.toString(fileNames));
            i+=fileNames.length;
            System.out.println(i);

             */
            for (String fileName : fileNames)
            {
                int position = fileName.indexOf(".");
                String fileNamePrefix = fileName.substring(0, position);
                if (!isFileExists(databaseAddress + identifySystem_String() + "thumbnail" + identifySystem_String() + fileNamePrefix + ".jpg"))
                {
                    System.out.println(fileNamePrefix);
                }
            }

            if (!checkFolder(false,databaseAddress + identifySystem_String() + file,true, regex.REGEX_STANDARD_FILE_NAME_NO_EXTENSION,false,null,false,null,false,null))
            {
                System.out.println(Arrays.toString(fileNames));
                i2+=fileNames.length;
                System.out.println(i2);
            }
        }
    }

    public static String[] removeElement(String[] input, String target) {
        return Arrays.stream(input)
                .filter(s -> !s.equals(target))
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        databaseStatistics("/Volumes/CameraDatabase");
    }
}
