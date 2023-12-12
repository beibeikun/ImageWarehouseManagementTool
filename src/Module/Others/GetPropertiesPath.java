package Module.Others;

import Module.CheckOperations.SystemChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetPropertiesPath {
    public static String settingspath() {
        SystemChecker system = new SystemChecker();//获取系统类型

        String folderPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT";

        // 使用 Paths.get() 创建 Path 对象
        Path folder = Paths.get(folderPath);

        // 检查文件夹是否存在
        if (!Files.exists(folder)) {
            try {
                // 创建文件夹
                Files.createDirectories(folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String() + "settings.properties";

    }

    public static String propertiespath() {
        SystemChecker system = new SystemChecker();//获取系统类型
        return System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String();
    }
}
