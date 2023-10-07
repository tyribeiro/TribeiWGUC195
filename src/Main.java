import DAO.AppointmentsDAO;
import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Helper.DBConnecter;
import Model.AppointmentsModel;
import Model.CustomersModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        DBConnecter.getConnection();
        System.out.println(AppointmentsDAO.createNewAppt("Consultation","New client consult","Kirkalnd","Consult",LocalDateTime.of(2023,10,20,13,15,0),LocalDateTime.of(2023,1,2,13,45,0),130,1,5));
        DBConnecter.closeConnection();

    }
}

