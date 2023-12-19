package Model;

/**
 * This class is for the Contact Object and stores all key information for a contact.
 * Includes constructors, getters , setters and to string method.
 */
public class ContactsModel {
    private String contactName;
    private int contactID;
    private String contactEmail;

    /**
     * Constructor
     *
     * @param contactName  name of the contact
     * @param contactID    id of the contact
     * @param contactEmail email of the contact
     */
    public ContactsModel(String contactName, int contactID, String contactEmail) {
        this.contactName = contactName;
        this.contactID = contactID;
        this.contactEmail = contactEmail;
    }

    /**
     * Setters and getters for the contact object
     **/
    public String getContactName() {
        return contactName;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactEmail() {
        return contactEmail;
    }

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
