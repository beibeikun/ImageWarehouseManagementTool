import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV文件读取器和处理器。
 */
public class CsvReaderToList {
    /**
     * 读取CSV文件并按要求处理数据。
     *
     * @param csvPath CSV文件的路径。
     * @return 一个按指定行数分割的多层嵌套列表。
     */
    public static List<List<String[][]>> readCsvAndProcess(String csvPath) {
        // 首先解析CSV到数据块列表
        List<String[][]> csvData = parseCsvToDataBlocks(csvPath);
        List<List<String[][]>> allDataLists = new ArrayList<>();
        // 对每个数据块按行分割为更小的数据块
        for (String[][] dataBlock : csvData) {
            allDataLists.add(splitDataBlockByRows(dataBlock, 20));
        }
        return allDataLists;
    }

    /**
     * 根据给定的行数分割数据块。
     *
     * @param original 原始的数据块。
     * @param maxRows 最大行数，用于分割数据块。
     * @return 分割后的数据块列表。
     */
    private static List<String[][]> splitDataBlockByRows(String[][] original, int maxRows) {
        List<String[][]> result = new ArrayList<>();
        int totalBlocks = (original.length + maxRows - 1) / maxRows; // 计算需要多少个分组

        for (int i = 0; i < totalBlocks; i++) {
            int startRow = i * maxRows;
            int endRow = Math.min((i + 1) * maxRows, original.length); // 确保不越界
            String[][] block = new String[endRow - startRow][];

            System.arraycopy(original, startRow, block, 0, endRow - startRow);
            result.add(block);
        }

        return result;
    }

    /**
     * 从文件路径读取CSV文件，并按分组标识将数据分割成多个数据块。
     *
     * @param filePath 文件路径。
     * @return 分组后的数据块列表。
     */
    private static List<String[][]> parseCsvToDataBlocks(String filePath) {
        List<String[][]> dataBlocks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentGroup = ""; // 当前分组的标识符
            List<String[]> tempBlock = new ArrayList<>();

            br.readLine(); // 跳过标题行

            while ((line = br.readLine()) != null) {
                String[] items = line.split(",", -1);
                // 当分组标识变更时，保存当前数据块并开始新的数据块
                if (!currentGroup.equals(items[1])) {
                    if (!tempBlock.isEmpty()) {
                        dataBlocks.add(tempBlock.toArray(new String[0][]));
                        tempBlock.clear();
                    }
                    currentGroup = items[1];
                }
                tempBlock.add(items);
            }
            // 确保最后一组数据被添加
            if (!tempBlock.isEmpty()) {
                dataBlocks.add(tempBlock.toArray(new String[0][]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataBlocks;
    }
}
