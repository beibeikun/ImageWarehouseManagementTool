package Module.CompleteProcess;

import Module.CheckOperations.SystemChecker;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static Module.CompressOperations.CompressFileList.compressFiles;
import static Module.CompressOperations.ImageCompression.compressImgWithFileListUseMultithreading;
import static Module.CompressOperations.ImageCompression.imageCompression;
import static Module.DataOperations.FileLister.getFileNames;
import static Module.DataOperations.FileNameProcessor.processFileNames;
import static Module.DataOperations.FileSearch.isFileExists;
import static Module.DataOperations.FileSearch.searchFiles;
import static Module.FileOperations.CreateTemporaryDestinationFolder.createTemporaryFolder;
import static Module.FileOperations.DeleteDirectory.deleteDirectory;
import static Module.FileOperations.FileCopyAndDelete.*;
import static Module.Others.SystemPrintOut.systemPrintOut;


/**
 * 图片压缩上传类，封装了完整的图片压缩上传过程。
 */
public class CompressImgToZipAndUpload
{
    /**
     * 完成图片压缩上传的整个过程。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     * @param mode              1.相机图 其他.手机图
     * @throws IOException 如果文件操作失败
     */
    public static void compressImgToZipAndUpload(String sourceFolder, String destinationFolder, int mode, int imgsize) throws IOException, ImageProcessingException, MetadataException
    {
        systemPrintOut("Start to upload", 3, 0);
        SystemChecker system = new SystemChecker();

        //创建一个临时文件夹来储存压缩包
        String temporaryDestinationFolder = createTemporaryFolder(sourceFolder, "TemporaryCompression1");
        //创建一个临时文件夹来储存压缩图
        String temporaryCompressedImgFolder = createTemporaryFolder(sourceFolder, "TemporaryCompression2");
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
        String[] FileNames = processFileNames(getFileNames(sourceFolder));
        for (int i = 0; i < FileNames.length; i++)
        {
            //检测仓库中是否存在，如果存在则跳过
            String databasePath = destinationFolder + system.identifySystem_String() + FileNames[i].substring(0, 6) + system.identifySystem_String() + FileNames[i] +  ".zip";
            if (!isFileExists(databasePath))
            {

                //mode为1，上传缩略图
                if (mode == 1)
                {
                    copyFile(sourceFolder + system.identifySystem_String() + FileNames[i] + ".JPG", thumbnailFolder);
                    imageCompression(thumbnailFolder + system.identifySystem_String() + FileNames[i] + ".JPG", 2500);
                    systemPrintOut("Thumbnail upload:" + FileNames[i], 1, 0);
                }
                //获取同一前缀的文件列表
                List<File> readytocompress = searchFiles(sourceFolder, FileNames[i]);
                //根据 imgsize 判断图片是否需要压缩，不为0即需要压缩
                if (imgsize != 0)
                {
                    //复制需要压缩尺寸的图像到临时文件夹，并更新list为移动后的路径
                    readytocompress = moveFilesWithList(readytocompress, temporaryCompressedImgFolder);
                    //压缩图片
                    compressImgWithFileListUseMultithreading(readytocompress, imgsize);
                }
                //x为已完成的数量
                int x = i + 1;
                //压缩同一前缀的文件
                compressFiles(readytocompress, temporaryDestinationFolder + system.identifySystem_String() + FileNames[i] + ".zip");
                systemPrintOut("Compressed:" + FileNames[i] + ".zip" + "    (" + x + "/" + FileNames.length + ")", 1, 0);


            }
            else
            {
                int x = i + 1;
                systemPrintOut("The file has been backed up:" + FileNames[i]+ "    (" + x + "/" + FileNames.length + ")",2,0);
            }
        }
        //把压缩包从临时文件夹移动到目标文件夹并按前缀分类
        copyFiles(temporaryDestinationFolder, destinationFolder, 6);
        //删除临时文件夹
        deleteDirectory(new File(temporaryDestinationFolder));
        deleteDirectory(new File(temporaryCompressedImgFolder));
    }
}
