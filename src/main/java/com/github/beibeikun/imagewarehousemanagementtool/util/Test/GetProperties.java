package com.github.beibeikun.imagewarehousemanagementtool.util.Test;

import com.github.beibeikun.imagewarehousemanagementtool.util.Others.GetPropertiesPath;
import com.github.beibeikun.imagewarehousemanagementtool.util.Others.SystemPrintOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetProperties
{
    public static String getSingleProperties(String name) {
        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(GetPropertiesPath.settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            settingsproperties.load(reader);

            return settingsproperties.getProperty(name);
        }
        catch (IOException e)
        {
            SystemPrintOut.systemPrintOut("No settings file", 2, 0);
            e.printStackTrace();
        }
        return name;
    }

    public static Map<String, String> getPropertiesAsMap(String filePath) throws IOException {
        Properties properties = new Properties();
        // 使用 FileInputStream 读取 properties 文件
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }

        // 将 properties 转换为 HashMap
        Map<String, String> map = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.getProperty(key));
        }

        return map;
    }
}
