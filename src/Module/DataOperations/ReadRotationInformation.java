package Module.DataOperations;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 这是一个用于读取图片旋转信息的类
public class ReadRotationInformation {
    /**
     * 读取指定图片的EXIF旋转信息
     *
     * @param imgpath 图片的路径。
     */
    public static int readRotationInformation(String imgpath) throws IOException, ImageProcessingException, MetadataException {
        // 创建一个文件对象，该对象对应于路径为imgpath的文件
        File file = new File(imgpath);
        // 使用ImageIO读取图片文件，返回一个BufferedImage对象
        BufferedImage image = ImageIO.read(file);
        // 使用ImageMetadataReader读取文件的元数据
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        // 获取元数据中的ExifIFD0Directory对象
        ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        // 如果ExifIFD0Directory对象存在并且包含旋转信息
        if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
            // 读取并返回旋转信息
            return directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        } else {
            // 如果ExifIFD0Directory对象不存在或者不包含旋转信息，则返回0
            return 0;
        }
    }
}
