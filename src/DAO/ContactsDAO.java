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

    /**
     * This method gets all the contacts from the database.
     *
     * @return a list of contacts with all the information stored with it.
     * @throws SQLException when there is an error fetching the data.
     */
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

    /**
     * This method gets the Contact ID for the contact passed as a parameter.
     *
     * @param contact_name name of the contact
     * @return a integer of the contact ID.
     * @throws SQLException if there is an error when fetching data from the database
     */

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

    /**
     * this method gets the whole contact object of the contact with the name passed.
     * @param name name of the contact that user wants to read
     * @return a ContactModel object that has all of the details for the contact
     * @throws SQLException if there is an error getting the data from the database
     */
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

    /**
     * This method gets a contact based on the ID passed as a parameter.
     * @param id the id of the contact that needs to be fetched from database.
     * @return a contact object that has the contacts details/
     * @throws SQLException if there is an error fetching the data from the database.
     */
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

    /**
     * This method gets the names of all the contacts in the database.
     * @return a list of all the names of all the contacts in the database.
     * @throws RuntimeException if there is an error getting the information.
     */
    public static ObservableList<String> getAllContactNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Contact_Name FROM contacts");
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                names.add(rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }
}
