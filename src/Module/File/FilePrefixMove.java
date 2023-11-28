package Module.File;
/*------------------------------------------------------------------
已重构，该代码当前停用
------------------------------------------------------------------*/
import Module.Others.IdentifySystem;

import java.io.File;
import java.util.Objects;

import static Module.File.FileOperations.copyFile;

/**
 * 用于移动文件并修改文件名前缀的功能。
 */
public class FilePrefixMove {
    /**
     * 将指定路径下的文件移动到新路径，并修改文件名前缀。
     *
     * @param lastpath   文件夹的路径
     * @param character  需要修改的文件名前缀字符
     */
    public void filePrefixMove(String lastpath, String character) {
        File file = new File(lastpath);
        File[] imglist = file.listFiles();
        IdentifySystem system = new IdentifySystem();
        if (file.exists() && file.isDirectory()) {
            for (File value : Objects.requireNonNull(imglist)) {
                String name = value.getName();
                int lo = name.indexOf(character);
                if (lo != -1) {
                    // 存在指定字符
                    name = name.substring(0, lo);
                } else {
                    lo = name.indexOf(".");
                    name = name.substring(0, lo);
                }
                File file1 = new File(lastpath + system.identifySystem_String() + name);
                if (!file1.exists()) {
                    System.out.println("succeeded: Create file\"" + name + "\"");
                    file1.mkdirs();
                }
                copyFile(lastpath + system.identifySystem_String() + value.getName(), lastpath + system.identifySystem_String() + name);
                System.out.println("succeeded:\"" + value.getName() + "\"");
                value.delete();
            }
        }
    }
}
