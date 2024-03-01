package Module.Others;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetCorrectTime {
    /**
     * 获取当前时间的字符串表示，包括毫秒部分
     *
     * @return 包含毫秒部分的当前时间字符串
     */
    public static String getCorrectTime() {
        // 获取当前时间的毫秒数
        long millis = System.currentTimeMillis();

        // 将毫秒数转换为 Instant 对象
        Instant instant = Instant.ofEpochMilli(millis);

        // 将 Instant 对象转换为 LocalDateTime 对象，使用系统默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 格式化 LocalDateTime 对象，包括毫秒部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }

    /**
     * 获取适用于文件夹名称的当前时间字符串
     *
     * @return 适用于文件夹名称的当前时间字符串
     */
    public static String getCorrectTimeToFolderName() {
        // 获取当前时间的毫秒数
        long millis = System.currentTimeMillis();

        // 将毫秒数转换为 Instant 对象
        Instant instant = Instant.ofEpochMilli(millis);

        // 将 Instant 对象转换为 LocalDateTime 对象，使用系统默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 格式化 LocalDateTime 对象，适用于文件夹名称格式（两位年份、两位月份、两位日期、两位小时、两位分钟、两位秒）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }

}
