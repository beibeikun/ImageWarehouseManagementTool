package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.util.Arrays;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
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
        int i = 0;
        for (String file : files)
        {
            String[] fileNames = getFileNamesInString(databaseAddress + identifySystem_String() + file);
            Arrays.sort(fileNames);
            System.out.println(Arrays.toString(fileNames));
            i+=fileNames.length;
            System.out.println(i);
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
