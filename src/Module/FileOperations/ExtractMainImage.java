package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.FileOperations.FileCompression.getPrefix;
import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class ExtractMainImage {

    /**
     * 从源文件夹中提取主图像到目标文件夹。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     */
    public static void extractMainImage(String sourceFolder, String destinationFolder) {
        systemPrintOut("Start to take main img from source path",3,0);
        SystemChecker system = new SystemChecker();
        File folder = new File(sourceFolder);

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Source folder does not exist or is not a directory.");
            return;
        }

        File[] files = folder.listFiles();
        Map<String, List<File>> prefixToFileListMap = new HashMap<>();

        // 遍历文件列表，根据前缀将文件分类
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String prefix = getPrefix(fileName);

                if (prefix != null) {
                    prefixToFileListMap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(file);
                }
            }
        }

        String[] prefixArray = prefixToFileListMap.keySet().toArray(new String[0]);
        for (String prefix : prefixArray) {
            String sourceFilePath = Paths.get(sourceFolder, system.identifySystem_String() + prefix + ".JPG").toString();
            if (!isSystemOrHiddenFile(new File(sourceFilePath)))
            {
                copyFile(sourceFilePath, destinationFolder);
                systemPrintOut("Copy:"+prefix,1,0);
            }
        }
        systemPrintOut(null,0,0);
    }
}
