package Module.File;

import Module.IdentifySystem;

import java.io.File;
import java.util.Objects;

import static Module.File.CopyFiles.copyfiles;

/*--------------------------------------------
用于移动文件并修改文件名的前缀。

代码中包含一个方法fileprefixmove，它接受最后文件夹路径和字符作为参数，并实现文件的移动和前缀修改操作。

在方法内部，首先创建一个File对象，表示最后文件夹路径，并使用listFiles()方法获取文件列表。然后，使用IdentifySystem类创建一个系统标识对象。

接下来，通过判断最后文件夹路径是否存在且为文件夹来进行操作。如果条件成立，开始遍历文件列表，对于每个文件执行以下操作：

获取文件名，并使用indexOf()方法查找指定字符的位置。如果找到指定字符，将文件名截取到该字符之前的部分；否则，将文件名截取到最后一个点号（.）之前的部分。
根据新的文件名和最后文件夹路径，构建一个新的文件对象file1。
如果file1不存在，则输出成功创建文件的提示，并使用mkdirs()方法创建对应的文件夹。
调用CopyFiles.copyfiles()方法将源文件移动到新的文件夹中，并输出移动成功的提示。
删除原始文件。
--------------------------------------------*/

public class FilePrefixMove {
    public void fileprefixmove(String lastpath,String character)
    {
        File file = new File(lastpath);
        File[] imglist = file.listFiles();
        IdentifySystem system = new IdentifySystem();
        if (file.exists() && file.isDirectory()) //如果lastpath存在且为文件夹
        {
            for (File value : Objects.requireNonNull(imglist))
            {
                String name = value.getName();
                int lo = name.indexOf(character);
                if (lo!=-1)//存在
                {
                    name = name.substring(0, lo);
                }
                else
                {
                    lo = name.indexOf(".");
                    name = name.substring(0, lo);
                }
                File file1 = new File(lastpath+system.identifysystem_String()+name);
                if (!file1.exists())
                {
                    System.out.println("Succeeded: Create file\"" + name + "\"");
                    file1.mkdirs();
                }
                copyfiles(lastpath+system.identifysystem_String()+value.getName(),lastpath+system.identifysystem_String()+name);
                System.out.println("Succeeded: " + value.getName());
                value.delete();
            }
        }
    }
}
