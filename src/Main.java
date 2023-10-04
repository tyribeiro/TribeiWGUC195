import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Helper.DBConnecter;
import Model.CustomersModel;

public class Main {
    public static void main(String[] args) throws Exception {
        DBConnecter.getConnection();

        System.out.println(        CustomersDAO.readCustomers());


        DBConnecter.closeConnection();

    }
}

