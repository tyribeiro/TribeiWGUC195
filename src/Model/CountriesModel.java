package Model;

/**
 * This class is for the Country Object and stores all key information for a country.
 * Includes constructors, getters , setters and to string method.
 */
public class CountriesModel {
    private int countryID;
    private String countryName;

    /**
     * Constructors
     * @param countryID
     * @param countryName
     */
    public CountriesModel(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * This method is so that we can print out information of the country object.
     *
     * @return a string with information of the country object
     */
    @Override
    public String toString() {
        return "CountriesModel{" +
                "countryID=" + countryID +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    /**
     * Setters and getters
     */
    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    //setters
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
