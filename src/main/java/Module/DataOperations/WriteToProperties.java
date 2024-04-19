package Module.DataOperations;

import Module.CheckOperations.SystemChecker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static Module.Others.GetPropertiesPath.settingspath;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 写入属性到Properties文件的工具类。
 */
public class WriteToProperties
{
    /**
     * 将属性写入指定的Properties文件中。
     *
     * @param filename    文件名
     * @param writeinlist 写入的属性列表，包含键值对数组
     */
    public static void writeToProperties(String filename, String[][] writeinlist)
    {
        SystemChecker system = new SystemChecker();
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            properties.load(fis);
            // 读取属性值...
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int i = 0; // 计数器
        while (writeinlist[0][i] != null)
        {
            properties.setProperty(writeinlist[0][i], writeinlist[1][i]);
            i++;
        }

        try (FileOutputStream fos = new FileOutputStream(settingspath()))
        {
            // 将属性写入文件
            properties.store(fos, null);
            systemPrintOut("Properties written to file", 1, 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
