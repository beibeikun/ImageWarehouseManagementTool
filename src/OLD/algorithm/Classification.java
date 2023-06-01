package OLD.algorithm;

import java.io.File;
import java.util.Objects;

import static OLD.algorithm.Copy.copy;

public class Classification
{
    public void classification(String imgpath)
    {
        File file = new File(imgpath);
        File[] imglist = file.listFiles();
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
        System.out.println("\n\n" + "-------------------------Start classification-------------------------");
        if (file.exists() && file.isDirectory())
        {
            for (File value : Objects.requireNonNull(imglist))
            {
                String name = value.getName();
                int lo = name.indexOf(" (");
                String clName = name.substring(0, lo);
                File file1 = new File(imgpath + symbol + clName);
                if (!file1.exists())
                {
                    System.out.println("Succeeded: Create file\"" + clName + "\"");
                    file1.mkdirs();
                }
                copy(imgpath + symbol + value.getName(), imgpath + symbol + clName);
                value.delete();
                System.out.println("Succeeded: " + name);
            }
        }
        System.out.println("-------------------------Classification succeeded-------------------------");
    }
}
