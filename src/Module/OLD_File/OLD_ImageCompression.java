package Module.OLD_File;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//TODO: 有奇怪的问题，个别图片会旋转，原因可能为exif中旋转数据丢失 在refactor中的imagecompression已经解决,等待测试后更新
public class OLD_ImageCompression {
    /**
     * 压缩图片，通过调整尺寸保持宽高比。
     *
     * @param imgpath 图片的路径。
     * @param size    压缩后的最大尺寸（最长边），输入0时默认使用1024。
     */
    public static void imageCompression(String imgpath, int size) {
        if (size == 0) {
            size = 1024;
        }

        try {
            // 读取源图片
            BufferedImage sourceImage = ImageIO.read(new File(imgpath));

            // 计算目标宽度和高度
            int sourceWidth = sourceImage.getWidth();
            int sourceHeight = sourceImage.getHeight();
            int targetWidth;
            int targetHeight;
            if (sourceWidth > sourceHeight) {
                // 源图片宽度大于高度
                targetWidth = size;
                targetHeight = (int) (sourceHeight / (double) sourceWidth * size);
            } else {
                // 源图片高度大于宽度或宽度等于高度
                targetWidth = (int) (sourceWidth / (double) sourceHeight * size);
                targetHeight = size;
            }

            // 创建目标图片
            BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

            // 绘制目标图片
            Graphics2D graphics2D = targetImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
            graphics2D.dispose();

            // 保存目标图片到文件
            ImageIO.write(targetImage, "jpg", new File(imgpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
