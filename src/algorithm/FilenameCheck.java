package algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilenameCheck
{
    public int namecheck(String filepath,String imgpath,String copypath)
    {
        /*
        1  - 检查通过
        2  - 未选取路径
        3  - 路径名存在中文字符
        41 - filepath不存在
        42 - imgpath不存在
        43 - copypath不存在
        */
        if (filepath.equals("")||imgpath.equals("")||copypath.equals("")) //没选路径
        {
            return 2;
        }
        else
        {
            isContainChinese CNcheck = new isContainChinese();
            //路径里有中文
            if (CNcheck.isContainChinese(filepath) == true ||CNcheck.isContainChinese(imgpath) == true||CNcheck.isContainChinese(copypath) == true)
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
                File savepath = new File("savedpath.bbk");
                if (savepath.exists())
                {
                    savepath.delete();
                }

                FileWriter fw = null;
                try
                {
                    fw = new FileWriter(savepath,true);
                    String c = filepath+"\r\n"+imgpath+"\r\n"+copypath;
                    fw.write(c);
                    fw.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                    System.out.println("Failed");
                    System.exit(-1);
                }

                return 1;
            }
        }
    }
}
