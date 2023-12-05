package Module.Others;

import java.time.Duration;
import java.time.Instant;

public class GetTimeConsuming {
    public static long getTimeConsuming(Instant start, Instant end) {
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }
}
