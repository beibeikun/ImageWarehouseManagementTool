package Module.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ReadRotationInformation {
    public static int readRotationInformation(String imgpath) throws IOException, ImageProcessingException, MetadataException {
        File file = new File(imgpath);
        BufferedImage image = ImageIO.read(file);
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        System.out.println("The rotation information of the specified JPG photo is: " + orientation);
        return orientation;
    }
}
