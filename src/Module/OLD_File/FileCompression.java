package Module.OLD_File;
/*------------------------------------------------------------------
已重构，该代码当前停用
------------------------------------------------------------------*/
import Module.CheckOperations.SystemChecker;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.DataOperations.GetPrefix.getPrefix;
import static Module.FileOperations.DeleteDirectory.deleteDirectory;
import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.FileOperations.FileCopyAndDelete.copyFiles;
import static Module.Others.SystemPrintOut.systemPrintOut;
import static Module.CompressOperations.CompressFileList.compressFiles;
import static Module.CompressOperations.ImageCompression.imageCompression;
//TODO:重构完成，在TEST中，还需要测试
/**
 * 文件压缩功能实现类。
 * 根据指定的源文件夹路径和目标文件夹路径，压缩符合条件的文件。
 * 遍历源文件夹中的文件列表，根据文件名中的前缀将文件分类。
 * 使用 Map 数据结构来跟踪每个前缀与对应文件列表的关系。
 * 压缩每个前缀对应的文件列表，将其保存为一个压缩文件。
 * 返回一个包含所有前缀的字符串数组。
 */
public class FileCompression {
    /**
     * 压缩符合条件的文件。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     * @param mode              1上传压缩图 2不上传压缩图
     * @return 包含所有前缀的字符串数组
     */
    public static String[] compressFilesByPrefix(String sourceFolder, String destinationFolder, int mode) throws IOException, ImageProcessingException, MetadataException {
        systemPrintOut("Start compression upload", 3, 0);
        SystemChecker system = new SystemChecker();

        //缩略图路径
        String temporaryDestinationFolder = sourceFolder + "TemporaryCompression";

        File folder = new File(sourceFolder);
        File[] files = folder.listFiles();
        File file1 = new File(temporaryDestinationFolder);
        if (!file1.exists()) {
            File directory = new File(temporaryDestinationFolder);
            boolean created = directory.mkdirs();
        }

        // 使用Map来跟踪前缀和文件列表的关系
        Map<String, List<File>> prefixToFileListMap = new HashMap<>();

        // 遍历文件列表，根据前缀将文件分类
        for (File file : files) {
            if (file.isFile()) {
                //隐藏文件判断
                if (!isSystemOrHiddenFile(file)) {
                    String fileName = file.getName();
                    String prefix = getPrefix(fileName);

                    if (prefix != null) {
                        prefixToFileListMap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(file);
                    }
                }
            }
        }

        String[] prefixArray = prefixToFileListMap.keySet().toArray(new String[0]);

        // 压缩每个前缀对应的文件列表
        for (Map.Entry<String, List<File>> entry : prefixToFileListMap.entrySet()) {
            String prefix = entry.getKey();
            List<File> fileList = entry.getValue();

            String compressedFile = temporaryDestinationFolder + system.identifySystem_String() + prefix + ".zip";
            systemPrintOut("Zipped:" + compressedFile, 1, 0);
            if (mode == 1) {
                copyFile(sourceFolder + system.identifySystem_String() + prefix + ".JPG", destinationFolder + system.identifySystem_String() + "thumbnail");
                imageCompression(destinationFolder + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + prefix + ".JPG", 2500);
                systemPrintOut("Thumbnail upload:" + prefix, 1, 0);
            }
            compressFiles(fileList, compressedFile);
        }
        copyFiles(temporaryDestinationFolder, destinationFolder, 6);
        File directory = new File(temporaryDestinationFolder);
        boolean deleted = deleteDirectory(directory); //删除临时文件夹

        return prefixArray;
    }
}
