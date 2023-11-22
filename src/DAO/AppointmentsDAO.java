package DAO;

import Helper.DBConnecter;
import Utils.Timezones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import Model.AppointmentsModel;

public class AppointmentsDAO {

    static Connection connection = DBConnecter.getConnection();



    public static int addANewAppt(AppointmentsModel appt) throws SQLException{

        int autogeneratedID = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    ("INSERT INTO appointments (Title,Description,Location,Contact_ID,Type,Start,End,Customer_ID,User_ID) VALUES (?,?,?,?,?,?,?,?,?)"), PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1,appt.getApptTitle());
            ps.setString(2,appt.getApptDescription());
            ps.setString(3,appt.getApptLocation());
            ps.setInt(4,appt.getContactID());
            ps.setString(5,appt.getApptType());

            LocalDateTime startDateAndTime = LocalDateTime.of(appt.getApptStartDate(),appt.getApptStartTime());
            LocalDateTime endDateAndTime = LocalDateTime.of(appt.getApptEndDate(),appt.getApptEndTime());

            LocalDateTime utcStart = Timezones.utcConversion(startDateAndTime);
            LocalDateTime utcEnd = Timezones.utcConversion(endDateAndTime);

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

        LocalDateTime startDateAndTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateAndTime = LocalDateTime.of(endDate, endTime);

        LocalDateTime utcStart = Timezones.utcConversion(startDateAndTime);
        LocalDateTime utcEnd = Timezones.utcConversion(endDateAndTime);

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

    //get ALL appts
    public static ObservableList<AppointmentsModel> getAllAppointments() throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID");

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd= Timezones.localTimeConversion(utcEnd);

                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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

                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd= Timezones.localTimeConversion(utcEnd);


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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
                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd= Timezones.localTimeConversion(utcEnd);


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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

                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd = Timezones.localTimeConversion(utcEnd);


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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
    public static ObservableList<AppointmentsModel> getApptsByContact(int contactID) throws SQLException{
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE appointments.Contact_ID = ?");
        ps.setInt(1,contactID);

        try {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd= Timezones.localTimeConversion(utcEnd);


                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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

    //get number of appts filtered by type and month
    public static Map<String,Integer> getAllApptsByTypeAndMonth() throws SQLException{
        Map<String,Integer> total = new HashMap<>();

        PreparedStatement ps = connection.prepareStatement("SELECT Type, COUNT(*) as total FROM appointments WHERE MONTH(Start) = MONTH(CURRENT_DATE)  GROUP BY Type" );

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            total.put(rs.getString("Type"),rs.getInt("total"));
        }

        return total;
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
                LocalDateTime utcStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime localStart = Timezones.localTimeConversion(utcStart);

                LocalDateTime utcEnd = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime localEnd= Timezones.localTimeConversion(utcEnd);

                AppointmentsModel appt = new AppointmentsModel(
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        localStart.toLocalDate(),
                        localStart.toLocalTime(),
                        localEnd.toLocalDate(),
                        localEnd.toLocalTime(),
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
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as overlapping FROM appointments WHERE (Start BETWEEN ? AND ? OR End BETWEEN ? AND ?) AND Customer_ID=?");
        LocalDateTime startDateAndTime = LocalDateTime.of(appt.getApptStartDate(),appt.getApptStartTime());
        LocalDateTime endDateAndTime = LocalDateTime.of(appt.getApptEndDate(),appt.getApptEndTime());


        LocalDateTime utcStart = Timezones.utcConversion(startDateAndTime);
        LocalDateTime utcEnd = Timezones.utcConversion(endDateAndTime);

        ps.setTimestamp(1,Timestamp.valueOf(utcStart));
        ps.setTimestamp(2,Timestamp.valueOf(utcEnd));
        ps.setTimestamp(3,Timestamp.valueOf(utcStart));
        ps.setTimestamp(4,Timestamp.valueOf(utcEnd));
        ps.setInt(5,appt.getCustomerID());

        ResultSet rs = ps.executeQuery();
        if(rs.next() && rs.getInt("overlapping") > 0){
            return true;
        }
        return false;
    }
}