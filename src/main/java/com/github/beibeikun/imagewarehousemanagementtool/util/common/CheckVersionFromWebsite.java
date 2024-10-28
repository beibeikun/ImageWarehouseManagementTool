package com.github.beibeikun.imagewarehousemanagementtool.util.common;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.github.beibeikun.imagewarehousemanagementtool.util.common.VersionNumber.getGithubVersionNumberApi;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.VersionNumber.officialVersionNumber;

public class CheckVersionFromWebsite
{

    public static String getLatestReleaseName(String repoUrl) throws IOException
    {
        // 创建 HttpURLConnection
        URL url = new URL(repoUrl + "/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode != 200)
        {
            throw new IOException("请求失败，响应码：" + responseCode);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null)
        {
            response.append(line);
        }
        reader.close();

        // 解析 JSON 响应
        Gson gson = new Gson();
        Release release = gson.fromJson(response.toString(), Release.class);

        // 返回最新 Release 的名字
        return release.getName();
    }

    public static boolean cheackLatestVersion() throws IOException
    {
        String latestReleaseName = getLatestReleaseName(getGithubVersionNumberApi());
        String versionNumber = "V" + officialVersionNumber();
        return versionNumber.equals(latestReleaseName);
    }

    private static class Release
    {
        private String name;

        public String getName()
        {
            return name;
        }
    }
}
