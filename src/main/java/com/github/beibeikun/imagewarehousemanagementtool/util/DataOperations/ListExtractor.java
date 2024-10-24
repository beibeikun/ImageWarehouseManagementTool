package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.util.*;

/**
 * 列表操作类
 */
public class ListExtractor
{
    /**
     * 输入listA和listB，移除listA中与listB重复的元素并返回
     *
     * @param listA 需要移除元素的list
     * @param listB 包含移除元素内容的list
     * @return 移除与listB重复元素的listA
     */
    public static List<String> removeElementFromList(List<String> listA, List<String> listB)
    {
        Set<String> setA = new HashSet<>(listA);
        Set<String> setB = new HashSet<>(listB);
        // 将 setB 中的元素从 setA 中删除
        setA.removeAll(setB);
        // 将 setA 转换为 List<String>
        return new ArrayList<>(setA);
    }
    /**
     * 获取在数组A中但不在数组B中的元素的差集。
     *
     * @param arrayA 第一个字符串数组
     * @param arrayB 第二个字符串数组
     * @return 包含在数组A中但不在数组B中的元素的Set
     */
    public static Set<String> removeElementFromList(String[] arrayA, String[] arrayB)
    {
        // 将数组A转换为Set
        Set<String> setA = new HashSet<>(Arrays.asList(arrayA));

        // 将数组B转换为Set
        Set<String> setB = new HashSet<>(Arrays.asList(arrayB));

        // 使用Set的差集操作
        setA.removeAll(setB);

        return setA;
    }
}
