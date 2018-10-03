package fr.istic.gm.weplan.infra.utils;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateUtils {

    public static DateTimeFormatter WEATHER_DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd:H")
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    private DateUtils() {
    }
}
