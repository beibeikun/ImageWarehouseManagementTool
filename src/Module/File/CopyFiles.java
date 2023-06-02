package Module.File;

import Module.IdentifySystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*--------------------------------------------
输入源文件夹路径与目标文件夹路径
将所有源文件夹文件拷贝至目标文件夹
--------------------------------------------*/

public class CopyFiles {
    static void copyfiles(String srcPathStr, String desPathStr)
    {
        IdentifySystem systemtype = new IdentifySystem();

        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf(systemtype.identifysystem_String()) + 1); //目标文件地址
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        try
        {
            FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
            byte datas[] = new byte[1024 * 8];//创建搬运工具
            int len = 0;//创建长度
            while ((len = fis.read(datas)) != -1)//循环读取数据
            {
                fos.write(datas, 0, len);
            }
            fos.close();//释放资源
            fis.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void copyfile(String excelpath, String imgpath, String copypath)
    {
        IdentifySystem systemtype = new IdentifySystem();
        File file = new File(imgpath);
        File[] imglist = file.listFiles();
        if (file.exists() && file.isDirectory())
        {
            System.out.println("\n\n" + "-------------------------Start to copy-------------------------");
            for (int i = 0; i < imglist.length; i++)
            {
                copyfiles(imgpath + systemtype.identifysystem_String() + imglist[i].getName(), copypath);
                System.out.println("succeeded: " + imglist[i].getName());
            }
            System.out.println("-------------------------Copy succeeded-------------------------");

        }

    }
}
