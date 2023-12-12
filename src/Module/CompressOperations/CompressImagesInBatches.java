package Module.CompressOperations;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.Others.SystemPrintOut.systemPrintOut;
import static Module.CompressOperations.ImageCompression.imageCompression;

public class CompressImagesInBatches {

    /**
     * 批量压缩图像。
     *
     * @param folderPath 文件夹路径
     * @param imageSize  目标图像大小
     */
    public static void compressImagesInBatches(String folderPath, int imageSize, boolean percent) throws ImageProcessingException, IOException, MetadataException {
        // 创建文件夹对象
        File folder = new File(folderPath);

        // 获取文件列表
        File[] files = folder.listFiles();


        int filesNums = files.length;
        int completedNums = 0;

        // 遍历文件列表并输出文件名
        for (File file : files) {
            completedNums++;
            float progress = ((float) completedNums / filesNums) * 100;
            // 创建DecimalFormat对象，指定小数点后两位的格式
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            // 格式化float值
            String progress_S = decimalFormat.format(progress);
            //判断隐藏文件
            if (!isSystemOrHiddenFile(file)) {
                // 压缩图像
                imageCompression(String.valueOf(file), imageSize);
                if (percent) {
                    systemPrintOut("now: " + completedNums + " All: " + filesNums + " Completed: " + progress_S + "% Compressed: " + file, 1, 0);
                } else {
                    systemPrintOut("Compressed: " + file, 1, 0);
                }
            } else {
                if (percent) {
                    systemPrintOut("now: " + completedNums + " All: " + filesNums + " Completed: " + progress_S + "% Unsupported file: " + file, 2, 0);
                } else {
                    systemPrintOut("Unsupported file: " + file, 2, 0);
                }

            }
        }
    }
}
