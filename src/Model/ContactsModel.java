package Model;

public class ContactsModel {
    private String contactName;
    private int contactID;
    private String contactEmail;

    //constructor
    public ContactsModel(String contactName, int contactID, String contactEmail) {
        this.contactName = contactName;
        this.contactID = contactID;
        this.contactEmail = contactEmail;
    }


    //getters
    public String getContactName() {
        return contactName;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    //setters
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
