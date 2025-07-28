package com.github.beibeikun.imagewarehousemanagementtool.util.DataOperations;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class GetFileCreationTime {
    /**
     * 获取指定文件的创建时间，格式为 年/月/日-时:分
     *
     * @param filePath 文件路径
     * @return 创建时间字符串，例如 "2025/07/28-10:35"
     * @throws IOException 文件不存在或读取失败时抛出
     */
    public static String getFileCreationTime(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("文件不存在：" + filePath);
        }

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime fileTime = attr.creationTime();

        // 转换为本地时间
        LocalDateTime ldt = LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());

        // 格式化为 年/月/日-时:分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm");
        return ldt.format(formatter);
    }
}
