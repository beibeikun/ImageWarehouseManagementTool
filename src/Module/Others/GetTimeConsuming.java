package Module.Others;

import java.time.Duration;
import java.time.Instant;

public class GetTimeConsuming
{
    /**
     * 计算两个时间戳之间的时间差，单位为秒
     *
     * @param start 开始时间戳
     * @param end   结束时间戳
     * @return 时间差（秒）
     */
    public static long getTimeConsuming(Instant start, Instant end)
    {
        // 计算两个时间戳之间的持续时间
        Duration duration = Duration.between(start, end);

        // 获取持续时间的秒数
        return duration.getSeconds();
    }
}
