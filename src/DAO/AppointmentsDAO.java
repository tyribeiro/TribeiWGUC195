package DAO;

import Helper.DBConnecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.server.RemoteRef;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

import Model.AppointmentsModel;
import jdk.jshell.PersistentSnippet;

public class AppointmentsDAO {

    static Connection connection = DBConnecter.getConnection();



    public static void addANewAppt(AppointmentsModel appt) throws SQLException{
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO appointments (Appointment_ID, Title,Description,Location,Contact,Type,Start,End,Customer_ID,User_ID) VALUES (?,?,?,?,?,?,?,?,?,?)");

            ps.setInt(1,appt.getApptID());
            ps.setString(2,appt.getApptTitle());
            ps.setString(3,appt.getApptDescription());
            ps.setString(4,appt.getApptLocation());
            ps.setString(5,appt.getContactName());
            ps.setString(6,appt.getApptType());
            ps.setTimestamp(7, Timestamp.valueOf(appt.getApptStart()));
            ps.setTimestamp(8,Timestamp.valueOf(appt.getApptEnd()));
            ps.setInt(9,appt.getCustomerID());
            ps.setInt(10,appt.getUserID());

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateExistingAppt(AppointmentsModel appt) throws  SQLException{
        PreparedStatement ps = connection.prepareStatement("UPDATE appointments SET Title=?,Description=?,Location=?,Contact=?,Type=?,Start=?,Emd=?,Customer_ID=?,User_ID=? WHERE Appointment_ID=?");
        ps.setString(1,appt.getApptTitle());
        ps.setString(2,appt.getApptDescription());
        ps.setString(3, appt.getApptLocation());
        ps.setString(4,appt.getContactName());
        ps.setString(5, appt.getApptType());
        ps.setTimestamp(6,Timestamp.valueOf(appt.getApptStart()));
        ps.setTimestamp(7,Timestamp.valueOf(appt.getApptEnd()));
        ps.setInt(8,appt.getCustomerID());
        ps.setInt(9,appt.getUserID());
        ps.setInt(10,appt.getApptID());

        ps.execute();
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
                AppointmentsModel appt = new AppointmentsModel(
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
                );
                System.out.println(appt);
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
                AppointmentsModel appt = new AppointmentsModel(
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
                );
                System.out.println(appt);
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
        ps.setDate(1,Date.valueOf(startMonth));
        ps.setDate(2,Date.valueOf(endMonth));

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                AppointmentsModel appt = new AppointmentsModel(
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
                );
                System.out.println(appt);
                appts.add(appt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appts;

    }

    //get all appts for a contact
    public static ObservableList<AppointmentsModel> getApptsByContact(int contactID) throws SQLException{
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE appointments.Contact_ID = ?");
        ps.setInt(1,contactID);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                AppointmentsModel appt = new AppointmentsModel(
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
                );
                System.out.println(appt);
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
                AppointmentsModel appt = new AppointmentsModel(
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
                );
                System.out.println(appt);
                appts.add(appt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appts;
    }

    public static boolean overlappingAppts(AppointmentsModel appt) throws  SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as overlapping FROM appointments WHERE (Start BETWEEN ? AND ? OR End BETWEEN ? AND ?) AND Customer_ID=?");
        ps.setTimestamp(1,Timestamp.valueOf(appt.getApptStart()));
        ps.setTimestamp(2,Timestamp.valueOf(appt.getApptEnd()));
        ps.setTimestamp(3,Timestamp.valueOf(appt.getApptStart()));
        ps.setTimestamp(4,Timestamp.valueOf(appt.getApptEnd()));
        ps.setInt(5,appt.getCustomerID());

        ResultSet rs = ps.executeQuery();
        if(rs.next() && rs.getInt("overlapping") > 0){
            return true;
        }
        return false;
    }
}