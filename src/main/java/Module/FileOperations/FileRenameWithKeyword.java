package Module.FileOperations;

import java.io.File;

/**
 * 文件重命名工具类
 */
public class FileRenameWithKeyword
{

    /**
     * 使用关键词重命名文件
     *
     * @param filePath 要重命名的文件路径
     * @param keyword1 要替换的关键词
     * @param keyword2 替换后的关键词
     * @return 重命名后的文件路径
     */
    public static String renameFileWithKeyword(String filePath, String keyword1, String keyword2)
    {
        // 创建文件对象
        File file = new File(filePath);

        // 检查文件是否存在
        if (file.exists())
        {
            // 获取文件名
            String fileName = file.getName();

            // 使用关键词替换文件名中的内容
            String newFileName = fileName.replace(keyword1, keyword2);

            // 创建新的文件对象
            File newFile = new File(file.getParent(), newFileName);

            // 尝试重命名文件
            if (file.renameTo(newFile))
            {
                // 返回重命名后的文件路径
                return String.valueOf(newFile);
            }
            else
            {
                // 如果重命名失败，返回新文件的路径（可能未发生实际重命名）
                return String.valueOf(newFile);
            }
        }
        else
        {
            // 如果文件不存在，返回空字符串
            return "";
        }
    }
}
