package com.github.beibeikun.imagewarehousemanagementtool.util.wordflow;

import com.github.beibeikun.imagewarehousemanagementtool.constant.files;
import com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 解压并删除工具类。
 */
public class UnzipAllZipsWithDelete
{

    /**
     * 解压指定文件夹中的所有 ZIP 文件并删除原始 ZIP 文件。
     *
     * @param folderPath 要解压的文件夹路径
     * @throws IOException 如果文件操作失败
     */
    public static void unzipAllZipsInFolder(String folderPath) throws IOException
    {
        Path sourceFolderPath = Paths.get(folderPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolderPath, files.ZIP_FILE_EXTENSION))
        {
            for (Path zipFilePath : stream)
            {
                unzipFile(zipFilePath, sourceFolderPath);
                // 删除已解压的 ZIP 文件
                if (Files.exists(zipFilePath))
                {
                    Files.delete(zipFilePath);
                }
                SystemPrintOut.systemPrintOut("Unzip:" + zipFilePath, 1, 0);
            }
        }
    }

    private static void unzipFile(Path zipFilePath, Path targetFolder) throws IOException
    {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath.toFile())))
        {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null)
            {
                Path entryPath = Paths.get(targetFolder.toString(), entry.getName());
                Files.createDirectories(entryPath.getParent());

                try (OutputStream outputStream = Files.newOutputStream(entryPath))
                {
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = zipInputStream.read(buffer)) > 0)
                    {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        }
    }
}
