package Module.FileOperations;

import java.io.File;

/**
 * 删除目录及其子目录中的所有文件和文件夹。
 */
public class DeleteDirectory
{
    /**
     * 删除指定目录及其子目录中的所有文件和文件夹。
     *
     * @param directory 要删除的目录
     * @return 是否成功删除目录
     */
    public static boolean deleteDirectory(File directory)
    {
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    deleteDirectory(file);
                }
                else
                {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }
}