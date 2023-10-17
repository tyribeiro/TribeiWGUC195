package DAO;

import Helper.DBConnecter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Model.AppointmentsModel;

public class AppointmentsDAO {

    static Connection connection = DBConnecter.getConnection();

    public static ObservableList<AppointmentsModel> getAllAppointments() throws SQLException {
        ObservableList<AppointmentsModel> appts = FXCollections.observableArrayList();

        LocalDate startRange = LocalDate.now();
        LocalDate endRange = LocalDate.now().plusMonths(1);
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID");

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()){
                System.out.println("inside while loop appt dao");
                AppointmentsModel appt = new AppointmentsModel(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime().toLocalTime(),
                        rs.getTimestamp("End").toLocalDateTime().toLocalTime(),
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


}