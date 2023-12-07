package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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


    public static ObservableList<String> getBusinessHours() {
        // time zones for EST  time and for users local time zone to populate drop down menus
        ZoneId estZone = ZoneId.of("America/New_York");
        ZoneId localZone = ZoneId.systemDefault();

        // business hours in EST as defined in project.
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        //Observable list for the business hours after its converted
        ObservableList<String> businessHours = FXCollections.observableArrayList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

        //converting EST to local time in 30min increments
        while (!start.isAfter(end)) {
            ZonedDateTime est = ZonedDateTime.of(LocalDate.now(), start, estZone);
            ZonedDateTime local = est.withZoneSameInstant(localZone);

            businessHours.add(local.format(format));

            start = start.plusMinutes(30);
        }
        return businessHours;
    }
}
