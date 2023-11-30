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

    private LocalDateTime start;
    private LocalDateTime end;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;


    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;


    public AppointmentsModel(String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID, String contactName) {
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public AppointmentsModel(int aptID, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID, String contactName) {
        this.apptID = aptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.start = start;
        this.end = end;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDate getStartDate() {
        return start.toLocalDate();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public LocalDate getEndDate() {
        return end.toLocalDate();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString(){
        return "Appointments: " + apptID + "--- APPT TYPE: " + apptType + "   --- Appt Title: " + apptTitle + "   ---Location: " + apptLocation + "---- Start Date " + start.toLocalDate() + "---Start Time  " + start.toLocalTime() + "----  Appt End date = " + end.toLocalDate() + "---Appt End Time  " + end.toLocalTime();

    }
}
