package Module.FileOperations;

import java.io.File;

import static Module.Others.SystemPrintOut.systemPrintOut;

public class DeleteFilesWithKeyword
{
    /**
     * 删除文件夹内文件名包含关键词的文件。
     *
     * @param folderPath 文件夹路径
     * @param keyword    关键词
     */
    public static void deleteFilesWithKeyword(String folderPath, String keyword)
    {
        File folder = new File(folderPath);

        // 获取文件夹中的文件列表
        File[] files = folder.listFiles();

        // 遍历文件夹中的文件
        if (files != null)
        {
            for (File file : files)
            {
                // 检查文件名是否包含关键词
                if (file.getName().contains(keyword))
                {
                    // 删除文件
                    if (file.delete())
                    {
                        systemPrintOut("Delete:" + file.getName(), 1, 0);
                    }
                    else
                    {
                        systemPrintOut("Failed:" + file.getName(), 2, 0);
                    }
                }
            }
        }
    }
}
