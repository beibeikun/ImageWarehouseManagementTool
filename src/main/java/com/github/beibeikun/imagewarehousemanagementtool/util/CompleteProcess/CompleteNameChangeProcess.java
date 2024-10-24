package com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess;

import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.UnzipAllZipsWithDelete;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ArrayExtractor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ReadCsvFile;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileExtractor;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FolderCopy;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.RenameFiles;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.ListExtractor;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.*;
import com.github.beibeikun.imagewarehousemanagementtool.util.Test.Path;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.OrganizeFiles.moveNumberForward;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.OrganizeFiles.organizeFileNumbers;
import static com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut.systemPrintOut;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;

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
    public void completeNameChangeProcess(String nasFolderPath, String targetFolderPath, String CSVPath, int checkBoxAddFromDatabase, int checkSort,int checkWhichDatabase, int imgsize, boolean terminal, boolean prefixmove, int suffix) throws IOException, ImageProcessingException, MetadataException
    {
        String sourceFolderPath = Path.getSourcePath();
        if (!checkFolder(sourceFolderPath,targetFolderPath,true,CSVPath,true,"csv",true))
        {
            systemPrintOut(printOutMessage.INVALID_PATH_STOP_TASK,2,0);
            systemPrintOut(printOutMessage.NULL,0,0);
            return;
        }
        // 从 CSV 中提取要提取的文件名数组
        String[] fileNamesToExtract = ArrayExtractor.extractRow(ReadCsvFile.csvToArray(CSVPath), 0);
        // 将文件名数组转化为列表
        List<String> readyToCopyNameList = Arrays.asList(fileNamesToExtract);
        // 用当前时间新建一个文件夹来存储
        targetFolderPath = CreateFolder.createFolderWithTime(targetFolderPath);
        // 输出“开始重命名”
        systemPrintOut(printOutMessage.START_TO_RENAME, 3, 0);
        // 用于储存数据库中存在的文件名
        List<String> databaseNamelist = new ArrayList<>();
        Collections.sort(databaseNamelist);
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
        readyToCopyNameList = ListExtractor.removeElementFromList(readyToCopyNameList, databaseNamelist);
        // 从源文件夹拷贝文件到目标文件夹
        FolderCopy.copyFolderWithList(sourceFolderPath, targetFolderPath, readyToCopyNameList);
        systemPrintOut(null, 0, 0);
        //根据 checkSort 判断图片是否需要排序，为1即需要排序
        if (checkSort == 1)
        {
            organizeFileNumbers(printOutMessage.NULL,targetFolderPath,false);
            if (checkWhichDatabase == 0)
            {
                moveNumberForward(printOutMessage.NULL,targetFolderPath,false);
            }
        }
        // 重命名文件
        RenameFiles.renameFiles(CSVPath, targetFolderPath, 0, 0);
        systemPrintOut(null, 0, 0);
        // 删除目标文件夹中包含关键词的文件
        //DeleteFilesWithKeyword.deleteFilesWithKeyword(targetFolderPath, "-");
        //根据 imgsize 判断图片是否需要压缩，不为0即需要压缩
        if (imgsize != 0)
        {
            systemPrintOut(null, 0, 0);
            //获取需要要压缩尺寸的图像列表
            List<File> files = FileLister.getFileNamesInList(targetFolderPath);
            //压缩图片
            ImageCompression.compressImgWithFileListUseMultithreading(files, imgsize);
        }
        systemPrintOut(null, 0, 0);
        //根据 suffix 判断是否需要生成其他后缀
        if (suffix != 0)
        {
            ChangeAllSuffix.changeAllSuffix(targetFolderPath, printOutMessage.NULL, 1);
        }
        systemPrintOut(null, 0, 0);
        //根据prefixmove判断是否需要分类
        if (prefixmove)
        {
            FilePrefixMove.filePrefixMove(targetFolderPath, " (");
            systemPrintOut(null, 0, 0);
        }
    }
}
