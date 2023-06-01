package Module.File;

import OLD.algorithm.isContainChinese;
import java.io.File;
import java.io.PrintWriter;

/*--------------------------------------------
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
                File savepath = new File("historysettings.bbk");
                try
                {
                    //写入的txt文档的路径
                    PrintWriter pw = new PrintWriter(savepath);
                    //写入的内容
                    String c =imgpath + "\r\n" + copypath + "\r\n" + filepath;
                    pw.write(c);
                    pw.flush();
                    pw.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return 1;
            }
        }
    }
}
