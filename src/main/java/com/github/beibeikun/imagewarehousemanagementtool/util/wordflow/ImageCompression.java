package com.github.beibeikun.imagewarehousemanagementtool.util.wordflow;

import com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCompression
{
    /**
     * 压缩单张图片。
     *
     * @param imgpath 需要压缩的图片路径
     * @param size    指定的压缩尺寸，为最长边的像素
     * @throws IOException 如果文件操作失败
     */
    public static void imageCompression(String imgpath, int size) throws IOException
    {
        Thumbnails.of(imgpath)
                .size(size, size)
                .outputQuality(0.9f)
                .toFile(imgpath);
    }

    /**
     * 调用多线程，压缩list中的所有图片。
     *
     * @param files   需要压缩的图片列表
     * @param imgsize 指定的压缩尺寸，为最长边的像素
     * @throws IOException 如果文件操作失败
     */
    public static void compressImgWithFileListUseMultithreading(List<File> files, int imgsize)
    {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 将图片文件分割成多个子任务
        List<List<File>> subtasks = new ArrayList<>();
        for (int i = 0; i < files.size(); i += 4)
        {
            subtasks.add(files.subList(i, Math.min(i + 4, files.size())));
        }
        // 创建多个线程来处理子任务
        for (List<File> subtask : subtasks)
        {
            executorService.submit(() ->
            {
                for (File file : subtask)
                {
                    try
                    {
                        imageCompression(file.toString(), imgsize);
                        SystemPrintOut.systemPrintOut("Compressed: " + file, 1, 0);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                    // 压缩图片
                    // ...
                }
            });
        }
        // 等待所有线程完成
        executorService.shutdown();
        while (! executorService.isTerminated())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}