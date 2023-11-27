package Module.File;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FolderCsvComparator {
    /**
     * 比较文件夹内文件的文件名和CSV文件中列举的文件名，
     * 将不在CSV中但在文件夹内的文件名输出到第一列，
     * 将不在文件夹内但在CSV中的文件名输出到第二列，
     * 并保存到CSV文件中。
     *
     * @param folderPath    文件夹路径
     * @param csvFilePath   CSV文件路径
     * @throws IOException  如果发生文件读写错误
     */
    public static void compareAndGenerateCsv(String folderPath, String csvFilePath) throws IOException {
        String outputCsvPath = folderPath + File.separator + "COMPARISON_RESULT.csv";

        Set<String> folderFiles = listFilesInFolder(folderPath);
        Set<String> csvFiles = readCSVFile(csvFilePath);

        Set<String> notInCsv = new HashSet<>(folderFiles);
        notInCsv.removeAll(csvFiles);

        Set<String> notInFolder = new HashSet<>(csvFiles);
        notInFolder.removeAll(folderFiles);

        writeCsv(outputCsvPath, notInCsv, notInFolder);
    }

    /**
     * 获取文件夹内文件的文件名（不包括后缀），并排除指定类型的文件。
     *
     * @param folderPath 文件夹路径
     * @return 文件名集合
     */
    private static Set<String> listFilesInFolder(String folderPath) {
        Set<String> fileNames = new HashSet<>();
        File folder = new File(folderPath);

        for (File file : folder.listFiles(file -> file.isFile() && !file.getName().equalsIgnoreCase(".DS_Store"))) {
            // 获取文件名并去除后缀
            fileNames.add(removeFileExtension(file.getName()));
        }

        return fileNames;
    }

    /**
     * 读取CSV文件中的文件名（不包括后缀）。
     *
     * @param csvFilePath CSV文件路径
     * @return 文件名集合
     * @throws IOException 如果发生文件读取错误
     */
    private static Set<String> readCSVFile(String csvFilePath) throws IOException {
        Set<String> fileNames = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            br.lines().map(String::trim).map(FolderCsvComparator::removeFileExtension).forEach(fileNames::add);
        }

        return fileNames;
    }

    /**
     * 去除文件名的后缀。
     *
     * @param fileName 带有后缀的文件名
     * @return 不带后缀的文件名
     */
    private static String removeFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return lastDotIndex != -1 ? fileName.substring(0, lastDotIndex) : fileName;
    }

    /**
     * 将结果写入CSV文件，第一列为不在CSV中但在文件夹内的文件名，
     * 第二列为不在文件夹内但在CSV中的文件名。
     *
     * @param outputPath    输出CSV文件路径
     * @param notInCsv       不在CSV中但在文件夹内的文件名集合
     * @param notInFolder    不在文件夹内但在CSV中的文件名集合
     * @throws IOException  如果发生文件写入错误
     */
    private static void writeCsv(String outputPath, Set<String> notInCsv, Set<String> notInFolder) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("Not_In_CSV, Not_In_Folder");

            int maxSize = Math.max(notInCsv.size(), notInFolder.size());
            for (int i = 0; i < maxSize; i++) {
                String csvEntry = i < notInCsv.size() ? notInCsv.toArray(new String[0])[i] : "";
                String folderEntry = i < notInFolder.size() ? notInFolder.toArray(new String[0])[i] : "";
                writer.println(csvEntry + "," + folderEntry);
            }
        }
    }
}