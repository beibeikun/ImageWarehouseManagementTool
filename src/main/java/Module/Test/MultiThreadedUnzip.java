package Module.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MultiThreadedUnzip
{

    public static void main(String[] args)
    {
        String folderPath = "D:\\2222\\test";
        unzipAllFiles(folderPath);
    }

    // 解压文件夹中的所有压缩包
    public static void unzipAllFiles(String folderPath)
    {
        Instant instant1 = Instant.now();
        // 获取文件夹对象
        File folder = new File(folderPath);
        // 获取文件夹中的所有文件
        File[] files = folder.listFiles();

        if (files != null)
        {
            // 创建固定数量的线程池，数量为文件夹中的文件数量
            //ExecutorService executorService = Executors.newFixedThreadPool(files.length);

            for (File file : files)
            {
                // 如果是文件且是以".zip"结尾的压缩包
                if (file.isFile() && file.getName().toLowerCase().endsWith(".zip"))
                {
                    try
                    {
                        // 调用解压方法
                        unzip(file.getAbsolutePath(), folderPath);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    /*
                    executorService.submit(() -> {
                        try {
                            // 调用解压方法
                            unzip(file.getAbsolutePath(), folderPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                     */
                }
            }

            // 关闭线程池
            //executorService.shutdown();
            Instant instant2 = Instant.now();
            Duration duration = Duration.between(instant1, instant2);
            System.out.println(duration.getSeconds());
        }
    }

    // 解压单个压缩包
    private static void unzip(String zipFilePath, String destFolder) throws IOException
    {
        // 使用try-with-resources确保流资源会被正确关闭
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath)))
        {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null)
            {
                String entryName = entry.getName();
                String outputPath = destFolder + File.separator + entryName;

                // 如果是目录
                if (entry.isDirectory())
                {
                    // 创建目录
                    new File(outputPath).mkdirs();
                }
                else
                {
                    // 如果是文件，创建输出流写入文件内容
                    try (FileOutputStream outputStream = new FileOutputStream(outputPath))
                    {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0)
                        {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }
}
