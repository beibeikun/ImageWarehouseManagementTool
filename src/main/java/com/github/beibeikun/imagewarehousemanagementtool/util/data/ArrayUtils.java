package com.github.beibeikun.imagewarehousemanagementtool.util.data;

import java.util.Arrays;

/**
 * 数组操作工具类，用于从数组中提取子数组。
 */
public class ArrayUtils
{

    /**
     * 从二维数组中提取指定行并转换为 String 数组。
     *
     * @param inputArray 输入的二维数组
     * @param rowIndex   要提取的行的索引（从0开始）
     * @return 提取后的字符串数组
     */
    public static String[] extractRow(String[][] inputArray, int rowIndex)
    {
        // 检查数组是否为空或索引是否越界
        if (inputArray == null || rowIndex < 0 || rowIndex >= inputArray.length)
        {
            return new String[0]; // 返回空数组
        }

        // 直接返回指定行的克隆
        return inputArray[rowIndex].clone();
    }

    /**
     * 从数组中去除指定元素。
     *
     * @param input     输入的数组
     * @param target    要去除的元素
     * @return          去除后的数组
     */
    public static String[] removeElementFromArrays(String[] input, String target) {
        return Arrays.stream(input)
                .filter(s -> !s.equals(target))
                .toArray(String[]::new);
    }

    /**
     * 从数组中去除隐藏文件名。
     *
     * @param input     输入的数组
     * @return          去除后的数组
     */
    public static String[] removeHiddenFileNamesFromArrays(String[] input) {
        input = removeElementFromArrays(input, "boot.ini");
        input = removeElementFromArrays(input, "ntldr");
        input = removeElementFromArrays(input, "ntdetect.com");
        input = removeElementFromArrays(input, "thumbs.db");
        input = removeElementFromArrays(input, ".ds_store");
        input = removeElementFromArrays(input, ".localized");
        return input;
    }
}
