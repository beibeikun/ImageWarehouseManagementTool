package com.github.beibeikun.imagewarehousemanagementtool.util.wordflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressFileList
{
    /**
     * 压缩文件列表到指定的压缩文件。
     *
     * @param files          文件列表
     * @param compressedFile 压缩文件路径
     */
    public static void compressFiles(List<File> files, String compressedFile)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(compressedFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            zipOut.setLevel(Deflater.BEST_COMPRESSION);

            for (File file : files)
            {
                FileInputStream fis = new FileInputStream(file);

                String entryName = file.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0)
                {
                    zipOut.write(bytes, 0, length);
                }

                fis.close();
            }

            zipOut.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
