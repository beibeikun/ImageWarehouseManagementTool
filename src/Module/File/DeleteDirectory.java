package Module.File;

import java.io.File;

/*--------------------------------------------
用于递归删除指定目录及其子目录中的所有文件和文件夹。

代码中包含一个静态方法deleteDirectory，它接受一个File对象（表示要删除的目录）作为参数，并返回一个布尔值表示是否成功删除目录。

在方法内部，首先通过directory.listFiles()获取目录下的所有文件和文件夹，并将它们存储在files数组中。然后，通过遍历files数组，对于每个文件或文件夹，进行如下操作：
如果是一个子目录，递归调用deleteDirectory方法，以删除子目录及其内容。
如果是一个文件，直接调用file.delete()方法删除文件。
最后，返回directory.delete()的结果，即尝试删除指定目录本身，并返回是否删除成功。
--------------------------------------------*/

public class DeleteDirectory {
    public static boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }
}
