package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;

public class GetPropertiesPath
{
    public static String settingsPath()
    {

        String folderPath = System.getProperty("user.home") + identifySystem_String() + "Documents" + identifySystem_String() + "IWMT";

        // 使用 Paths.get() 创建 Path 对象
        Path folder = Paths.get(folderPath);

        // 检查文件夹是否存在
        if (! Files.exists(folder))
        {
            try
            {
                // 创建文件夹
                Files.createDirectories(folder);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        return System.getProperty("user.home") + identifySystem_String() + "Documents" + identifySystem_String() + "IWMT" + identifySystem_String() + "settings.properties";

    }

}
