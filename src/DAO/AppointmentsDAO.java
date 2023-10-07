package DAO;

import Helper.DBConnecter;
import Model.AppointmentsModel;
import Model.ContactsModel;

import javax.sound.sampled.FloatControl;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.LongConsumer;

public class AppointmentsDAO {
    static Connection connection = DBConnecter.getConnection();
    //read functions
    public static List<AppointmentsModel> readAllAppts() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts as contacts ON appointments.Contact_ID=contacts.Contact_ID");
       List appts = new ArrayList();

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }

            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppointmentsModel> readAllAppointmentMonths()throws SQLException{

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Start < ? AND Start > ?");

        ps.setDate(1,Date.valueOf(String.valueOf(LocalDateTime.now())));
        ps.setDate(2, Date.valueOf(Date.valueOf(String.valueOf(LocalDateTime.now().minusDays(30))).toLocalDate()));

        List appts = new ArrayList();
        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }
            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppointmentsModel> readAllApptWeeks() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appt INNER JOIN contacts AS ctcs ON appt.Contact_ID=ctcs.Contact_ID WHERE Start < ? AND Start > ?");

        ps.setDate(1, Date.valueOf(String.valueOf(LocalDateTime.now())));
        ps.setDate(2, Date.valueOf(String.valueOf(LocalDateTime.now().minusDays(7))));
        List appts = new ArrayList();

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }
            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppointmentsModel> readApptsByCustomerID(int CustomerID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appt INNER JOIN contacts AS ctcs ON appt.Contact_ID=ctcs.Contact_ID WHERE Customer_ID=?");
        ps.setInt(1,CustomerID);
        List appts = new ArrayList();

        try{
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }
            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static List<AppointmentsModel> readApptsByContactID(int ContactID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appts INNER JOIN contacts AS ctcs ON appts.Contact_ID=ctcs.Contact_ID WHERE appts.Contact_ID=?");
        ps.setInt(1,ContactID);
        List appts = new ArrayList();

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }
            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppointmentsModel> readApptsByApptID(int ApptID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appts INNER JOIN contacts AS ctcs ON appts.Contact_ID=ctcs.Contact_ID WHERE appts.Appointment_ID=?");
        ps.setInt(1,ApptID);

        List appts = new ArrayList();

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                AppointmentsModel appt = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appts.add(appt);
            }
            return appts;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createNewAppt(String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime start, LocalDateTime end, int customerID,int userID,int contactID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO appointments (Title,Description,Location,Type,Start,End,Customer_ID,User_ID,Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setString(1,apptTitle);
        ps.setString(2,apptDescription);
        ps.setString(3,apptLocation);
        ps.setString(4,apptType);
        ps.setTimestamp(5,Timestamp.valueOf(start));
        ps.setTimestamp(6,Timestamp.valueOf(end));
        ps.setInt(7,customerID);
        ps.setInt(8,userID);
        ps.setInt(9,contactID);

        try {
            ps.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateExistingAppt(int apptID, int customerID, int userID, String apptTitle, String apptDescription, String apptLocation, String contactName, LocalDateTime start, LocalDateTime end) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE appointments SET Customer_ID= ?,User_ID= ?,Title= ?,Description= ?,Location= ?,Contact_ID= ?,Start= ?,End= ? WHERE Appointment_ID = ?");
        ps.setInt(1,customerID);
        ps.setInt(2,userID);
        ps.setString(3,apptTitle);
        ps.setString(4, apptDescription);
        ps.setString(5,apptLocation);
        ps.setInt(6, ContactsDAO.readContactID(contactName));
        ps.setTimestamp(7,Timestamp.valueOf(start));
        ps.setTimestamp(8,Timestamp.valueOf(end));
        ps.setInt(9, apptID);

        try{
            ps.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteExistingAppt(int apptID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE from appointments WHERE Appointment_ID=?");
        ps.setInt(1,apptID);

     try{
         ps.execute();
         return true;
     }catch (Exception e){
         e.printStackTrace();
         return false;
     }
    }


}
