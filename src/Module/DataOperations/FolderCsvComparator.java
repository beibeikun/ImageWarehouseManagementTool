package Module.DataOperations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static Module.DataOperations.FileLister.getFileNames;
import static Module.DataOperations.FileNameProcessor.processFileNames;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class FolderCsvComparator
{
    /**
     * 比较文件夹内文件的文件名和CSV文件中列举的文件名，
     * 将不在CSV中但在文件夹内的文件名输出到第一列，
     * 将不在文件夹内但在CSV中的文件名输出到第二列，
     * 并保存到CSV文件中。
     *
     * @param folderPath  文件夹路径
     * @param csvFilePath CSV文件路径
     * @throws IOException 如果发生文件读写错误
     */
    public static void compareAndGenerateCsv(String folderPath, String csvFilePath, String outpath) throws IOException
    {
        systemPrintOut("Start to compare", 3, 0);
        String outputCsvPath = outpath + File.separator + "COMPARISON_RESULT.csv";
        systemPrintOut("Create csv", 1, 0);
        String[] filenames = processFileNames(getFileNames(folderPath));
        systemPrintOut("Get filenames in path", 1, 0);
        String[] csvnames = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(csvFilePath), 0);
        systemPrintOut("Get filenames in csv", 1, 0);
        Set<String> diffAtoB = getArrayDifference(filenames, csvnames);
        Set<String> diffBtoA = getArrayDifference(csvnames, filenames);
        systemPrintOut("Compare finish", 1, 0);
        writeCsv(outputCsvPath, diffAtoB, diffBtoA);
        systemPrintOut("Write to csv finish", 1, 0);
        systemPrintOut("", 0, 0);
    }

    /**
     * 获取在数组A中但不在数组B中的元素的差集。
     *
     * @param arrayA 第一个字符串数组
     * @param arrayB 第二个字符串数组
     * @return 包含在数组A中但不在数组B中的元素的Set
     */
    public static Set<String> getArrayDifference(String[] arrayA, String[] arrayB)
    {
        // 将数组A转换为Set
        Set<String> setA = new HashSet<>(Arrays.asList(arrayA));

        // 将数组B转换为Set
        Set<String> setB = new HashSet<>(Arrays.asList(arrayB));

        // 使用Set的差集操作
        setA.removeAll(setB);

        return setA;
    }

    /**
     * 将结果写入CSV文件，第一列为不在CSV中但在文件夹内的文件名，
     * 第二列为不在文件夹内但在CSV中的文件名。
     *
     * @param outputPath  输出CSV文件路径
     * @param notInCsv    不在CSV中但在文件夹内的文件名集合
     * @param notInFolder 不在文件夹内但在CSV中的文件名集合
     * @throws IOException 如果发生文件写入错误
     */
    private static void writeCsv(String outputPath, Set<String> notInCsv, Set<String> notInFolder) throws IOException
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath)))
        {
            writer.println("Not_In_CSV, Not_In_Folder");

            int maxSize = Math.max(notInCsv.size(), notInFolder.size());
            for (int i = 0; i < maxSize; i++)
            {
                String csvEntry = i < notInCsv.size() ? notInCsv.toArray(new String[0])[i] : "";
                String folderEntry = i < notInFolder.size() ? notInFolder.toArray(new String[0])[i] : "";
                writer.println(csvEntry + "," + folderEntry);
            }
        }
    }
}