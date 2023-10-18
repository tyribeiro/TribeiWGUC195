package Utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Timezones {
    public static LocalDateTime utcConversion(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }

    public static LocalDateTime localTimeConversion(LocalDateTime utcDateTime){
        ZonedDateTime zoned = utcDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime local = zoned.withZoneSameInstant(ZoneId.systemDefault());
        return  local.toLocalDateTime();
    }

    public static LocalDateTime ETConversion(LocalDateTime estDateTime){
        ZonedDateTime zoned = estDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime est = zoned.withZoneSameInstant(ZoneId.of("America/New_York"));
        return est.toLocalDateTime();
    }

    public static LocalDateTime fromET(LocalDateTime estDateTime){
        ZonedDateTime zoned = estDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime local = zoned.withZoneSameInstant(ZoneId.systemDefault());
        return local.toLocalDateTime();
    }

    public static boolean businessHours(LocalDateTime estDateTime){
        LocalTime officeOpen = LocalTime.of(9,0);
        LocalTime officeClose = LocalTime.of(17,0);
        LocalTime apptTime = estDateTime.toLocalTime();

        return !apptTime.isBefore(officeOpen) && !apptTime.isAfter(officeClose);
    }
}
