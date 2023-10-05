package DAO;

import Helper.DBConnecter;
import Model.CountriesModel;
import Model.DivisionsModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountriesDAO {
    static Connection connection = DBConnecter.getConnection();

    public static List<CountriesModel> readAllCountries(){
        List<CountriesModel> countries = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM countries");
            ps.execute();
            ResultSet countriesResult = ps.getResultSet();

            while (countriesResult.next()){
                CountriesModel country = new CountriesModel(
                        countriesResult.getInt("Country_ID"), countriesResult.getString("Country"));
                    countries.add(country);
            }
            return  countries;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

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
}
