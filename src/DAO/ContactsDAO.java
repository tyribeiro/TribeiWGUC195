package DAO;

import Helper.DBConnecter;
import Model.ContactsModel;
import Model.CustomersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO {
    static Connection connection = DBConnecter.getConnection();
    public static ObservableList<ContactsModel> readAllContacts() throws SQLException {
        ObservableList allContacts = FXCollections.observableArrayList();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM contacts");

        try{
            ps.execute();
            ResultSet results = ps.getResultSet();

            while(results.next()){
                ContactsModel contact = new ContactsModel(
                        results.getString("Contact_Name"),
                        results.getInt("Contact_ID"),
                        results.getString("Email")
                );
                allContacts.add(contact);
            }
            return allContacts;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int readContactID(String contact_name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, contact_name);

        try {
            ps.execute();
            ResultSet result = ps.getResultSet();

            if(result.next()){
                ContactsModel contact = new ContactsModel(
                        result.getString("Contact_Name"),
                        result.getInt("Contact_ID"),
                        result.getString("Email")
                );
                return contact.getContactID();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static ContactsModel getContactByName(String name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, name);
        ContactsModel contact = null;
        try {
            ps.execute();
            ResultSet results = ps.getResultSet();

            if (results.next()) {
                contact = new ContactsModel(
                        results.getString("Contact_Name"),
                        results.getInt("Contact_ID"),
                        results.getString("Email")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contact;
    }

    public static ContactsModel getContactById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM contacts WHERE Contact_ID = ?");
        ps.setInt(1, id);
        ContactsModel contact = null;
        try {
            ps.execute();
            ResultSet results = ps.getResultSet();

            if (results.next()) {
                contact = new ContactsModel(
                        results.getString("Contact_Name"),
                        results.getInt("Contact_ID"),
                        results.getString("Email")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contact;
    }
}
