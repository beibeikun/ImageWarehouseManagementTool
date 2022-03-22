package algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Classification
{
    public void classification(String imgpath)
    {
        File file = new File(imgpath);
        File[] imglist = file.listFiles();
        System.out.println("\n\n"+"-------------------------Start classification-------------------------");
        if (file.exists() && file.isDirectory())
        {
            for (int i = 0; i < imglist.length; i++)
            {
                String name = imglist[i].getName();
                int lo=name.indexOf(" (");
                String clName=name.substring(0,lo);
                File file1 = new File(imgpath+"/"+clName);
                if(!file1 .exists()) {
                    System.out.println("Succeeded: Create file\""+clName+"\"");
                    file1.mkdirs();
                }
                copy(imgpath+"\\"+imglist[i].getName(),imgpath+"\\"+clName);
                imglist[i].delete();
                System.out.println("Succeeded: "+name);
            }
        }
        System.out.println("-------------------------Classification succeeded-------------------------");
    }
    static void copy(String srcPathStr, String desPathStr)
    {
        //获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //目标文件地址
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        try
        {
            FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!=-1)//循环读取数据
            {
                fos.write(datas,0,len);
            }
            fos.close();//释放资源
            fis.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
