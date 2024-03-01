package Module.CompressOperations;
//TODO:要测试要测试要测试
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ImageCompression {
    public static void imageCompression(String imgpath, int size) throws IOException {
        Thumbnails.of(imgpath)
                .size(size, size)
                .outputQuality(0.9f)
                .toFile(imgpath);
    }
}