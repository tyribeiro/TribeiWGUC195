package Model;

public class CustomersModel {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerDivision;
    private String customerCountry;
    private int customerDivisionID;

    public CustomersModel(int customerID, String customerName, String customerAddress, String customerPhone, String customerDivision, String customerCountry, int customerDivisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
        this.customerDivisionID = customerDivisionID;
    }

    //getters
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
}
