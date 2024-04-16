package Module.Test;

import Module.CheckOperations.SystemChecker;
import Module.FileOperations.FolderCopy;

import java.io.IOException;
import java.util.Arrays;

import static Module.DataOperations.FileLister.getFileNames;
import static Module.DataOperations.FileNameProcessor.processFileNames;
import static Module.DataOperations.FileSearch.isFileExists;
import static Module.FileOperations.CreateFolder.createFolderWithTime;
import static Module.FileOperations.RenameFiles.renameFileWithName;
import static Module.Others.SystemPrintOut.systemPrintOut;

public class MoveNumberForward
{
    /**
     * 将指定文件夹中的文件编号前移
     *
     * @param sourceFolderPath 源文件夹路径
     * @param targetFolderPath 目标文件夹路径
     * @throws IOException 文件操作异常
     */
    public static void moveNumberForward(String sourceFolderPath, String targetFolderPath) throws IOException
    {
        // 创建系统检查器
        SystemChecker system = new SystemChecker();

        targetFolderPath = createFolderWithTime(targetFolderPath);
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);
        systemPrintOut(null, 0, 0);

        // 获取文件夹中的所有文件名
        String[] fileNameList = processFileNames(getFileNames(targetFolderPath));

        // 对文件名列表进行排序
        Arrays.sort(fileNameList);

        // 遍历每个文件名
        for (String s : fileNameList)
        {
            int number = 1;

            // 循环查找同名文件
            while (true)
            {
                // 构造文件路径
                String filePath = targetFolderPath + system.identifySystem_String() + s + " (" + number + ").jpg";

                // 判断文件是否存在
                boolean fileExists = isFileExists(filePath);

                // 文件存在
                if (fileExists)
                {
                    // 如果是第一个文件，则重命名为原文件名
                    if (number == 1)
                    {
                        renameFileWithName(filePath, s);
                    }
                    else
                    {
                        // 否则将文件重命名为原文件名 + (number - 1)
                        int newNum = number - 1;
                        renameFileWithName(filePath, s + " (" + newNum + ")");
                    }
                }
                else
                {
                    // 文件不存在，跳出循环
                    break;
                }

                // 递增编号
                number++;
            }
        }
    }


    public static void main(String[] args) throws IOException
    {
        /*
        boolean fileDetermination = isFileExists("/Users/bbk/Downloads/未命名文件夹 2/JB6364-018 (1).jpg");
        if (fileDetermination)
        {
            System.out.println(1111);
        }
        else
        {
            System.out.println(2222);
        }

        renameFileWithName("/Users/bbk/Downloads/未命名文件夹 2/JB6364-018 (1).jpg","JB6364-018");

         */
        moveNumberForward("/Users/bbk/photographs/IWMT_OUT/240415_093846", "/Users/bbk/photographs/IWMT_OUT");
    }
}
