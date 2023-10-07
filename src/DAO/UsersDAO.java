package DAO;

import Helper.DBConnecter;
import Model.UsersModel;

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

    public static List<UsersModel> readAllUsers() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");

        List users = new ArrayList();
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
