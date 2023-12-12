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

public class StartCheck {
    public static boolean StartCheck() {
        SystemChecker system = new SystemChecker();//获取系统类型

        String folderPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT";

        // 使用 Paths.get() 创建 Path 对象
        Path folder = Paths.get(folderPath);

        // 检查文件夹是否存在
        if (!Files.exists(folder)) {
            try {
                // 创建文件夹
                Files.createDirectories(folder);

                String logoUrl = "https://raw.githubusercontent.com/beibeikun/ImageWarehouseManagementTool/master/logo/logo.png"; // 替换为您要下载的图片的URL
                String logoPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String() + "logo.png"; // 替换为您希望保存图片的本地路径
                String zhpropertiesUrl = "https://raw.githubusercontent.com/beibeikun/ImageWarehouseManagementTool/master/properties/zh.properties";
                String zhpropertiesPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String() + "zh.properties";

                try {
                    downloadFile(logoUrl, logoPath);
                    downloadFile(zhpropertiesUrl, zhpropertiesPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return true;
        }

    }

    public static void downloadFile(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        try (InputStream in = connection.getInputStream()) {
            Path outputPath = Paths.get(savePath);
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
