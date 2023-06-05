package Module.File;

import Module.IdentifySystem;
import Module.File.IsContainChinese;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/*--------------------------------------------
用于检查源文件夹、目标文件夹和CSV文件夹路径的合法性和正确性。
1  - 检查通过
2  - 未选取路径
3  - 路径名存在中文字符
41 - csv文件不存在
42 - 源文件路径不存在
43 - 目标文件路径不存在
5  - 目标文件夹不为空

代码中包含一个方法namecheck，它接受源文件夹路径、图片文件夹路径和拷贝文件夹路径作为参数，并返回一个整数值表示路径检查的结果。

在方法内部，首先检查是否有路径未选取，即参数中是否有空字符串。如果有空字符串，则返回2，表示未选取路径。

接下来，使用isContainChinese类的实例CNcheck来检查路径中是否包含中文字符。如果任何一个路径中包含中文字符，就返回3，表示路径名存在中文字符。

然后，通过创建File对象，检查路径对应的文件或文件夹是否存在。如果源文件夹、图片文件夹或拷贝文件夹不存在，分别返回41、42和43，表示相应的路径不存在。

如果路径检查通过，继续检查目标文件夹是否为空。通过创建File对象并使用listFiles()方法获取拷贝文件夹中的文件列表，如果文件列表长度不为0，说明目标文件夹不为空，返回5。

最后，如果所有路径检查通过，创建WriteToProperties对象并调用其writetoproperties方法，将路径信息写入属性文件。然后返回1，表示路径检查通过。
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
            IsContainChinese CNcheck = new IsContainChinese();
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

                WriteToProperties writeToProperties = new WriteToProperties();
                String[][] filenamelist = new String[2][10];
                filenamelist[0][0]="firstpath";
                filenamelist[1][0]=imgpath;
                filenamelist[0][1]="renamecsvpath";
                filenamelist[1][1]=filepath;
                filenamelist[0][2]="lastpath";
                filenamelist[1][2]=copypath;
                writeToProperties.writetoproperties("settings",filenamelist);

                return 1;
            }
        }
    }
}
