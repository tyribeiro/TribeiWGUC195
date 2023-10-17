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

    private LocalDateTime apptStart;
    private LocalDateTime apptEnd;

    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    public AppointmentsModel(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, int userID, int contactID, String contactName) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
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

    public LocalDateTime getApptStart() {
        return apptStart;
    }

    public LocalDateTime getApptEnd() {
        return apptEnd;
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

    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
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
        return "Appointments: " + apptID + "   --- Appt Title: "  + apptType + "   ---Location: " + apptLocation + "---- Start Date and time" + getApptStart() + "----Appt end = " + getApptEnd();
    }
}
