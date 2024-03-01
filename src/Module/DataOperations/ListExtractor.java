package Module.DataOperations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 列表操作类
 */
public class ListExtractor {
    /**
     * 输入listA和listB，移除listA中与listB重复的元素并返回
     * @param listA 需要移除元素的list
     * @param listB 包含移除元素内容的list
     * @return 移除与listB重复元素的listA
     */
    public static List<String> removeElementFromList(List<String> listA, List<String> listB) {
        Set<String> setA = new HashSet<>(listA);
        Set<String> setB = new HashSet<>(listB);
        // 将 setB 中的元素从 setA 中删除
        setA.removeAll(setB);
        // 将 setA 转换为 List<String>
        return new ArrayList<>(setA);
    }
}
