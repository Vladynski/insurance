package kp.bahmatov.insurance.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static LocalDateTime getStartNextDay() {
        LocalDateTime resultDate = LocalDateTime.now()
                .plusDays(1)
                .withNano(0)
                .withSecond(0)
                .withMinute(0)
                .withHour(0);
        long minutes = ChronoUnit.MINUTES.between(resultDate, LocalDateTime.now());
        //если до следующего дня осталось меньше чем два часа, увеличиваем крайний срок оплаты на сутки
        if (minutes < 120)
            return resultDate.plusDays(1);
        else
            return resultDate;
    }
}
