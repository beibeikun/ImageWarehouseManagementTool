package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetPropertiesPath;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 写入属性到Properties文件的工具类。
 */
public class WriteToProperties
{
    public static void writeToPropertiesSingle(String filename, String name, String path)
    {
        String[][] filenamelist = new String[2][10];
        filenamelist[0][0] = name;
        filenamelist[1][0] = path;
        writeToProperties(filename, filenamelist);
    }
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
        try (FileInputStream fis = new FileInputStream(GetPropertiesPath.settingspath());
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

        try (FileOutputStream fos = new FileOutputStream(GetPropertiesPath.settingspath()))
        {
            // 将属性写入文件
            properties.store(fos, null);
            SystemPrintOut.systemPrintOut("Properties written to file", 1, 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
