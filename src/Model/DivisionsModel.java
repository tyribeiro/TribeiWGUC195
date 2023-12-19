package Model;

/**
 * This class is for the Division Object and stores all key information for a division.
 * Includes constructors, getters , setters and to string method.
 */
public class DivisionsModel {
    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Constructor
     *
     * @param divisionID   id of division
     * @param divisionName name of division
     * @param countryID    id of the country
     */
    public DivisionsModel(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Setters and getters
     */
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

    /**
     * Method that builds a string representation of the division
     * @return a string with the division's information
     * @return
     */
    @Override
    public String toString() {
        return "DivisionsModel{" +
                "divisionID=" + divisionID +
                ", divisionName='" + divisionName + '\'' +
                ", countryID=" + countryID +
                '}';
    }
}
