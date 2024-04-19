package Module.FileOperations;

import Module.CheckOperations.SystemChecker;

import java.io.File;
import java.io.IOException;

import static Module.DataOperations.DeleteTypeCheck.deleteTypeCheck;
import static Module.DataOperations.FileSearch.isDirectoryEmpty;
import static Module.DataOperations.FileSearch.isFileExists;
import static Module.FileOperations.DeleteDirectory.deleteDirectory;
import static Module.FileOperations.DeleteDirectory.deleteFile;

public class DeleteFileFromDatabase
{
    public static void deleteFileFromDatabase(String destinationFolder, String deleteNum) throws IOException
    {
        SystemChecker system = new SystemChecker();
        //TODO
        if (deleteTypeCheck(deleteNum)) // 完整
        {

        }
        else //单个
        {
            String deletePath = destinationFolder + system.identifySystem_String() + deleteNum.substring(0, 6) + system.identifySystem_String() + deleteNum + ".zip";
            String folderPath = destinationFolder + system.identifySystem_String() + deleteNum.substring(0, 6);
            String thumbnailPath = destinationFolder + system.identifySystem_String() + "thumbnail" + system.identifySystem_String() + deleteNum + ".JPG";
            if (isFileExists(deletePath)) //文件存在，执行删除
            {
                deleteFile(deletePath);//删除压缩包
                if (isDirectoryEmpty(folderPath)) // 如果文件夹内已经没有其他文件，则删除文件夹
                {
                    deleteDirectory(new File(folderPath));
                }
                deleteFile(thumbnailPath);//删除压缩图
            }
            else
            {

            }
        }
    }
}
