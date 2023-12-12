package Module.CompleteProcess;

import java.io.File;
import java.io.IOException;

import static Module.CheckOperations.HiddenFilesChecker.isSystemOrHiddenFile;
import static Module.FileOperations.ChangeSuffix.changeSuffix;
import static Module.FileOperations.CreateTemporaryDestinationFolder.createTemporaryDestinationFolder;
import static Module.FileOperations.FolderCopy.copyFolder;
//TODO:能用了但还没实装
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
        String suffixfolder;
        if (mode == 1)
        {
            suffixfolder = createTemporaryDestinationFolder(filepath,"_suffix");
        }
        else {
            suffixfolder = targetpath;
        }
        copyFolder(filepath,suffixfolder);
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
    }

    /**
     * 主方法，用于测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) throws IOException {
        // 示例用法
        String image = "/Users/bbk/photographs/test";
        changeAllSuffix(image,"",0);
    }
}
