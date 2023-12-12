package Module.DataOperations;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class FileNameProcessor {
    /**
     * 处理文件名数组，去除文件后缀名、去除 "(x)" 后缀并删除重复项，只保留一个
     * @param fileNames 文件名数组
     * @return 处理后的文件名数组
     */
    public static String[] processFileNames(String[] fileNames) {
        String[] processedNames = new String[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            // 去除文件后缀名
            String nameWithoutExtension = fileName.split("\\.")[0];
            // 去除带有 "(x)" 后缀
            String nameWithoutSuffix = nameWithoutExtension.split(" \\(")[0];
            processedNames[i] = nameWithoutSuffix;
        }

        // 删除重复项，只保留一个
        processedNames = new LinkedHashSet<>(Arrays.asList(processedNames)).toArray(new String[0]);

        return processedNames;
    }
}
