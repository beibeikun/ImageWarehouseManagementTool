package Module.CompleteProcess;

import Module.CheckOperations.SystemChecker;
import Module.CompressOperations.UnzipAllZipsWithDelete;
import Module.DataOperations.ArrayExtractor;
import Module.DataOperations.ReadCsvFile;
import Module.FileOperations.DeleteFilesWithKeyword;
import Module.FileOperations.FileExtractor;
import Module.FileOperations.FolderCopy;
import Module.FileOperations.RenameFiles;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Module.CompleteProcess.ChangeAllSuffix.changeAllSuffix;
import static Module.CompressOperations.ImageCompression.compressImgWithFileListUseMultithreading;
import static Module.DataOperations.FileLister.getFileNamesInList;
import static Module.DataOperations.ListExtractor.removeElementFromList;
import static Module.FileOperations.CreateFolder.createFolderWithTime;
import static Module.FileOperations.FilePrefixMove.filePrefixMove;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 文件名更改处理类，封装了完整的文件名更改过程。
 */
public class CompleteNameChangeProcess
{

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
    public void completeNameChangeProcess(String nasFolderPath, String sourceFolderPath, String targetFolderPath, String CSVPath, int checkBoxAddFromDatabase, int imgsize, boolean terminal, boolean prefixmove, int suffix) throws IOException, ImageProcessingException, MetadataException
    {
        SystemChecker system = new SystemChecker();
        // 从 CSV 中提取要提取的文件名数组
        String[] fileNamesToExtract = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(CSVPath), 0);
        // 将文件名数组转化为列表
        List<String> readyToCopyNameList = Arrays.asList(fileNamesToExtract);
        // 用当前时间新建一个文件夹来存储
        targetFolderPath = createFolderWithTime(targetFolderPath);
        // 输出“开始重命名”
        systemPrintOut("Start to rename", 3, 0);
        // 用于储存数据库中存在的文件名
        List<String> databaseNamelist = new ArrayList<>();
        // 根据 checkBoxAddFromDatabase 标志判断是否添加数据库中的文件
        if (checkBoxAddFromDatabase == 1)
        {
            // 提取压缩包并获取数据库中存在的文件名
            databaseNamelist = FileExtractor.extractFiles(nasFolderPath, targetFolderPath, fileNamesToExtract);
            systemPrintOut(null, 0, 0);
            // 解压压缩包并删除
            UnzipAllZipsWithDelete.unzipAllZipsInFolder(targetFolderPath);
            systemPrintOut(null, 0, 0);
        }
        //从列表中去除已经在文件仓库中找到的文件
        readyToCopyNameList = removeElementFromList(readyToCopyNameList, databaseNamelist);
        // 从源文件夹拷贝文件到目标文件夹
        FolderCopy.copyFolderWithList(sourceFolderPath, targetFolderPath, readyToCopyNameList);
        systemPrintOut(null, 0, 0);
        // 重命名文件
        RenameFiles.renameFiles(CSVPath, targetFolderPath, 0, 0);
        systemPrintOut(null, 0, 0);
        // 删除目标文件夹中包含关键词的文件
        DeleteFilesWithKeyword.deleteFilesWithKeyword(targetFolderPath, "JB");
        //根据 imgsize 判断图片是否需要压缩，不为0即需要压缩
        if (imgsize != 0)
        {
            systemPrintOut(null, 0, 0);
            //获取需要要压缩尺寸的图像列表
            List<File> files = getFileNamesInList(targetFolderPath);
            //压缩图片
            compressImgWithFileListUseMultithreading(files, imgsize);
            //CompressImagesInBatches.compressImagesInBatches(targetFolderPath, imgsize, terminal);
        }
        systemPrintOut(null, 0, 0);
        //根据 suffix 判断是否需要生成其他后缀
        if (suffix != 0)
        {
            changeAllSuffix(targetFolderPath, "", 1);
        }
        systemPrintOut(null, 0, 0);
        //根据prefixmove判断是否需要分类
        if (prefixmove)
        {
            filePrefixMove(targetFolderPath, " (");
            systemPrintOut(null, 0, 0);
        }
    }
}
