package DAO;

import Helper.DBConnecter;
import Model.CustomersModel;
import Model.UsersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersDAO {
    static Connection connection = DBConnecter.getConnection();

    /**
     * This method gets all the users in the database with all the information from them.
     *
     * @return a list of user model objects.
     * @throws SQLException if there is an error communicating with the database.
     */
    public static ObservableList<UsersModel> readAllUsers() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");

        ObservableList users = FXCollections.observableArrayList();
        try {
            ps.execute();
            ResultSet result = ps.getResultSet();

            while (result.next()){
                UsersModel user = new UsersModel(
                        result.getInt("User_ID"),
                        result.getString("User_Name"),
                        result.getString("Password")
                );
                users.add(user);
            }
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets a user object based on the user ID passed in.
     *
     * @param id of the user to get information for.
     * @return a User model object that has the ID passed in as a parameter.
     */
    public static UsersModel getUserByID(int id) {
        UsersModel user = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE User_ID=?");
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                user = new UsersModel(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    /**
     * This method validates the username and password entered by the application user. It checks with the database
     * to see if the user credentials exists and matches the one entered by the user.
     * @param username username entered by user in the login page.
     * @param password password entered by user in the login page.
     * @return True if the username and password was found in the database and is valid, false otherwise.
     * @throws SQLException if there is an error communicating with the database.
     */
    public static boolean validateUsernamePassword(String username, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE User_Name=? AND Password =?");
        ps.setString(1,username);
        ps.setString(2,password);

        try {
            ps.execute();
            ResultSet result = ps.getResultSet();
           if(result.next()){
               return true;
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
