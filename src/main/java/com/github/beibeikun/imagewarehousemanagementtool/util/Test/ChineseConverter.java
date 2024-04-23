package com.github.beibeikun.imagewarehousemanagementtool.util.Test;
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

    // 主方法，用于演示
    public static void main(String[] args) {
        String[][] simplifiedArray = {
                {"中国", "发展"},
                {"简体字", "转换成繁体字"}
        };

        String[][] traditionalArray = convertSimplifiedToTraditional(simplifiedArray);

        // 打印转换结果
        for (String[] row : traditionalArray) {
            for (String word : row) {
                System.out.print(word + " ");
            }
            System.out.println();  // 换行
        }
    }
}
