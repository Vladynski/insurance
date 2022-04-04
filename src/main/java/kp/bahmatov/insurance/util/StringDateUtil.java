package kp.bahmatov.insurance.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class StringDateUtil {
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private StringDateUtil(){}

    public static String format(LocalDateTime dateTime) {
        return LOCAL_DATE_TIME_FORMATTER.format(dateTime);
    }

    public static LocalDateTime parse(String string) {
        return LocalDateTime.parse(string, LOCAL_DATE_TIME_FORMATTER);
    }
}
