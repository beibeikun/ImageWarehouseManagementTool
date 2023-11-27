package Module.Refactor;
import java.io.File;

public class DeleteFilesWithKeyword {
    /**
     * 删除文件夹内文件名包含关键词的文件。
     *
     * @param folderPath 文件夹路径
     * @param keyword    关键词
     */
    static void deleteFilesWithKeyword(String folderPath, String keyword) {
        File folder = new File(folderPath);

        // 检查文件夹是否存在
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("文件夹不存在。");
            return;
        }

        // 获取文件夹中的文件列表
        File[] files = folder.listFiles();

        // 遍历文件夹中的文件
        if (files != null) {
            for (File file : files) {
                // 检查文件名是否包含关键词
                if (file.getName().contains(keyword)) {
                    // 删除文件
                    if (file.delete()) {
                        System.out.println("删除文件: " + file.getName());
                    } else {
                        System.err.println("无法删除文件: " + file.getName());
                    }
                }
            }
        }
    }
}
