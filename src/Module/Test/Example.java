package Module.Test;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static Module.Others.VersionNumber.finalVersionNumber;
import static Module.Others.VersionNumber.getGithubVersionNumberApi;

public class Example {

    public static String getLatestReleaseName(String repoUrl) throws IOException {
        // 创建 HttpURLConnection
        URL url = new URL(repoUrl + "/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("请求失败，响应码：" + responseCode);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 解析 JSON 响应
        Gson gson = new Gson();
        Release release = gson.fromJson(response.toString(), Release.class);

        // 返回最新 Release 的名字
        return release.getName();
    }
    public static boolean cheackLatestVersion() throws IOException {
        String latestReleaseName = getLatestReleaseName(getGithubVersionNumberApi());
        if (finalVersionNumber().equals(latestReleaseName)) {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static void main(String[] args) throws IOException {
        String latestReleaseName = getLatestReleaseName(getGithubVersionNumberApi());
        System.out.println("最新 Release 的名字：" + latestReleaseName);
        if (finalVersionNumber().equals(latestReleaseName)) {
            System.out.println("是最新 Release");
        }
        else
        {
            System.out.println("不是最新 Release");
        }
    }

    private static class Release {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
