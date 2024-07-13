package com.github.beibeikun.imagewarehousemanagementtool.util.CompleteProcess;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.CompressFileList;
import com.github.beibeikun.imagewarehousemanagementtool.util.CompressOperations.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateTemporaryDestinationFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteDirectory;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * 图片压缩上传类，封装了完整的图片压缩上传过程。
 */
public class CompressImgToZipAndUpload
{
    /**
     * 完成图片压缩上传的整个过程。
     *
     * @param sourceFolder       源文件夹路径
     * @param destinationFolder  目标文件夹路径
     * @param mode               1.相机图 其他.手机图
     * @param coverageDeterminer 判断是否覆盖 true为覆盖，false不覆盖
     * @throws IOException 如果文件操作失败
     */
    public static void compressImgToZipAndUpload(String sourceFolder, String destinationFolder, int mode, int imgsize, boolean coverageDeterminer, boolean deleteQualifier) throws IOException, ImageProcessingException, MetadataException
    {
        SystemPrintOut.systemPrintOut("Start to upload", 3, 0);
        SystemChecker system = new SystemChecker();

        //创建一个临时文件夹来储存压缩包
        String temporaryDestinationFolder = CreateTemporaryDestinationFolder.createTemporaryFolder(sourceFolder, "TemporaryCompression1");
        //创建一个临时文件夹来储存压缩图
        String temporaryCompressedImgFolder = CreateTemporaryDestinationFolder.createTemporaryFolder(sourceFolder, "TemporaryCompression2");
        //缩略图路径
        String thumbnailFolder = destinationFolder + system.identifySystem_String() + "thumbnail";
        //mode为1，检查缩略图路径
        if (mode == 1)
        {

            // 创建缩略图路径文件夹对象
            File thumbFolder = new File(thumbnailFolder);

            // 如果缩略图路径文件夹不存在，则创建
            if (! thumbFolder.exists())
            {
                File directory = new File(thumbnailFolder);
                directory.mkdirs();
            }
        }
        //获取源文件夹内所有文件名,处理文件名数组，去除文件后缀名、去除 "(x)" 后缀并删除重复项，只保留一个
        String[] FileNames = FileNameProcessor.processFileNames(FileLister.getFileNames(sourceFolder));
        Arrays.sort(FileNames);
        for (int i = 0; i < FileNames.length; i++)
        {
            if (coverageDeterminer)
            {
                thumbnailAndCompress(mode, sourceFolder, FileNames, i, thumbnailFolder, imgsize, temporaryCompressedImgFolder, temporaryDestinationFolder);
            }
            else
            {
                //检测仓库中是否存在，如果存在则跳过
                int position = FileNames[i].indexOf("-");
                String databasePath = destinationFolder + system.identifySystem_String() + FileNames[i].substring(0, position) + system.identifySystem_String() + FileNames[i] + ".zip";
                if (! FileSearch.isFileExists(databasePath))
                {
                    thumbnailAndCompress(mode, sourceFolder, FileNames, i, thumbnailFolder, imgsize, temporaryCompressedImgFolder, temporaryDestinationFolder);
                }
                else
                {
                    int x = i + 1;
                    SystemPrintOut.systemPrintOut("The file has been backed up:" + FileNames[i] + "    (" + x + "/" + FileNames.length + ")", 2, 0);
                }
            }
        }
        //把压缩包从临时文件夹移动到目标文件夹并按前缀分类
        FileCopyAndDelete.copyFilesAndOrganize(temporaryDestinationFolder, destinationFolder, 6);
        //删除临时文件夹
        DeleteDirectory.deleteDirectory(new File(temporaryDestinationFolder));
        DeleteDirectory.deleteDirectory(new File(temporaryCompressedImgFolder));
        if (deleteQualifier)
        {
            DeleteDirectory.deleteDirectory(new File(sourceFolder));
        }
    }

    private static void thumbnailAndCompress(int mode, String sourceFolder, String[] FileNames, int i, String thumbnailFolder, int imgsize, String temporaryCompressedImgFolder, String temporaryDestinationFolder) throws IOException
    {
        SystemChecker system = new SystemChecker();
        if (mode == 1)
        {
            FileCopyAndDelete.copyFile(sourceFolder + system.identifySystem_String() + FileNames[i] + ".JPG", thumbnailFolder);
            ImageCompression.imageCompression(thumbnailFolder + system.identifySystem_String() + FileNames[i] + ".JPG", 2500);
            SystemPrintOut.systemPrintOut("Thumbnail upload:" + FileNames[i], 1, 0);
        }
        //获取同一前缀的文件列表
        List<File> readytocompress = FileSearch.searchFiles(sourceFolder, FileNames[i]);
        //根据 imgsize 判断图片是否需要压缩，不为0即需要压缩
        if (imgsize != 0)
        {
            //复制需要压缩尺寸的图像到临时文件夹，并更新list为移动后的路径
            readytocompress = FileCopyAndDelete.moveFilesWithList(readytocompress, temporaryCompressedImgFolder);
            //压缩图片
            ImageCompression.compressImgWithFileListUseMultithreading(readytocompress, imgsize);
        }
        //x为已完成的数量
        int x = i + 1;
        //压缩同一前缀的文件
        CompressFileList.compressFiles(readytocompress, temporaryDestinationFolder + system.identifySystem_String() + FileNames[i] + ".zip");
        SystemPrintOut.systemPrintOut("Compressed:" + FileNames[i] + ".zip" + "    (" + x + "/" + FileNames.length + ")", 1, 0);

    }
}
