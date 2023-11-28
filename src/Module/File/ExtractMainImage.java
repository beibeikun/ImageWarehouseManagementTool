package Module.File;

import Module.Others.IdentifySystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Module.File.FileCompression.getPrefix;
import static Module.File.FileOperations.copyFile;

public class ExtractMainImage {

    /**
     * 从源文件夹中提取主图像到目标文件夹。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     */
    public static void extractMainImage(String sourceFolder, String destinationFolder) {
        IdentifySystem system = new IdentifySystem();
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
            copyFile(sourceFilePath, destinationFolder);
        }
    }
}
