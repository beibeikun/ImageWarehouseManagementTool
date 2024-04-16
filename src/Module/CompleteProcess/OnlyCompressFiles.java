package Module.CompleteProcess;

import java.io.IOException;

import static Module.CompressOperations.ImageCompression.compressImgWithFileListUseMultithreading;
import static Module.DataOperations.FileLister.getFileNamesInList;
import static Module.FileOperations.CreateFolder.createFolderWithTime;
import static Module.FileOperations.FolderCopy.copyFolder;

public class OnlyCompressFiles
{
    public static void onlyCompressFiles(String sourceFolderPath, String targetFolderPath, int imgSize) throws IOException
    {
        targetFolderPath = createFolderWithTime(targetFolderPath);
        copyFolder(sourceFolderPath, targetFolderPath);
        compressImgWithFileListUseMultithreading(getFileNamesInList(targetFolderPath), imgSize);
    }
}
