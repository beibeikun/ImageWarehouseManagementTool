package Module.File;

import Module.IdentifySystem;
import OLD.algorithm.isContainChinese;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/*--------------------------------------------
用于检测源文件夹，目标文件夹与csv文件夹是否有误
1  - 检查通过
2  - 未选取路径
3  - 路径名存在中文字符
41 - csv文件不存在
42 - 源文件路径不存在
43 - 目标文件路径不存在
5  - 目标文件夹不为空
--------------------------------------------*/

public class FilenameCheck
{
    public int namecheck(String filepath, String imgpath, String copypath)
    {
        if (filepath.equals("") || imgpath.equals("") || copypath.equals("")) //没选路径
        {
            return 2;
        }
        else
        {
            isContainChinese CNcheck = new isContainChinese();
            //路径里有中文
            if (CNcheck.isContainChinese(filepath) || CNcheck.isContainChinese(imgpath) || CNcheck.isContainChinese(copypath))
            {
                return 3;
            }
            else
            {
                File file1 = new File(filepath);
                File file2 = new File(imgpath);
                File file3 = new File(copypath);
                if (!file1.exists())
                {
                    return 41;
                }
                else if (!file2.exists())
                {
                    return 42;
                }
                else if (!file3.exists())
                {
                    return 43;
                }
                File file = new File(copypath);//图片目录
                File[] copypathlist = file.listFiles();
                if (copypathlist.length != 0)
                {
                    return 5;
                }

                IdentifySystem system = new IdentifySystem();
                Properties properties = new Properties();
                try (FileInputStream fis = new FileInputStream("properties"+system.identifysystem_String()+"settings.properties");
                     InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
                    properties.load(fis);
                    // 读取属性值...
                } catch (IOException ea) {
                    ea.printStackTrace();
                }
                // 设置属性值
                properties.setProperty("firstpath",imgpath);
                properties.setProperty("renamecsvpath",filepath);
                properties.setProperty("lastpath",copypath);

                try (FileOutputStream fos = new FileOutputStream("properties"+system.identifysystem_String()+"settings.properties")) {
                    // 将属性以XML格式写入文件
                    properties.store(fos, null);
                    System.out.println("Properties written to XML file.");
                } catch (IOException ea) {
                    ea.printStackTrace();
                }

                return 1;
            }
        }
    }
}
