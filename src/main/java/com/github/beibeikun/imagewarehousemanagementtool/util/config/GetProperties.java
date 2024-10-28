package com.github.beibeikun.imagewarehousemanagementtool.util.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * GetProperties 类用于读取 properties 配置文件并将其转换为一个键值对的地图。
 */
public class GetProperties
{
/**
 * 读取 CSV 文件并将其以键值对的形式返回为一个 Map 。
 *
 * @param filePath CSV 文件的路径
 * @return 一个包含所有 properties 轻sub
 */
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
