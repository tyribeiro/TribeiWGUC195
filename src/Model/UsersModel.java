package Model;

/**
 * This class is for the User Object and stores all key information for a user.
 * Includes constructors, getters , setters and to string method.
 */
public class UsersModel {
    private int userID;
    private String userName;
    private String userPassword;

    /**
     * Constructor
     *
     * @param userID       id of the user
     * @param userName     name of the user
     * @param userPassword password of the user
     */
    public UsersModel(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * setters and getters
     */
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
