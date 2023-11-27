package Module.Refactor;
import java.io.IOException;

/**
 * 文件名更改处理类，封装了完整的文件名更改过程。
 */
public class CompleteNameChangeProcess {

    /**
     * 完成文件名更改的整个过程。
     *
     * @param nasFolderPath         NAS 文件夹路径
     * @param sourceFolderPath      源文件夹路径
     * @param targetFolderPath      目标文件夹路径
     * @param CSVPath               CSV 文件路径
     * @param checkBoxAddFromDatabase 复选框标志，1 表示添加数据库中的文件，0 表示不添加
     * @throws IOException 如果文件操作失败
     */
    public void completeNameChangeProcess(String nasFolderPath, String sourceFolderPath, String targetFolderPath, String CSVPath, int checkBoxAddFromDatabase) throws IOException {
        // 从 CSV 中提取要提取的文件名数组
        String[] fileNamesToExtract = ArrayExtractor.excludeFirstElement(ArrayExtractor.extractRow(ReadCsvFile.csvToArray(CSVPath), 0));

        // 根据 checkBoxAddFromDatabase 标志判断是否添加数据库中的文件
        if (checkBoxAddFromDatabase == 1) {
            // 提取压缩包
            FileExtractor.extractFiles(nasFolderPath, targetFolderPath, fileNamesToExtract);
            // 解压压缩包并删除
            UnzipAllZipsWithDelete.unzipAllZipsInFolder(targetFolderPath);
        }

        // 从源文件夹拷贝文件到目标文件夹
        FolderCopy.copyFolder(sourceFolderPath, targetFolderPath);

        // 重命名文件
        RenameFiles.renameFiles(CSVPath, targetFolderPath, 0, 0);

        // 删除目标文件夹中包含关键词的文件
        DeleteFilesWithKeyword.deleteFilesWithKeyword(targetFolderPath, "JB");
    }
}
