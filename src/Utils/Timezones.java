package Utils;

import java.time.*;

public class Timezones {
    public static LocalDateTime localToUTC(LocalDateTime localTime) {
        return ZonedDateTime.of(localTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime utcToLocal(LocalDateTime utcTime) {
        return ZonedDateTime.of(utcTime, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime ETConversion(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();

    }


    public static boolean businessHours(LocalDateTime estTime) {
        LocalTime officeOpen = LocalTime.of(9,0);
        LocalTime officeClose = LocalTime.of(17,0);

        return !estTime.toLocalTime().isBefore(officeOpen) && !estTime.toLocalTime().isAfter(officeClose);
    }
}
