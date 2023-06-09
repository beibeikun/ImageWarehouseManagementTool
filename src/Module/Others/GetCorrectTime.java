package Module.Others;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetCorrectTime {
    public static String getCorrectTime()
    {
        long millis = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

// 格式化时间，包括毫秒部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }
}
