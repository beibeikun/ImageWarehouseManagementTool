package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class GetLatestSubfolderPath
{
    private static final Logger logger = LoggerFactory.getLogger(GetLatestSubfolderPath.class);
    public static String getLatestSubfolder(String folderPath)
    {
        File folder = new File(folderPath);
        File[] subfolders = folder.listFiles(File::isDirectory);
        if (subfolders == null || subfolders.length == 0)
        {
            return null; // 没有子文件夹，直接返回null
        }

        File latestFolder = null;
        long latestTime = Long.MIN_VALUE;

        for (File subfolder : subfolders)
        {
            try
            {
                Path path = subfolder.toPath();
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                long creationTime = attrs.creationTime().toMillis();

                if (creationTime > latestTime)
                {
                    latestTime = creationTime;
                    latestFolder = subfolder;
                }
            }
            catch (IOException e)
            {
                logger.error("An error occurred: ", e);
            }
        }

        return Optional.ofNullable(latestFolder).map(File::getAbsolutePath).orElse(null);
    }
}
