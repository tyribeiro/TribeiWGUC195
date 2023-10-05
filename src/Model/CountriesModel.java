package Model;

public class CountriesModel {
    private int countryID;
    private String countryName;

    @Override
    public String toString() {
        return "CountriesModel{" +
                "countryID=" + countryID +
                ", countryName='" + countryName + '\'' +
                '}';
    }

    //construtore
    public CountriesModel(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    //getters
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
