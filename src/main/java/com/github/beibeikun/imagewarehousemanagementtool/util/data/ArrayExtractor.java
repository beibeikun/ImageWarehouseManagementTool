package com.github.beibeikun.imagewarehousemanagementtool.util.data;

/**
 * 数组操作工具类，用于从数组中提取子数组。
 */
public class ArrayExtractor
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
}
