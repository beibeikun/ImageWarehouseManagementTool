package com.github.beibeikun.imagewarehousemanagementtool.util.Others;

import com.github.beibeikun.imagewarehousemanagementtool.util.CheckOperations.SystemChecker;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class StartCheck
{
    /**
     * 启动检查类
     *TODO:注释缺失 功能修正
     */
    public static void startCheck() throws IOException
    {

        if (! VersionNumber.getReleaseType()) // 如果为正式版，则检查版本更新
        {
            if (! CheckVersionFromWebsite.cheackLatestVersion())
            {
                JOptionPane.showMessageDialog(null, "检查到新版本，请及时更新");
                try
                {
                    Desktop.getDesktop().browse(new URI(VersionNumber.getGithubLatestRelease()));
                }
                catch (Exception ea)
                {
                    ea.printStackTrace();
                }
            }
        }

        //获取文档中的版本号与系统内的版本号
        String fileVersionNumber = null;
        String systemVersionNumber;
        if (VersionNumber.getReleaseType())
        {
            systemVersionNumber = VersionNumber.officialVersionNumber();
        }
        else
        {
            systemVersionNumber = VersionNumber.betaVersionNumber();
        }

        Properties settingsproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(GetPropertiesPath.settingspath());
             InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8))
        {
            settingsproperties.load(reader);
            fileVersionNumber = settingsproperties.getProperty("versionNumber");
            // 读取属性值...
        }
        catch (IOException e)
        {
            SystemPrintOut.systemPrintOut("No settings file", 2, 0);
            e.printStackTrace();
        }

        SystemChecker system = new SystemChecker();//获取系统类型

        String folderPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT";

        // 使用 Paths.get() 创建 Path 对象
        Path folder = Paths.get(folderPath);

        // 检查文件夹是否存在 不存在则新建文件夹并下载配置文件
        if (! Files.exists(folder))
        {
            try
            {
                // 创建文件夹
                Files.createDirectories(folder);

                String logoUrl = "https://raw.githubusercontent.com/beibeikun/ImageWarehouseManagementTool/master/src/main/resources/logo.png"; // 替换为您要下载的图片的URL
                String logoPath = System.getProperty("user.home") + system.identifySystem_String() + "Documents" + system.identifySystem_String() + "IWMT" + system.identifySystem_String() + "logo.png"; // 替换为您希望保存图片的本地路径
                try
                {
                    downloadFile(logoUrl, logoPath);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static void downloadFile(String fileUrl, String savePath) throws IOException
    {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        try (InputStream in = connection.getInputStream())
        {
            Path outputPath = Paths.get(savePath);
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}