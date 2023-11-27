package Module.Refactor;
import java.util.Arrays;

/**
 * 数组操作工具类，用于从数组中提取子数组。
 */
public class ArrayExtractor {

    /**
     * 从输入数组中排除第一个元素并返回新数组。
     *
     * @param inputArray 输入数组
     * @return 排除第一个元素后的新数组
     */
    static String[] excludeFirstElement(String[] inputArray) {
        // 检查数组是否为空或只有一个元素
        if (inputArray == null || inputArray.length <= 1) {
            return new String[0]; // 返回空数组或原数组
        }

        // 使用 Arrays.copyOfRange 创建新数组，排除第一个元素
        return Arrays.copyOfRange(inputArray, 1, inputArray.length);
    }

    /**
     * 从二维数组中提取指定行并转换为 String 数组。
     *
     * @param inputArray 输入的二维数组
     * @param rowIndex 要提取的行的索引（从0开始）
     * @return 提取后的字符串数组
     */
    static String[] extractRow(String[][] inputArray, int rowIndex) {
        // 检查数组是否为空或索引是否越界
        if (inputArray == null || rowIndex < 0 || rowIndex >= inputArray.length) {
            return new String[0]; // 返回空数组
        }

        // 直接返回指定行的克隆
        return inputArray[rowIndex].clone();
    }
}
