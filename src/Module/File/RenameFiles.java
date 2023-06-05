package Module.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*--------------------------------------------
根据CSV文件对文件进行重命名的功能。

代码中包含一个方法renamefile，它接受CSV文件路径、图片路径、数字检查和前缀检查作为参数，并执行文件重命名操作。

在方法内部，首先创建一个二维数组filenamelist，用于存储CSV文件中的数据。还创建了一个字符串数组failedfile，用于存储转换失败的文件名（当前未使用），以及一个整数failednum用于计数。

接下来，通过BufferedReader读取CSV文件，并将数据存储到filenamelist数组中。

然后，创建一个表示图片目录的File对象，并使用listFiles()方法获取文件列表。

之后，遍历文件列表，对于每个文件执行以下操作：

获取文件名，并根据特定的格式进行切割，分别获取前缀和后缀部分。
使用循环遍历filenamelist数组，找到与前缀部分匹配的对应项。
如果找到匹配项，则根据数字检查和前缀检查的条件生成新的文件名。
如果需要添加前缀，则在新文件名前添加特定前缀。
构建新的文件对象dest，表示重命名后的文件。
使用renameTo()方法将原始文件重命名为新文件名，并输出重命名成功的提示信息。
如果没有找到匹配项，则输出重命名失败的提示信息，并将文件名存储到failedfile数组中。

（未完成）使用String[] failedfile尝试从图片库中进行拉取
--------------------------------------------*/

public class RenameFiles
{
    //digitcheck存在bug尚未修复
    public void renamefile(String excelpath, String imgpath, int digit_check, int prefix_check)
    {
        String[][] filenamelist = new String[2][10000]; //存放对应的JB号-Lot号
        String[] failedfile = new String[10000]; //存放转换失败的文件名，现在用不上
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
                        if (digit_check == 1)
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
                            else
                            {
                                newName = filenamelist[1][x] + BehindName;
                            }
                        }
                        else
                        {
                            newName = filenamelist[1][x] + BehindName;
                        }

                        if (prefix_check == 1) //判定是否添加前缀
                        {
                            newName = "Lot" + newName;
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

