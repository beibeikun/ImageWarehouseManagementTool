package OLD.algorithm;

import java.io.File;

public class DeleteFailed
{
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
