package DAO;

import Helper.DBConnecter;
import Model.AppointmentsModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentsDAO {
    static Connection connection = DBConnecter.getConnection();
    //read functions
    public static List<AppointmentsModel> readAllAppointments() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts as contacts ON appointments.Contact_ID=contacts.Contact_ID");
       List appointments = new ArrayList();
        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                AppointmentsModel appointment = new AppointmentsModel(rs.getInt("Appointment_ID"),rs.getString("Title"),rs.getString("Description"),rs.getString("Location"),rs.getString("Type"),rs.getDate("Start").toLocalDate(),rs.getDate("End").toLocalDate(),rs.getTimestamp("Start").toLocalDateTime(),rs.getTimestamp("End").toLocalDateTime(),rs.getInt("Customer_ID"),rs.getInt("User_ID"),rs.getInt("Contact_ID"),rs.getString("Contact_Name"));
                appointments.add(appointment);
            }

            return appointments;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppointmentsModel> readAllMonths()throws SQLException{
        List appointments = new ArrayList();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS appointments INNER JOIN contacts AS contacts ON appointments.Contact_ID=contacts.Contact_ID WHERE Start < ? AND Start > ?");

        ps.setDate(1,java.sql.Date.valueOf(String.valueOf(LocalDateTime.now())));
        ps.setDate(2, Date.valueOf(Date.valueOf(String.valueOf(LocalDateTime.now().minusDays(30))).toLocalDate()));

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();
        }
    }
}
