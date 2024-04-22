package Module.DataOperations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class GetLatestSubfolderPath
{

    public static String getLatestSubfolder(String folderPath)
    {
        File folder = new File(folderPath);
        File[] subfolders = folder.listFiles(File::isDirectory);
        if (subfolders == null || subfolders.length == 0)
        {
            return null; // 没有子文件夹，直接返回null
        }

        File latestFolder = null;
        long latestTime = Long.MIN_VALUE;

        for (File subfolder : subfolders)
        {
            try
            {
                Path path = subfolder.toPath();
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                long creationTime = attrs.creationTime().toMillis();

                if (creationTime > latestTime)
                {
                    latestTime = creationTime;
                    latestFolder = subfolder;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                // 出现异常时，可能是读取文件属性失败，这里简单处理，实际使用时可以根据需要进行更复杂的异常处理
            }
        }

        return Optional.ofNullable(latestFolder).map(File::getAbsolutePath).orElse(null);
    }

    public static void main(String[] args)
    {
        String folderPath = "/Users/bbk/photographs/test2"; // 替换为实际的文件夹路径
        String latestSubfolder = getLatestSubfolder(folderPath);
        if (latestSubfolder != null)
        {
            System.out.println("创建时间最晚的子文件夹是: " + latestSubfolder);
        }
        else
        {
            System.out.println("该文件夹内没有子文件夹");
        }
    }
}
