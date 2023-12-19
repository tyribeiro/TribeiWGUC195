package Model;

/**
 * This class is for the Customer Object and stores all key information for a customer.
 * Includes constructors, getters , setters and to string method.
 */

public class CustomersModel {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerDivision;
    private String customerCountry;
    private int customerDivisionID;

    private String postalCode;

    /**
     * constructor
     *
     * @param customerID         ID of the customer
     * @param customerName       name of the customer
     * @param customerAddress    address of the customer
     * @param customerPhone      phone number of the customer
     * @param customerDivision   division of the customer
     * @param customerDivisionID division ID of the customer
     * @param postal             postal  of the customers address
     * @param customerCountry    country of the customer
     */
    public CustomersModel(int customerID, String customerName, String customerAddress, String customerPhone, String customerDivision, int customerDivisionID, String postal, String customerCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;

        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.customerDivisionID = customerDivisionID;
        this.postalCode = postal;
        this.customerCountry = customerCountry;
    }

    /**
     * Setters and getters

     */
    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerDivision() {
        return customerDivision;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    //setters
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public void setCustomerDivisionID(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method that builds a string representation of the customer
     * @return a string with the customers information
     */
    @Override
    public  String toString() {
        return "CustomersDAO {" + "CustomerID: " + customerID + "     CustomerName: " + customerName + '\'';
    }
}
