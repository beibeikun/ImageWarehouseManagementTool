package Module.Others;

import Module.CheckOperations.SystemChecker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FirstTimeCheck {
    public static boolean firstTimeCheck() {
        SystemChecker system = new SystemChecker();//获取系统类型

        String folderPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT";

        // 使用 Paths.get() 创建 Path 对象
        Path folder = Paths.get(folderPath);

        // 检查文件夹是否存在
        if (!Files.exists(folder)) {
            try {
                // 创建文件夹
                Files.createDirectories(folder);

                String imageUrl = "https://ooo.0x0.ooo/2023/12/08/OAE5Eb.png"; // 替换为您要下载的图片的URL
                String savePath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT"+ system.identifySystem_String() + "logo.png"; // 替换为您希望保存图片的本地路径

                try {
                    downloadImage(imageUrl, savePath);
                    System.out.println("Image downloaded successfully!");
                } catch (IOException e) {
                    System.err.println("Error downloading image: " + e.getMessage());
                }

                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return true;
        }

    }
    public static void downloadImage(String imageUrl, String savePath) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        try (InputStream in = connection.getInputStream()) {
            Path outputPath = Paths.get(savePath);
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
