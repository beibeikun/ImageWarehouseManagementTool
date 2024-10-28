package com.github.beibeikun.imagewarehousemanagementtool.util.language;
import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class ChineseConverter {

    /**
     * 将二维字符串数组中的所有简体中文字符转换成繁体中文字符
     * @param simplifiedArray 二维字符串数组，包含简体中文
     * @return 返回繁体中文的二维字符串数组
     */
    public static String[][] convertSimplifiedToTraditional(String[][] simplifiedArray) {
        if (simplifiedArray == null) {
            return null;
        }

        // 获取原数组的行数和列数
        int rows = simplifiedArray.length;
        String[][] traditionalArray = new String[rows][];

        // 遍历每一个元素进行转换
        for (int i = 0; i < rows; i++) {
            int cols = simplifiedArray[i].length;
            traditionalArray[i] = new String[cols];
            for (int j = 0; j < cols; j++) {
                traditionalArray[i][j] = ZhConverterUtil.toTraditional(simplifiedArray[i][j]);
            }
        }

        return traditionalArray;
    }
}
