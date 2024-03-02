package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;
import java.io.IOException;

import static Module.DataOperations.FileLister.getFileNames;
import static Module.DataOperations.FileNameProcessor.processFileNames;
import static Module.FileOperations.CreateFolder.createFolderWithTime;
import static Module.FileOperations.FileCopyAndDelete.copyFile;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class ExtractMainImage
{

    /**
     * 从源文件夹中提取主图像到目标文件夹。
     *
     * @param sourceFolder      源文件夹路径
     * @param destinationFolder 目标文件夹路径
     */
    public static void extractMainImage(String sourceFolder, String destinationFolder) throws IOException
    {
        systemPrintOut("Start to take main img from source path", 3, 0);
        SystemChecker system = new SystemChecker();
        destinationFolder = createFolderWithTime(destinationFolder);
        File folder = new File(sourceFolder);

        if (! folder.exists() || ! folder.isDirectory())
        {
            systemPrintOut("Source folder does not exist or is not a directory.", 2, 0);
            return;
        }
        //获取源文件夹内所有文件名,处理文件名数组，去除文件后缀名、去除 "(x)" 后缀并删除重复项，只保留一个
        String[] FileNames = processFileNames(getFileNames(sourceFolder));
        for (String fileName : FileNames)
        {
            copyFile(sourceFolder + system.identifySystem_String() + fileName + ".JPG", destinationFolder);
            systemPrintOut("Copy:" + fileName, 1, 0);
        }
        systemPrintOut(null, 0, 0);
    }
}
