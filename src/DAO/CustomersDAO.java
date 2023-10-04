package DAO;

import Helper.DBConnecter;
import Model.CustomersModel;
import Model.DivisionsModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO {
    static Connection connection = DBConnecter.getConnection();

    //CRUD FUNCTIONS
    public static boolean createCustomer(String customerName, String customerAddress, String customerPhone, int customerDivisionID, String postal) throws SQLException {
        DivisionsModel division = DivisionsDAO.readDivByDivID(customerDivisionID);

        PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Phone, Division_ID, Postal_Code) VALUES (?,?,?,?,?)");
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPhone);
        ps.setInt(4, division.getDivisionID());
        ps.setString(5, postal);
        try {
            ps.execute();
            if (ps.getUpdateCount() > 0) {
                System.out.println("customer added");
            } else {
                System.out.println("no customer added");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            System.out.println("Error adding customer");
            return false;
        }
    }
    public static List<CustomersModel> readCustomers() throws Exception {
        List customers = new ArrayList();

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers AS customers INNER JOIN first_level_divisions AS divisions ON customers.Division_ID = divisions.Division_ID INNER JOIN countries AS countries ON countries.Country_ID=divisions.Country_ID");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                CustomersModel customer = new CustomersModel(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        DivisionsDAO.readDivByDivID(rs.getInt("Division_ID")).getDivisionName(),
                        rs.getString("Country"),
                        rs.getInt("Division_ID"),
                        rs.getString("Postal_Code")
                );
                customers.add(customer);
            }
            return customers;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateCustomer(int customerID, String customerName, String customerPhone, String customerAddress, String postal, String divisionName) throws SQLException {
        DivisionsModel divisionID = DivisionsDAO.readDivID(divisionName);


    }

}
