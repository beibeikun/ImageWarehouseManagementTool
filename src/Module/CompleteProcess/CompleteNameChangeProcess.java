package Module.CompleteProcess;

import Module.DataOperations.ArrayExtractor;
import Module.DataOperations.ReadCsvFile;
import Module.FileOperations.DeleteFilesWithKeyword;
import Module.FileOperations.FileExtractor;
import Module.FileOperations.FolderCopy;
import Module.FileOperations.RenameFiles;
import Module.CompressOperations.CompressImagesInBatches;
import Module.CompressOperations.UnzipAllZipsWithDelete;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.IOException;
import java.util.*;

import static Module.CompleteProcess.ChangeAllSuffix.changeAllSuffix;
import static Module.FileOperations.FilePrefixMove.filePrefixMove;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件名更改处理类，封装了完整的文件名更改过程。
 */
public class CompleteNameChangeProcess {

    /**
     * 完成文件名更改的整个过程。
     *
     * @param nasFolderPath           NAS 文件夹路径
     * @param sourceFolderPath        源文件夹路径
     * @param targetFolderPath        目标文件夹路径
     * @param CSVPath                 CSV 文件路径
     * @param checkBoxAddFromDatabase 复选框标志，1 表示添加数据库中的文件，0 表示不添加
     * @param imgsize                 压缩图片尺寸，为0则不压缩
     * @param suffix                  后缀判定
     * @throws IOException 如果文件操作失败
     */
    public void completeNameChangeProcess(String nasFolderPath, String sourceFolderPath, String targetFolderPath, String CSVPath, int checkBoxAddFromDatabase, int imgsize, boolean terminal, boolean prefixmove, int suffix) throws IOException, ImageProcessingException, MetadataException {
        // 从 CSV 中提取要提取的文件名数组
        String[] fileNamesToExtract = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(CSVPath), 0);
        List<String> readyToCopyNameList = Arrays.asList(fileNamesToExtract);
        systemPrintOut("Start to rename", 3, 0);
        //储存数据库中存在的文件名
        List<String> databaseNamelist = new ArrayList<>();
        // 根据 checkBoxAddFromDatabase 标志判断是否添加数据库中的文件
        if (checkBoxAddFromDatabase == 1) {
            // 提取压缩包
            databaseNamelist = FileExtractor.extractFiles(nasFolderPath, targetFolderPath, fileNamesToExtract);
            // 解压压缩包并删除
            UnzipAllZipsWithDelete.unzipAllZipsInFolder(targetFolderPath);
            systemPrintOut(null, 0, 0);
        }

        Set<String> setA = new HashSet<>(readyToCopyNameList);
        Set<String> setB = new HashSet<>(databaseNamelist);
        // 将 setB 中的元素从 setA 中删除
        setA.removeAll(setB);
        // 将 setA 转换为 List<String>
        readyToCopyNameList = new ArrayList<>(setA);

        // 从源文件夹拷贝文件到目标文件夹
        FolderCopy.copyFolderWithList(sourceFolderPath, targetFolderPath,readyToCopyNameList);
        systemPrintOut(null, 0, 0);
        // 重命名文件
        RenameFiles.renameFiles(CSVPath, targetFolderPath, 0, 0);
        systemPrintOut(null, 0, 0);
        // 删除目标文件夹中包含关键词的文件
        //TODO:现在这个方法用关键字符去判断不够合理，需要优化
        DeleteFilesWithKeyword.deleteFilesWithKeyword(targetFolderPath, "JB");
        //根据 imgsize 判断图片是否需要压缩
        if (imgsize != 0) {
            systemPrintOut(null, 0, 0);
            CompressImagesInBatches.compressImagesInBatches(targetFolderPath, imgsize, terminal);
        }
        systemPrintOut(null, 0, 0);
        //根据 suffix 判断是否需要生成其他后缀
        if (suffix != 0) {
            changeAllSuffix(targetFolderPath,"",1);
        }
        systemPrintOut(null, 0, 0);
        //根据prefixmove判断是否需要分类
        if (prefixmove) {
            filePrefixMove(targetFolderPath, " (");
            systemPrintOut(null, 0, 0);
        }
    }
}