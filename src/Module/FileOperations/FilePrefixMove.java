package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

/**
 * 用于移动文件并修改文件名前缀的功能。
 */
public class FilePrefixMove {
    /**
     * 将指定路径下的文件移动到新路径，并修改文件名前缀。
     *
     * @param lastpath  文件夹的路径
     * @param character 需要修改的文件名前缀字符
     */
    public static void filePrefixMove(String lastpath, String character) {
        File file = new File(lastpath);
        File[] imglist = file.listFiles();
        SystemChecker system = new SystemChecker();
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
                    systemPrintOut("Create file\"" + name + "\"", 1, 0);
                    file1.mkdirs();
                }
                copyFile(lastpath + system.identifySystem_String() + value.getName(), lastpath + system.identifySystem_String() + name);
                systemPrintOut(value.getName(), 1, 0);
                value.delete();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        // 示例用法
        String image = "D:\\1111\\222";
        filePrefixMove(image," (");
    }
}
