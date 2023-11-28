package Module.Test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRotator {

    public static void main(String[] args) {
        String imagePath = "/Users/bbk/photographs/test2/JB1234-333 (4).JPG"; // 替换为实际的图像路径
        int rotationDirection = 1; // 0表示逆时针，1表示顺时针

        try {
            rotateImage(imagePath, rotationDirection);
            System.out.println("图像旋转成功！");
        } catch (IOException e) {
            System.out.println("图像旋转失败：" + e.getMessage());
        }
    }

    public static void rotateImage(String imagePath, int rotationDirection) throws IOException {
        File inputFile = new File(imagePath);
        BufferedImage originalImage = ImageIO.read(inputFile);

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (rotationDirection == 0) {
                    rotatedImage.setRGB(height - 1 - j, i, originalImage.getRGB(i, j));
                } else if (rotationDirection == 1) {
                    rotatedImage.setRGB(j, width - 1 - i, originalImage.getRGB(i, j));
                }
            }
        }

        File outputFile = new File(imagePath); // 替换为实际的输出路径
        ImageIO.write(rotatedImage, "jpg", outputFile);
    }
}
