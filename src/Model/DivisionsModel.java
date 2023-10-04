package Model;

public class DivisionsModel {
    private int divisionID;
    private String divisionName;
    private int countryID;

    //constructor
    public DivisionsModel(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    //getters
    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryID() {
        return countryID;
    }

    //setters
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return "DivisionsModel{" +
                "divisionID=" + divisionID +
                ", divisionName='" + divisionName + '\'' +
                ", countryID=" + countryID +
                '}';
    }
}
