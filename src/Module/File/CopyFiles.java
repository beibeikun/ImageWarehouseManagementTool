package Module.File;

import Module.IdentifySystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*--------------------------------------------
用于实现文件拷贝和删除操作。

copyfiles方法：用于将源文件夹中的文件拷贝到目标文件夹
copyfile方法：用于批量拷贝文件。接受源文件夹路径、图片文件夹路径和拷贝文件夹路径作为参数
deletefiles方法：用于删除指定文件夹中以"JB"开头的文件。它接受图片文件夹路径作为参数
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
    public void deletefiles(String imgpath)
    {
        File file = new File(imgpath);
        File[] imglist = file.listFiles();
        System.out.println("\n\n" + "-------------------------Start to delete failed files-------------------------");
        if (file.exists() && file.isDirectory())
        {
            for (int i = 0; i < imglist.length; i++)
            {
                if (imglist[i].getName().startsWith("JB"))
                {
                    String name = imglist[i].getName();
                    imglist[i].delete();
                    System.out.println("succeeded: " + name);
                }
            }
        }
        System.out.println("-------------------------Delete failed files succeeded-------------------------");
    }
}
