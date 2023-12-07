package DAO;

import Controller.appointmentSum;
import Helper.DBConnecter;
import Model.CustomersModel;
import Utils.Timezones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.spi.LocaleServiceProvider;

import Model.AppointmentsModel;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;

public class AppointmentsDAO {

    static Connection connection = DBConnecter.getConnection();



    public static int addANewAppt(AppointmentsModel appt) throws SQLException{

        int autogeneratedID = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    ("INSERT INTO appointments (Title,Description,Location,Contact_ID,Type,Start,End,Customer_ID,User_ID) VALUES (?,?,?,?,?,?,?,?,?)"), PreparedStatement.RETURN_GENERATED_KEYS);
            LocalDateTime utcStart = Timezones.localToUTC(appt.getStart());
            LocalDateTime utcEnd = Timezones.localToUTC(appt.getEnd());

            ps.setString(1,appt.getApptTitle());
            ps.setString(2,appt.getApptDescription());
            ps.setString(3,appt.getApptLocation());
            ps.setInt(4,appt.getContactID());
            ps.setString(5,appt.getApptType());

            ps.setTimestamp(6, Timestamp.valueOf(utcStart));
            ps.setTimestamp(7,Timestamp.valueOf(utcEnd));
            ps.setInt(8,appt.getCustomerID());
            ps.setInt(9,appt.getUserID());

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                autogeneratedID = generatedKeys.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return autogeneratedID;
    }

    public static boolean updateExistingAppt(int id, String title, String desc, String location, int contactID, String type, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int customerID, int userID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE appointments SET Title=?,Description=?,Location=?,Contact_ID=?,Type=?,Start=?,End=?,Customer_ID=?,User_ID=? WHERE Appointment_ID=?");
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, location);
        ps.setInt(4, contactID);
        ps.setString(5, type);


        LocalDateTime utcStart = Timezones.localToUTC(startDate.atTime(startTime));
        LocalDateTime utcEnd = Timezones.localToUTC(endDate.atTime(endTime));

        ps.setTimestamp(6,Timestamp.valueOf(utcStart));
        ps.setTimestamp(7,Timestamp.valueOf(utcEnd));
        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, id);

        try {
            ps.execute();
        } catch (Exception e) {
            System.out.println("cant update");
        }

        return false;
    }

    public static void deleteExistingAppt(int apptID) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");
        ps.setInt(1,apptID);
        ps.execute();
    }

    public static void deleteApptByCustomerId(int customerID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM appointments WHERE Customer_ID=?");
        ps.setInt(1, customerID);
        ps.executeUpdate();
    }

    //get ALL appts
    public static ObservableList<AppointmentsModel> getAllAppointments() throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID");

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());

                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart,
                        localEnd,
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                );
                appt.setApptID(rs.getInt("Appointment_ID"));
                appts.add(appt);
            }




            return appts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    //get all appts in current week
    public static ObservableList<AppointmentsModel> getApptsThisWeek() throws SQLException{
        ObservableList<AppointmentsModel> appts  = FXCollections.observableArrayList();

        LocalDate currentDate= LocalDate.now();
        LocalDate startWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDate endWeek = currentDate.with(DayOfWeek.SUNDAY);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts as contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Start BETWEEN ? AND ?");
        ps.setDate(1,Date.valueOf(startWeek));
        ps.setDate(2,Date.valueOf(endWeek));

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {

                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart,
                        localEnd,
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                );
                appt.setApptID(rs.getInt("Appointment_ID"));

                appts.add(appt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appts;
    }

    //get all appts in the current month
    public static ObservableList<AppointmentsModel> getApptThisMonth() throws SQLException{
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();
        LocalDate currentDate = LocalDate.now();
        LocalDate startMonth = currentDate.withDayOfMonth(1);
        LocalDate endMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Start BETWEEN ? AND ?");
        ps.setTimestamp(1,Timestamp.valueOf(startMonth.atStartOfDay()));
        ps.setTimestamp(2,Timestamp.valueOf(endMonth.atTime(23,59,59)));

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart,
                        localEnd,
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                );
                appt.setApptID(rs.getInt("Appointment_ID"));

                appts.add(appt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appts;

    }

    //get appt by ID
    public static AppointmentsModel getApptsByID(int apptID) throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE appointments.Appointment_ID = ?");
        ps.setInt(1, apptID);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart,
                        localEnd,
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                );

                appt.setApptID(rs.getInt("Appointment_ID"));
                appts.add(appt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appts.get(0);
    }

    //get all appts for a contact
    public static Map<String, String> getApptsByContact() throws SQLException {
        Map<String, String> schedules = new HashMap<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID ORDER BY contacts.Contact_Name, appointments.Start");

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());


                String contact = rs.getString("Contact_Name");
                int contactID = rs.getInt("Contact_ID");
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptType = rs.getString("Type");
                String description = rs.getString("Description");
                LocalDate startDate = rs.getTimestamp("Start").toLocalDateTime().toLocalDate();
                LocalDate endDate = rs.getTimestamp("End").toLocalDateTime().toLocalDate();
                LocalTime startTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalTime endTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                int customerID = rs.getInt("Customer_ID");

                String report = ("\n" + "Contact ID: " + contactID + "/nAppt ID: " + apptID + "/nAppointment Title: " + apptTitle
                        + "/nAppointment Type: " + apptType + "/nDescription: " + description
                        + "/nStart Date and Time: " + startDate + " " + startTime
                        + "/nEnd Date and Time: " + endDate + " " + endTime
                        + "/nCustomer ID: " + customerID);

                if (schedules.containsKey(contact)) {
                    schedules.put(contact, schedules.get(contact) + report);
                } else {
                    schedules.put(contact, report);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return schedules;
    }

    //get appts by contact name
    public static ObservableList<AppointmentsModel> getApptsByContactName(String contactName) throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments as appointments JOIN contacts as contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE contacts.Contact_Name =?");
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appts.add(new AppointmentsModel(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                ));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return appts;
    }

    public static ObservableList<AppointmentsModel> getApptsByCustomerID(int customerID) throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Customer_ID = ?");
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appts.add(new AppointmentsModel(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                ));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return appts;
    }


    // get appts in the next 15 min
    public static ObservableList<AppointmentsModel> apptsIn15() throws SQLException{
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime upcoming = current.plusMinutes(15);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Start BETWEEN ? AND ?");
        ps.setTimestamp(1,Timestamp.valueOf(current));
        ps.setTimestamp(2,Timestamp.valueOf(upcoming));

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                Timestamp utcStart = rs.getTimestamp("Start");
                LocalDateTime localStart = Timezones.utcToLocal(utcStart.toLocalDateTime());

                Timestamp utcEnd = rs.getTimestamp("End");
                LocalDateTime localEnd = Timezones.utcToLocal(utcEnd.toLocalDateTime());

                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart,
                        localEnd,
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name")
                );

                appts.add(appt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appts;
    }

    public static boolean overlappingAppts(AppointmentsModel appt) throws  SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as overlapping FROM appointments WHERE ((Start <= ? AND End >= ?) OR (Start <= ? AND End >= ?) OR (Start >= ? AND End <= ?)) AND (Appointment_ID <> ? OR ? = null)");

        LocalDateTime utcStart = Timezones.localToUTC(appt.getStart());
        LocalDateTime utcEnd = Timezones.localToUTC(appt.getEnd());

        ps.setTimestamp(1,Timestamp.valueOf(utcStart));
        ps.setTimestamp(2,Timestamp.valueOf(utcEnd));
        ps.setTimestamp(3,Timestamp.valueOf(utcStart));
        ps.setTimestamp(4,Timestamp.valueOf(utcEnd));
        ps.setTimestamp(5, Timestamp.valueOf(utcStart));
        ps.setTimestamp(6, Timestamp.valueOf(utcEnd));
        ps.setInt(7, appt.getApptID());
        ps.setInt(8, appt.getApptID());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("overlapping: " + rs.getInt("overlapping"));
            return rs.getInt("overlapping") > 0;
        }
        return false;
    }


    public static ObservableList<AppointmentsModel> getApptsByTypeMonth(String type, String month) throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ?");
            ps.setString(1, type);
            ps.setString(2, month);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"),
                        ContactsDAO.getContactById(rs.getInt("Contact_ID")).getContactName()
                );
                appts.add(appt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appts;

    }

    public static ObservableList<AppointmentsModel> apptsType(String type) throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Type=?");
        ps.setString(1, type);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            AppointmentsModel appt = new AppointmentsModel(
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"));
            appts.add(appt);
        }
        return appts;
    }


    public static ObservableList<AppointmentsModel> apptsMonth() throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID GROUP BY MONTH(Start)");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            AppointmentsModel appt = new AppointmentsModel(
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"));
            appts.add(appt);
        }
        return appts;
    }

    public static String getTotalApptsByCustomer() {
        String report = "";
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Customer_ID, COUNT(*) as count FROM appointments GROUP BY Customer_ID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                report = report + rs.getInt("Customer_ID") + ": " + rs.getInt("count") + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }
}