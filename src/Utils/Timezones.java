package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class has helpful methods for working with time conversions for display in the application and storage in the database.
 */
public class Timezones {
    /**
     * This method converts the local time of the user to UTC to store the time in the database.
     *
     * @param localTime users local time
     * @return UTC time of the local time
     */
    public static LocalDateTime localToUTC(LocalDateTime localTime) {
        return ZonedDateTime.of(localTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    /**
     * This method converts the UTC time back into local time to display for the user.
     * @param utcTime UTC time
     * @return the local time of the UTC time
     */
    public static LocalDateTime utcToLocal(LocalDateTime utcTime) {
        return ZonedDateTime.of(utcTime, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * This method converts the time into Eastern Time to check for valid appointment times within the business hours.
     * @param localTime time to convert to Eastern time
     * @return eastern time of the local time.
     */
    public static LocalDateTime ETConversion(LocalDateTime localTime) {
        return localTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();

    }

    /**
     * This method gets the business hours to populate the combo box drop down menu in increments of 30 minutes, it sets the business ohurs to 8am-10pm
     * @return observable list of the business hours in increments of 30 minutes
     */
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
