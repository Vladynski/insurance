package kp.bahmatov.insurance.util;

import java.time.LocalDateTime;

public class DateTimeUtil {
    public static LocalDateTime getStartNextDay() {
        //FIXME if create it a few minutes before tomorrow????
        return LocalDateTime.now()
                .plusDays(1)
                .withNano(0)
                .withSecond(0)
                .withMinute(0)
                .withHour(0);
    }
}
