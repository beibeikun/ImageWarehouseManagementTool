package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.util.HashSet;
import java.util.Set;

import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ArrayExtractor.extractRow;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;

public class CsvChecker
{
    public static boolean checkCsv(String csvFilePath)
    {

        String[] csvRow0 = extractRow(ReadCsvFile.csvToArray(csvFilePath), 0);
        String[] csvRow1 = extractRow(ReadCsvFile.csvToArray(csvFilePath), 1);

        if (csvRowCheck(csvRow0,"^[A-Z][A-Z0-9]*-\\d+$"))
        {
            return false;
        }


        if (csvRow1.length != 0)
        {
            if (csvRowCheck(csvRow0,"^\\d+$"))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查CSV行中的元素是否符合给定的正则表达式格式，并检查是否有重复元素
     * @param csvRow 需要检查的CSV行
     * @param pattern 元素的正则表达式格式
     * @return 如果有非法元素或重复元素返回true，否则返回false
     */
    private static boolean csvRowCheck(String[] csvRow, String pattern)
    {
        Set<String> elementSet = new HashSet<>();

        for (String element : csvRow)
        {
            // 检查是否存在重复元素
            if (!elementSet.add(element))
            {
                systemPrintOut("Duplicate elements in CSV", 2, 0);
                return true;  // 有重复元素
            }

            // 检查元素格式是否符合正则表达式
            if (!element.matches(pattern))
            {
                systemPrintOut("Illegal elements in CSV", 2, 0);
                return true;  // 格式不合法
            }
        }

        return false; // 没有重复或非法元素
    }
}
