package DAO;

import Helper.DBConnecter;
import Model.CountriesModel;
import Model.DivisionsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DivisionsDAO {
    static Connection connection = DBConnecter.getConnection();

    //CRUD FUNCTIONS

    /**
     * Gets all the divisions from the database.
     *
     * @return a list of all the division objects.
     */
    public static ObservableList<DivisionsModel> readAllDivisions(){
        ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM first_level_divisions");
            ps.execute();
            ResultSet divs = ps.getResultSet();

            while (divs.next()){
                DivisionsModel division = new DivisionsModel(
                        divs.getInt("Division_ID"),
                        divs.getString("Division"),
                        divs.getInt("Country_ID"));
                divisions.add(division);
            }
            return (ObservableList<DivisionsModel>) divisions;
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            return null;
        }
    }

    /**
     * Gets a division ID from the database based on the division name passed.
     *
     * @param divisionName name of the division
     * @return a division model object that has the division name passed.
     * @throws SQLException if there is an error communicating with the databse.
     */
    public static DivisionsModel readDivID (String divisionName) throws SQLException {
       PreparedStatement ps = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Division=?");

        ps.setString(1, divisionName);

        try {
            ps.execute();
            ResultSet rs = ps.getResultSet();

            DivisionsModel division = null;
            while (rs.next()) {
                division = new DivisionsModel(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
            }
            return division;
        }catch (SQLException e){
            System.out.println("Cant get division ID");
            e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the division that has the ID specified in the parameter.
     * @param divisionID id of the division.
     * @return a Division model object that has the division id passed.
     * @throws SQLException if there is an error communicating with the database
     */
    public static DivisionsModel readDivByDivID(int divisionID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Division_ID = ?");
        ps.setInt(1,divisionID);

        try{
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next()){
                DivisionsModel division = new DivisionsModel(rs.getInt("Division_ID"),rs.getString("Division"), rs.getInt("Country_ID"));
                return division;
            }
        }catch (Exception e){
            System.out.println("Cant get division by ID");
            e.getMessage();
        }
        return null;
    }

    /**
     * This method gets the division based on the country name passed as a parameter.
     * @param countryName name of the country
     * @return a DivisionModel that corresponds with the coutnry name.
     * @throws SQLException if there is error with database communication
     */
    public static DivisionsModel readDivByCountry(String countryName) throws SQLException {
        CountriesModel country = new CountriesModel(CountriesDAO.readCountryID(countryName),countryName);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Country_ID=?");
        ps.setInt(1,country.getCountryID());

        try{
            ps.execute();
            ResultSet results = ps.getResultSet();

            if(results.next()){
                DivisionsModel division = new DivisionsModel(results.getInt("Division_ID"),results.getString("Division"),results.getInt("Country_ID"));
                return division;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method gets all the divisions with the country name passed in as a parameters. This will be used with comboboxes to select information.
     * @param country country for the divisions to be identified for.
     * @return a list of divisions so they can be listed in the selector combo box.
     */
    public static ObservableList<String> readDivisionsByCountry(String country){
        ObservableList<String> divisionsOptions = FXCollections.observableArrayList();

        DBConnecter.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Country_ID = ?");) {
            statement.setInt(1,CountriesDAO.readCountryID(country));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int divID = resultSet.getInt("Division_ID");
                String divisionName = DivisionsDAO.readDivByDivID(divID).getDivisionName();
                divisionsOptions.add(divisionName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return divisionsOptions;
    }
}
