package DAO;

import Helper.DBConnecter;
import Model.AppointmentsModel;
import Model.CustomersModel;
import Model.DivisionsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO {
    static Connection connection = DBConnecter.getConnection();

    //CRUD FUNCTIONS

    /**
     * Adds a new customer in the database
     *
     * @param customerName       Name of the customer
     * @param customerAddress    Address of the customer
     * @param customerPhone      Phone number of the customer
     * @param customerDivisionID Dvisiom ID of the customer
     * @param postal             Postal code of the customer
     * @param customerCountry    Country of the customer
     * @return true if no error occured and customer was added, false if error occurs.
     * @throws SQLException If there is an error when communicating with the database
     */
    public static boolean createCustomer(String customerName, String customerAddress, String customerPhone, int customerDivisionID, String postal, String customerCountry) throws SQLException {
        DivisionsModel division = DivisionsDAO.readDivByDivID(customerDivisionID);

        PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Phone, Division_ID, Postal_Code) VALUES (?,?,?,?,?)");
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPhone);
        ps.setInt(4, division.getDivisionID());
        ps.setString(5, postal);
        ps.setString(6, customerCountry);
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

    /**
     * This method gets all the customers from the database
     *
     * @return a list of the customers from the database
     * @throws Exception if there is an error communciating with database
     */
    public static ObservableList<CustomersModel> readCustomers() throws Exception {
        ObservableList customers = FXCollections.observableArrayList();

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
                        rs.getInt("Division_ID"),
                        rs.getString("Postal_Code"),
                        rs.getString("Country")
                );
                customers.add(customer);
            }
            return customers;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the customer who has the ID passed into as a parameter.
     * @param id id of the customer
     * @return a Customer object with all the customer information.
     */
    public static CustomersModel readCustomerByID(int id) {
        CustomersModel customer = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers AS customers INNER JOIN first_level_divisions AS divisions ON customers.Division_ID = divisions.Division_ID INNER JOIN countries AS countries ON countries.Country_ID=divisions.Country_ID Where Customer_ID=?");
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                customer = new CustomersModel(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        DivisionsDAO.readDivByDivID(rs.getInt("Division_ID")).getDivisionName(),
                        rs.getInt("Division_ID"),
                        rs.getString("Postal_Code"),
                        rs.getString("Country")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    /**
     * This method updates the information of an existing customer.
     * @param customerID id of the customer to update information
     * @param customerName updated name of the customer
     * @param customerPhone updated phone number of the customer
     * @param customerAddress updated address of the customer
     * @param postal updated postal code of the customer
     * @param divisionName updated division name of the customer
     * @return true if the customer was successfuly updated in the database, false it wasn't successful.
     * @throws SQLException if there is an error communicating with the database.
     */
    public static boolean updateCustomer(int customerID, String customerName, String customerPhone, String customerAddress, String postal, String divisionName) throws SQLException {
        DivisionsModel division = DivisionsDAO.readDivID(divisionName);

        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE customers SET Customer_Name=?,Phone=?,Address=?,Postal_Code=?,Division_ID=? WHERE Customer_ID=?");

            ps.setString(1,customerName);
            ps.setString(2,customerPhone);
            ps.setString(3,customerAddress);
            ps.setString(4,postal);
            ps.setInt(5,division.getDivisionID());
            ps.setInt(6,customerID);
            return ps.executeUpdate() > 0;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method deletes the customer from the database who has the passed in customer ID.
     * @param customerID id of the customer to delete.
     * @return true if customer was successfuly deleted from the database, false if it wasn't.
     * @throws SQLException if there is an error communicating with the database.
     */
    public static boolean deleteCustomer(int customerID) throws SQLException {
        AppointmentsDAO.deleteApptByCustomerId(customerID);

        PreparedStatement ps = connection.prepareStatement("DELETE from customers WHERE Customer_ID=?");
        ps.setInt(1,customerID);

        try{
            ps.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
