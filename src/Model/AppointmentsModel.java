package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentsModel {
    private int apptID;
    private String apptTitle;
    private String apptDescription;

    private String apptLocation;
    private String apptType;

    private LocalDate apptStartDate;
    private LocalDate apptEndDate;

    private LocalTime apptStartTime;
    private LocalTime apptEndTime;

    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    public AppointmentsModel(String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDate apptStartDate,LocalTime apptStartTime, LocalDate apptEndDate,LocalTime apptEndTime, int customerID, int userID, int contactID, String contactName) {
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDate = apptStartDate;
        this.apptStartTime = apptStartTime;
        this.apptEndDate = apptEndDate;
        this.apptEndTime = apptEndTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public AppointmentsModel(int aptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDate apptStartDate, LocalTime apptStartTime, LocalDate apptEndDate, LocalTime apptEndTime, int customerID, int userID, int contactID, String contactName) {
        this.apptID = aptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDate = apptStartDate;
        this.apptStartTime = apptStartTime;
        this.apptEndDate = apptEndDate;
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

    public LocalTime getApptStartTime() {
        return apptStartTime;
    }


    public LocalTime getApptEndTime() {
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
    public void setApptStartTime(LocalTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public void setApptEndDate(LocalDate apptEndDate) { this.apptEndDate = apptEndDate;
    }
    public void setApptEndTime(LocalTime apptStartTime) {
        this.apptStartTime = apptStartTime;
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

    @Override
    public String toString(){
        return "Appointments: " + apptID + "--- APPT TYPE: " + apptType + "   --- Appt Title: "  + apptTitle + "   ---Location: " + apptLocation + "---- Start Date " + getApptStartDate() + "---Start Time  " + getApptStartTime() + "----  Appt End date = " + getApptEndDate() + "---Appt End Time  " + getApptEndTime();
    }
}
