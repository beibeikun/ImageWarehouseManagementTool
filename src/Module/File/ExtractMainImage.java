package Module.File;

import Module.Others.IdentifySystem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Module.File.FileCompression.getPrefix;
import static Module.File.FileOperations.copyFile;

public class ExtractMainImage {
    public static void extractMainImage(String sourceFolder, String destinationFolder)
    {
        IdentifySystem system = new IdentifySystem();
        File folder = new File(sourceFolder);
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
        for (int i = 0; i < prefixArray.length; i++) {
            copyFile(sourceFolder + system.identifySystem_String() + prefixArray[i] + ".JPG",destinationFolder);
        }
    }
}
