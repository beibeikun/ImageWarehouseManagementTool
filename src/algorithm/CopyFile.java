package algorithm;

import java.io.File;

import static algorithm.Copy.*;

public class CopyFile
{
    public void copyfile(String excelpath, String imgpath, String copypath)
    {
        String OSname = System.getProperty("os.name");
        String symbol = null;
        if (OSname.startsWith("Mac OS")) {
            // 苹果
            symbol = "/";
        } else if (OSname.startsWith("Windows")) {
            // windows
            symbol = "\\";
        } else {
            // unix or linux
        }
        File file = new File(imgpath);
        File[] imglist = file.listFiles();
        if (file.exists() && file.isDirectory())
        {
            System.out.println("\n\n" + "-------------------------Start to copy-------------------------");
            for (int i = 0; i < imglist.length; i++)
            {
                copy(imgpath + symbol + imglist[i].getName(), copypath);
                System.out.println("succeeded: " + imglist[i].getName());
            }
            System.out.println("-------------------------Copy succeeded-------------------------");

        }

    }
}
