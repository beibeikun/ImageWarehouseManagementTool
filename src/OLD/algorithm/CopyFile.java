package OLD.algorithm;

import Module.IdentifySystem;

import java.io.File;

import static OLD.algorithm.Copy.copy;

public class CopyFile
{
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
                copy(imgpath + systemtype.identifysystem_String() + imglist[i].getName(), copypath);
                System.out.println("succeeded: " + imglist[i].getName());
            }
            System.out.println("-------------------------Copy succeeded-------------------------");

        }

    }
}
