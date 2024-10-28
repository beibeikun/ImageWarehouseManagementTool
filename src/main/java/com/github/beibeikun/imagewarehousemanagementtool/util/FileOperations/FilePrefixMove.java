package com.github.beibeikun.imagewarehousemanagementtool.util.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.github.beibeikun.imagewarehousemanagementtool.filter.SystemChecker.identifySystem_String;
import static com.github.beibeikun.imagewarehousemanagementtool.util.common.SystemPrintOut.systemPrintOut;

/**
 * 用于移动文件并修改文件名前缀的功能。
 */
public class FilePrefixMove
{
    /**
     * 将指定路径下的文件移动到新路径，并修改文件名前缀。
     *
     * @param lastpath  文件夹的路径
     * @param character 需要修改的文件名前缀字符
     */
    public static void filePrefixMove(String lastpath, String character) throws IOException
    {
        File file = new File(lastpath);
        File[] imgList = file.listFiles();
        Arrays.sort(imgList);
        if (file.exists() && file.isDirectory())
        {
            for (File value : Objects.requireNonNull(imgList))
            {
                String name = value.getName();
                int lo = name.indexOf(character);
                if (lo != - 1)
                {
                    // 存在指定字符
                    name = name.substring(0, lo);
                }
                else
                {
                    lo = name.indexOf(".");
                    name = name.substring(0, lo);
                }
                File file1 = new File(lastpath + identifySystem_String() + name);
                if (! file1.exists())
                {
                    systemPrintOut("Create file\"" + name + "\"", 1, 0);
                    file1.mkdirs();
                }
                FileCopyAndDelete.copyFile(lastpath + identifySystem_String() + value.getName(), lastpath + identifySystem_String() + name);
                systemPrintOut(value.getName(), 1, 0);
                value.delete();
            }
        }
    }
}
