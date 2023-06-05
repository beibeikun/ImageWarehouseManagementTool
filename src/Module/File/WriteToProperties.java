package Module.File;

import Module.IdentifySystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 写入属性到Properties文件的工具类。
 */
public class WriteToProperties {
    /**
     * 将属性写入指定的Properties文件中。
     *
     * @param filename     文件名
     * @param writeinlist  写入的属性列表，包含键值对数组
     */
    public void writeToProperties(String filename, String[][] writeinlist) {
        IdentifySystem system = new IdentifySystem();
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("properties" + system.identifySystem_String() + filename + ".properties");
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            properties.load(fis);
            // 读取属性值...
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0; // 计数器
        while (writeinlist[0][i] != null) {
            properties.setProperty(writeinlist[0][i], writeinlist[1][i]);
            i++;
        }

        try (FileOutputStream fos = new FileOutputStream("properties" + system.identifySystem_String() + filename + ".properties")) {
            // 将属性写入文件
            properties.store(fos, null);
            System.out.println("Properties written to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
