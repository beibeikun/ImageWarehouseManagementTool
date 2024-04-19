package Module.Test;// 导入必要的包

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static Module.FileOperations.CreateFolder.createFolderWithTime;
import static Module.Others.GetTimeConsuming.getTimeConsuming;

/**
 * 复制文件夹的类
 */
public class CopyFolderUseMultithreading
{

    /**
     * 主方法
     *
     * @param args 命令行参数
     * @throws IOException IO异常
     */
    public static void main(String[] args) throws IOException
    {
        Instant instant1 = Instant.now();
        // 源文件夹路径
        String sourcePath = "/Users/bbk/Downloads/test";
        // 目标文件夹路径
        String targetPath = createFolderWithTime("/Users/bbk/photographs/IWMT_OUT");
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 创建队列存储待复制的文件或文件夹
        LinkedBlockingQueue<Path> queue = new LinkedBlockingQueue<>();

        // 遍历源文件夹中的所有文件和文件夹
        Files.walk(Paths.get(sourcePath)).forEach(path ->
        {
            // 将每个文件或文件夹添加到队列中
            queue.offer(path);
        });

        // 创建多个线程从队列中获取文件或文件夹进行复制
        for (int i = 0; i < 4; i++)
        {
            executorService.submit(() ->
            {
                while (true)
                {
                    Path path = null;
                    try
                    {
                        // 从队列中获取文件或文件夹
                        path = queue.take();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        break;
                    }

                    // 判断是文件还是文件夹
                    if (Files.isRegularFile(path))
                    {
                        // 复制文件
                        try
                        {
                            Files.copy(path, Paths.get(targetPath, path.getFileName().toString()));
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (Files.isDirectory(path))
                    {
                        // 创建目标文件夹
                        try
                        {
                            Files.createDirectories(Paths.get(targetPath, path.getFileName().toString()));
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

        // 等待所有任务完成
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
        Instant instant2 = Instant.now();
        System.out.println(getTimeConsuming(instant1, instant2));
        System.out.println("复制完成！");
    }
}
