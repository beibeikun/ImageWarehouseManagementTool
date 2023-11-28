package Module.File;
/*------------------------------------------------------------------
已重构，该代码当前停用
------------------------------------------------------------------*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 压缩包解压工具类。
 */
public class ZipExtractor {
    /**
     * 解压指定的压缩包到压缩包所在路径。
     *
     * @param zipFilePath 压缩包文件路径
     */
    public void extractZip(String zipFilePath) {
        try {
            // 获取压缩包所在的路径
            String extractPath = new File(zipFilePath).getParent();

            // 创建输入流读取压缩包
            try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
                ZipEntry entry;

                // 循环解压每个文件
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    String entryPath = extractPath + File.separator + entryName;

                    // 如果当前entry是目录，则创建对应的目录
                    if (entry.isDirectory()) {
                        File dir = new File(entryPath);
                        dir.mkdirs();
                    } else {
                        // 创建输出流将文件写入磁盘
                        try (FileOutputStream outputStream = new FileOutputStream(entryPath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                        }
                    }

                    // 关闭当前entry的输入流
                    zipInputStream.closeEntry();
                }
            }

            System.out.println("解压完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
