package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentsModel {
    private int apptID;
    private String apptTitle;
    private String apptDescription;

    private String apptLocation;
    private String apptType;

    private LocalDate apptStartDate;
    private LocalDate apptEndDate;
    private LocalDateTime apptStartTime;
    private LocalDateTime apptEndTime;

    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    public AppointmentsModel(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDate apptStartDate, LocalDate apptEndDate, LocalDateTime apptStartTime, LocalDateTime apptEndTime, int customerID, int userID, int contactID, String contactName) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDate = apptStartDate;
        this.apptEndDate = apptEndDate;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    //getters
    public int getApptID() {
        return apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public String getApptType() {
        return apptType;
    }

    public LocalDate getApptStartDate() {
        return apptStartDate;
    }

    public LocalDate getApptEndDate() {
        return apptEndDate;
    }

    public LocalDateTime getApptStartTime() {
        return apptStartTime;
    }

    public LocalDateTime getApptEndTime() {
        return apptEndTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }


    //setters
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public void setApptStartDate(LocalDate apptStartDate) {
        this.apptStartDate = apptStartDate;
    }

    public void setApptEndDate(LocalDate apptEndDate) {
        this.apptEndDate = apptEndDate;
    }

    public void setApptStartTime(LocalDateTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public void setApptEndTime(LocalDateTime apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
