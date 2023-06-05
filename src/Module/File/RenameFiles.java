package Module.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*--------------------------------------------
用于对文件根据csv进行重命名
excelpath-csv文件路径
c1check-添加为三位编号
c2check-添加前缀
（未完成）使用String[] failedfile尝试从图片库中进行拉取
--------------------------------------------*/

public class RenameFiles
{
    public void renamefile(String excelpath, String imgpath, int c1check, int c2check)
    {
        String[][] filenamelist = new String[2][10000]; //存放对应的JB号-Lot号
        String[] failedfile = new String[10000]; //存放转换失败的文件名，但现在好像用不上
        int failednum = 0; //同上用于计数

        /*-------读取csv文件储存到filenamelist-------*/
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        int ii = 0;
        try
        {
            br = new BufferedReader(new FileReader(excelpath));
            while ((line = br.readLine()) != null)
            {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                filenamelist[0][ii] = country[0];
                filenamelist[1][ii] = country[1];
                ii++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*-------读取csv文件储存到filenamelist-------*/


        File file = new File(imgpath);//图片目录
        File[] imglist = file.listFiles();
        String newName = null;
        String oldname;
        File dest = null;
        System.out.println("\n\n" + "-------------------------Start to rename-------------------------");
        if (file.exists() && file.isDirectory())
        {
            for (int i = 0; i < imglist.length; i++)
            {
                //取文件名存入name中
                String name = imglist[i].getName();
                String FrontName = name.substring(0, 10);//格式为“JBOOOO-OOO”
                String BehindName = name.substring(10);//格式为" (00).OOO"注意这里前面有个空格
                int x = 0;
                while (x < ii)
                {
                    dest = null;
                    if (filenamelist[0][x].equals(FrontName))
                    {
                        if (c1check == 1)
                        {
                            int num = Integer.parseInt(filenamelist[1][x]);
                            if (num / 10 == 0)
                            {
                                newName = "00" + filenamelist[1][x] + BehindName;
                            }
                            else if (num / 10 < 10)
                            {
                                newName = "0" + filenamelist[1][x] + BehindName;
                            }
                        }
                        else
                        {
                            newName = filenamelist[1][x] + BehindName;
                        }

                        if (c2check == 1) //判定是否添加前缀
                        {
                            newName = "Lot " + newName;
                        }
                        dest = new File(imgpath + "/" + newName);
                        oldname = imglist[i].getName();
                        imglist[i].renameTo(dest);
                        System.out.println("succeeded: " + oldname + " --> " + newName);
                        break;
                    }
                    x++;
                }
                if (dest == null)
                {
                    System.out.println("failed: " + name);
                    failedfile[failednum] = name;
                    failednum++;
                }
            }
        }
        System.out.println("-------------------------Rename succeeded-------------------------");
/*
        int x = 0;
        while (x < ii)
        {
            if (filenamelist[1][x].equals(""))
            {
                System.out.println("No found: "+filenamelist[0][x]);
            }
            x++;
        }

 */
    }
}

