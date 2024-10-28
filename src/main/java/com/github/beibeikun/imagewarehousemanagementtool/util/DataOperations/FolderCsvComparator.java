package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut;
import com.github.beibeikun.imagewarehousemanagementtool.util.data.ArrayExtractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister.getFileNamesInString;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor.processFileNames;
import static com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ListExtractor.removeElementFromList;

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
        outpath = CreateFolder.createFolderWithTime(outpath);
        SystemPrintOut.systemPrintOut("Start to compare", 3, 0);
        String outputCsvPath = outpath + File.separator + "COMPARISON_RESULT.csv";
        SystemPrintOut.systemPrintOut("Create csv", 1, 0);
        String[] filenames = processFileNames(getFileNamesInString(folderPath));
        SystemPrintOut.systemPrintOut("Get filenames in path", 1, 0);
        String[] csvnames = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(csvFilePath), 0);
        SystemPrintOut.systemPrintOut("Get filenames in csv", 1, 0);
        Set<String> diffAtoB = removeElementFromList(filenames, csvnames);
        Set<String> diffBtoA = removeElementFromList(csvnames, filenames);
        SystemPrintOut.systemPrintOut("Compare finish", 1, 0);
        writeCsv(outputCsvPath, diffAtoB, diffBtoA);
        SystemPrintOut.systemPrintOut("Write to csv finish", 1, 0);
        SystemPrintOut.systemPrintOut("", 0, 0);
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