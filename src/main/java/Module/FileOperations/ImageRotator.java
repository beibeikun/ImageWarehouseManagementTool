package Module.FileOperations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRotator
{
    /**
     * 旋转图片
     *
     * @param imagePath         图片路径
     * @param rotationDirection 旋转方向，0表示顺时针旋转90度，1表示逆时针旋转90度
     */
    public static void rotateImage(String imagePath, int rotationDirection) throws IOException
    {
        File inputFile = new File(imagePath);
        BufferedImage originalImage = ImageIO.read(inputFile);

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (rotationDirection == 0)
                {
                    rotatedImage.setRGB(height - 1 - j, i, originalImage.getRGB(i, j));
                }
                else if (rotationDirection == 1)
                {
                    rotatedImage.setRGB(j, width - 1 - i, originalImage.getRGB(i, j));
                }
            }
        }

        File outputFile = new File(imagePath); // 替换为实际的输出路径
        ImageIO.write(rotatedImage, "jpg", outputFile);
    }
}
