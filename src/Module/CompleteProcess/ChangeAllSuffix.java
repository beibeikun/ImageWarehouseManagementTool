package Module.CompleteProcess;

import java.io.File;
import java.io.IOException;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.FileOperations.ChangeSuffix.changeSuffix;
import static Module.FileOperations.CreateTemporaryDestinationFolder.createTemporaryFolder;
import static Module.FileOperations.FolderCopy.copyFolder;
import static Module.Others.SystemPrintOut.systemPrintOut;
/**
 * 批量更改文件后缀的类
 */
public class ChangeAllSuffix {

    /**
     * 批量更改文件后缀的方法
     *
     * @param filepath 文件夹路径
     */
    public static void changeAllSuffix(String filepath, String targetpath, int mode) throws IOException {
        systemPrintOut("Start to change suffix", 3, 0);
        String suffixfolder;
        if (mode == 1)
        {
            suffixfolder = createTemporaryFolder(filepath,"_suffix");
        }
        else {
            suffixfolder = targetpath;
        }
        copyFolder(filepath,suffixfolder);
        systemPrintOut(null, 0, 0);
        // 创建文件夹对象
        File imageFolder = new File(suffixfolder);

        // 获取文件夹下的文件列表
        File[] imageList = imageFolder.listFiles();

        // 检查文件夹是否存在且为目录
        if (imageFolder.exists() && imageFolder.isDirectory()) {
            // 遍历文件列表
            for (File image : imageList) {
                // 检查是否为系统或隐藏文件
                if (isSystemOrHiddenFile(image)) {
                    // 跳过系统或隐藏文件
                    continue;
                } else {
                    // 调用更改后缀的方法
                    changeSuffix(image);
                }
            }
        }
        systemPrintOut(null, 0, 0);
    }
}
