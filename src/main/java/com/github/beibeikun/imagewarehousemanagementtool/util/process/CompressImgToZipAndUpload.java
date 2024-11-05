package com.github.beibeikun.imagewarehousemanagementtool.util.process;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.github.beibeikun.imagewarehousemanagementtool.constant.printOutMessage;
import com.github.beibeikun.imagewarehousemanagementtool.constant.regex;
import com.github.beibeikun.imagewarehousemanagementtool.util.wordflow.CompressFileList;
import com.github.beibeikun.imagewarehousemanagementtool.util.wordflow.ImageCompression;
import com.github.beibeikun.imagewarehousemanagementtool.util.file.FileLister;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileNameProcessor;
import com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations.FileSearch;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.CreateFolder;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteDirectory;
import com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete;
import com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.FolderChecker.checkFolder;
import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.DeleteDirectory.deleteDirectory;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.copyFilesWithList;
import static com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations.FileCopyAndDelete.moveFilesWithList;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;


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
    public static void compressImgToZipAndUpload(String sourceFolder, String destinationFolder, int mode, int imgSize, boolean coverageDeterminer, boolean deleteQualifier) throws IOException
    {
        if (!checkFolder(sourceFolder,true, regex.REGEX_STANDARD_FILE_NAME,destinationFolder,false,printOutMessage.NULL,false,printOutMessage.NULL))
        {
            systemPrintOut(printOutMessage.INVALID_PATH_STOP_TASK,2,0);
            systemPrintOut(printOutMessage.NULL,0,0);
            return;
        }
        // 打印系统消息，表明开始上传
        SystemPrintOut.systemPrintOut("Start to upload", 3, 0);

        // 创建记录失败的文件和原因的集合
        List<String> failedFiles = new ArrayList<>();

        // 创建一个临时文件夹用于存储压缩包
        String temporaryDestinationFolder = CreateFolder.createTemporaryFolder(sourceFolder, "TemporaryCompression1");

        // 创建另一个临时文件夹用于存储压缩后的图像
        String temporaryCompressedImgFolder = CreateFolder.createTemporaryFolder(sourceFolder, "TemporaryCompression2");

        // 创建一个文件夹用于转移已备份的图像内容
        File backedUpImgDirectory = new File(sourceFolder + "backedUp");
        String backedUpImgFolder = sourceFolder + "backedUp";

        // 如果备份文件夹不存在，则创建一个新的备份文件夹
        if (!backedUpImgDirectory.exists()) {
            backedUpImgFolder = CreateFolder.createTemporaryFolder(sourceFolder, "backedUp");
        }

        // 确定缩略图路径
        String thumbnailFolder = destinationFolder + identifySystem_String() + "thumbnail";

        // 如果 mode 为 1，表示需要创建缩略图路径
        if (mode == 1) {
            // 创建缩略图路径的文件夹对象
            File thumbFolder = new File(thumbnailFolder);

            // 如果缩略图路径文件夹不存在，则创建它
            if (!thumbFolder.exists()) {
                File thumbnailDirectory = new File(thumbnailFolder);
                thumbnailDirectory.mkdirs();
            }
        }

        // 获取源文件夹中所有文件的文件名，并对其进行处理：
        // 去除文件后缀名、删除不规范的后缀 "(x)"，以及删除重复的文件名
        String[] FileNames = FileNameProcessor.processFileNames(FileLister.getFileNamesInString(sourceFolder));

        // 筛选出符合规范的文件名（包含 "-" 符号的文件名）
        FileNames = Arrays.stream(FileNames)
                .filter(s -> s.contains("-"))
                .toArray(String[]::new);

        // 对文件名进行排序
        Arrays.sort(FileNames);

        // 使用线程池来执行压缩任务
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 遍历文件名数组，处理每个文件
        for (int i = 0; i < FileNames.length; i++) {
            boolean fileCorrectDetermination = true;

            // 检查文件是否已存在于目标文件夹中，如果已存在且不需要覆盖则跳过
            int position = FileNames[i].indexOf("-");
            String databasePath = destinationFolder + identifySystem_String() + FileNames[i].substring(0, position) + identifySystem_String() + FileNames[i] + ".zip";

            // 如果文件不存在或需要覆盖，则执行以下操作
            if (!FileSearch.isFileExists(databasePath) || coverageDeterminer) {
                // 提交压缩和生成缩略图的任务，并设置最大执行时间为 2 分钟
                String[] finalFileNames = FileNames;
                int finalI = i;
                Future<Boolean> future = executor.submit(() ->
                        thumbnailAndCompress(mode, sourceFolder, finalFileNames, finalI, thumbnailFolder, imgSize, temporaryCompressedImgFolder, temporaryDestinationFolder, failedFiles)
                );

                try {
                    // 获取任务结果，如果超过2分钟，则抛出 TimeoutException 异常
                    fileCorrectDetermination = future.get(2, TimeUnit.MINUTES);
                } catch (TimeoutException e) {
                    // 如果任务执行超时，记录文件名和原因并跳过该文件
                    String reason = "Skipping to next file due to timeout.";
                    failedFiles.add("File: " + FileNames[i] + " - Reason: " + reason);
                    SystemPrintOut.systemPrintOut(reason, 2, 0);
                    future.cancel(true); // 取消执行中的任务
                    fileCorrectDetermination = false; // 设置标志位以跳过备份步骤
                } catch (Exception e) {
                    // 如果任务执行过程中出现异常，记录文件名和原因并跳过该文件
                    String reason = "Error: " + e.getMessage();
                    failedFiles.add("File: " + FileNames[i] + " - Reason: " + reason);
                    SystemPrintOut.systemPrintOut(reason, 2, 0);
                    fileCorrectDetermination = false;
                }

                // 如果文件压缩和缩略图生成成功，则将文件从临时文件夹移动到目标文件夹
                if (fileCorrectDetermination) {
                    // 将压缩后的文件从临时文件夹移动到目标文件夹，并按前缀进行分类整理
                    FileCopyAndDelete.copyFilesAndOrganize(temporaryDestinationFolder, destinationFolder);

                    // 清空临时文件夹以便下一个文件使用
                    DeleteDirectory.deleteDirectory(new File(temporaryDestinationFolder));
                    new File(temporaryDestinationFolder).mkdirs();
                }
            } else {
                // 如果文件已存在并且不需要覆盖，记录文件名和原因并跳过该文件
                String reason = "The file has been backed up: " + FileNames[i];
                int x = i+1;
                SystemPrintOut.systemPrintOut(reason+ flattenStatisticsString(x,FileNames.length), 2, 0);
            }

            // 如果文件压缩和缩略图生成成功，则进行备份转移
            if (fileCorrectDetermination) {
                List<File> compressedFiles = FileSearch.searchFiles(sourceFolder, FileNames[i]);

                // 将成功压缩的文件转移到备份文件夹
                moveFilesWithList(compressedFiles, backedUpImgFolder);

                // 打印成功备份的日志信息
                SystemPrintOut.systemPrintOut("Backup files transferred successfully: " + FileNames[i], 1, 0);
            } else {
                SystemPrintOut.systemPrintOut("No such file or directory: " + FileNames[i], 2, 0);
            }

            // 每处理完一个文件，打印系统状态信息
            systemPrintOut(null, 0, 0);
        }

        // 关闭线程池，防止资源泄露
        executor.shutdown();

        // 删除临时文件夹，确保清理干净
        deleteDirectory(new File(temporaryDestinationFolder));
        deleteDirectory(new File(temporaryCompressedImgFolder));

        // 如果 deleteQualifier 为 true，删除源文件夹和备份文件夹
        if (deleteQualifier) {
            deleteDirectory(new File(sourceFolder));
            deleteDirectory(new File(backedUpImgFolder));
        }

        // 在处理完所有文件后，输出所有失败文件及其原因
        SystemPrintOut.systemPrintOut("Failed files and reasons:", 3, 0);
        for (String failure : failedFiles) {
            SystemPrintOut.systemPrintOut(failure, 2, 0);
        }
    }


    private static boolean thumbnailAndCompress(int mode, String sourceFolder, String[] FileNames, int i, String thumbnailFolder, int imgSize, String temporaryCompressedImgFolder, String temporaryDestinationFolder, List<String> failedFiles) throws IOException
    {
        //获取同一前缀的文件列表
        List<File> readyToCompress = FileSearch.searchFiles(sourceFolder, FileNames[i]);
        File file;
        if (mode == 1)//相机 包含主图
        {
            file = new File(sourceFolder + identifySystem_String() + FileNames[i] + ".JPG"); //检查文件是否正确存在
        }
        else//手机 没有主图
        {
            file = new File(sourceFolder + identifySystem_String() + FileNames[i] + " (1).JPG"); //检查文件是否正确存在
        }
        if (file.exists())//存在
        {
            if (readyToCompress.size() > 2)//图片超过两张
            {
                //复制图像到临时文件夹，并更新list为移动后的路径
                readyToCompress = copyFilesWithList(readyToCompress, temporaryCompressedImgFolder, true);
                //根据 imgSize 判断图片是否需要压缩，不为0即需要压缩
                if (imgSize != 0)
                {
                    ImageCompression.compressImgWithFileListUseMultithreading(readyToCompress, imgSize);
                }
            }
            else
            {
                //x为已完成的数量
                int x = i + 1;
                // 如果图片数量不足，记录文件名和原因
                String reason = "Too less pictures: " + FileNames[i];
                failedFiles.add("File: " + FileNames[i] + " - Reason: Too less pictures");
                SystemPrintOut.systemPrintOut(reason+ flattenStatisticsString(x,FileNames.length), 2, 0);
                return false;
            }
        }
        else//不存在
        {
            //x为已完成的数量
            int x = i + 1;
            // 如果文件不存在，记录文件名和原因
            String reason = "Non-existent file: " + FileNames[i];
            failedFiles.add("File: " + FileNames[i] + " - Reason: Non-existent file");
            SystemPrintOut.systemPrintOut(reason+ flattenStatisticsString(x,FileNames.length), 2, 0);
            return false;
        }

        //x为已完成的数量
        int x = i + 1;
        //压缩同一前缀的文件
        CompressFileList.compressFiles(readyToCompress, temporaryDestinationFolder + identifySystem_String() + FileNames[i] + ".zip");
        SystemPrintOut.systemPrintOut("Compressed:" + FileNames[i] + ".zip" + flattenStatisticsString(x,FileNames.length), 1, 0);
        if (mode == 1)
        {
            ImageCompression.imageCompression(sourceFolder + identifySystem_String() + FileNames[i] + ".JPG", 1000);
            FileCopyAndDelete.copyFile(sourceFolder + identifySystem_String() + FileNames[i] + ".JPG", thumbnailFolder);
            SystemPrintOut.systemPrintOut("Thumbnail upload:" + FileNames[i], 1, 0);
        }
        return true;
    }
    private static String flattenStatisticsString(int x, int length) {
        return "    (" + x + "/" + length + ")";
    }
}
