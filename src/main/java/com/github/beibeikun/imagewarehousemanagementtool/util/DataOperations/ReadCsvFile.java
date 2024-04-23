package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import org.apache.commons.io.input.BOMInputStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCsvFile
{
    /**
     * 读取 CSV 文件，并将其内容存储到二维数组中
     *
     * @param csvPath CSV 文件的路径
     * @return 存储 CSV 数据的二维数组
     */
    public static String[][] csvToArray(String csvPath)
    {
        List<String[]> dataList = new ArrayList<>(); // 存储 CSV 数据的动态数组
        int maxColumns = 0; // 最大的列数

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(csvPath)))))
        {
            String line;
            String cvsSplitBy = ",";

            while ((line = br.readLine()) != null)
            {
                String[] row = line.split(cvsSplitBy); // 使用逗号作为分隔符拆分行数据
                dataList.add(row); // 将拆分的行数据添加到动态数组中
                maxColumns = Math.max(maxColumns, row.length); // 更新最大列数
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new String[0][0]; // 文件读取出错时，返回空数组
        }

        // 将动态数组转换为二维数组，使用最大列数来填充行数据
        String[][] dataArray = new String[maxColumns][dataList.size()];
        for (int i = 0; i < dataList.size(); i++)
        {
            String[] row = dataList.get(i);
            for (int j = 0; j < maxColumns; j++)
            {
                if (j < row.length)
                {
                    dataArray[j][i] = row[j];
                }
                else
                {
                    dataArray[j][i] = ""; // 对于不满列数的行，用空字符串填充
                }
            }
        }

        return dataArray;
    }
    public static String[] readColumn(String filePath, int columnIndex) throws IOException {
        List<String> results = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             BOMInputStream bomIn = new BOMInputStream(fis);
             BufferedReader reader = new BufferedReader(new InputStreamReader(bomIn, "UTF-8"))) { // 使用UTF-8编码
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1); // 假设逗号为分隔符, 传入-1以保留空白结果
                if (values.length > columnIndex) {
                    String value = values[columnIndex].isEmpty() ? "" : values[columnIndex]; // 如果空，替换为一个空格
                    results.add(value);
                } else {
                    // 如果该行数据少于columnIndex指定的列数，添加一个空格作为默认值
                    results.add(" ");
                }
            }
        }
        return results.toArray(new String[0]); // 转换列表为数组并返回
    }
    public static void main(String[] args) {
        try {
            String[] data = readColumn("/Users/bbk/photographs/各种模版/test.csv", 1); // 调用函数，读取第一列
            for (String element : data) {
                System.out.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}