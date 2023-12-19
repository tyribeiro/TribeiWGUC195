package DAO;

import Helper.DBConnecter;
import Model.CountriesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class controls all the communication with the SQL database to perform CRUD functions for anything related to the countries in the database.
 */

public class CountriesDAO {
    static Connection connection = DBConnecter.getConnection();

    /**
     * This method gets all the countries, their name and ID, from the database .
     *
     * @return a list containing all the countries.
     */
    public static ObservableList<CountriesModel> readAllCountries(){
        ObservableList<CountriesModel> countries = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM countries");
            ps.execute();
            ResultSet countriesResult = ps.getResultSet();

            while (countriesResult.next()){
                CountriesModel country = new CountriesModel(
                        countriesResult.getInt("Country_ID"), countriesResult.getString("Country"));
                    countries.add(country);
            }
            return (ObservableList<CountriesModel>) countries;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the country id for the country passed in as a parameter.
     *
     * @param countryName Name of the country that the program needs the ID for.
     * @return a integer value of the country ID
     * @throws SQLException if there is an error communicating with the database.
     */
    public static int readCountryID(String countryName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT Country_ID FROM countries WHERE Country=?");
        ps.setString(1,countryName);
        try {
            ps.execute();
            ResultSet result = ps.getResultSet();

            if (result.next()){
                CountriesModel country = new CountriesModel(result.getInt("Country_ID"),countryName);
                return country.getCountryID();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * This method gets all the Country Names from the database to populate into the combo box selection drop down.
     * @return a list of strings of the country names from the database.
     */
    public static ObservableList<String> readCountires(){
        ObservableList<String> countryOptions = FXCollections.observableArrayList();

        DBConnecter.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries");) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String countryName = resultSet.getString("Country");
                countryOptions.add(countryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryOptions;
    }
}
