package Module.Refactor;
import java.io.File;

import static Module.File.ImageCompression.imageCompression;
import static Module.Refactor.FileChecker.isSystemOrHiddenFile;

public class CompressImagesInBatches {

    public static void main(String[] args) {
        String folderPath = "/Users/bbk/photographs/test3";
        int imageSize = 300;
        compressImagesInBatches(folderPath, imageSize);
    }

    /**
     * 批量压缩图像。
     *
     * @param folderPath 文件夹路径
     * @param imageSize  目标图像大小
     */
    public static void compressImagesInBatches(String folderPath, int imageSize) {
        // 创建文件夹对象
        File folder = new File(folderPath);

        // 检查是否为有效的文件夹路径
        if (!folder.isDirectory()) {
            System.out.println("无效的文件夹路径: " + folderPath);
            return;
        }

        // 获取文件列表
        File[] files = folder.listFiles();

        // 检查文件列表是否为空
        if (files == null) {
            System.out.println("访问文件夹时发生错误: " + folderPath);
            return;
        }

        int filesNums = files.length;
        int completedNums = 0;

        // 遍历文件列表并输出文件名
        for (File file : files) {
            //判断隐藏文件
            if (!isSystemOrHiddenFile(file))
            {
                // 压缩图像
                imageCompression(String.valueOf(file), imageSize);
                completedNums++;
                float progress = ((float) completedNums / filesNums) * 100;
                System.out.println("第" + completedNums + "件  共" + filesNums + "件  已完成" + String.format("%.2f", progress) + "%");
            }
        }
    }
}
