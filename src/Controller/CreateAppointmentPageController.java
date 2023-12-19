package Controller;

import DAO.*;
import Model.*;
import Utils.Timezones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates a new appointment in the database.
 * Controls the create appointment page. Updates the tableview and combobox
 */
public class CreateAppointmentPageController implements Initializable {


    @FXML
    public Label Header;
    @FXML
    public Label contactLabel;
    @FXML

    public Label titleLabel;
    @FXML

    public Label descriptionLabel;
    @FXML

    public Label locationLabel;
    @FXML

    public Label typeLabel;
    @FXML

    public TextField titleTextField;
    @FXML

    public TextField descriptionTextField;
    @FXML

    public TextField locationTextField;
    @FXML

    public Button saveButton;
    @FXML

    public Button cancelButton;
    @FXML

    public Button backToAppointments;
    @FXML

    public Label apptIDLabel;
    @FXML

    public Label startDateLabel;
    @FXML

    public Label startTimeLabel;
    @FXML

    public Label endDateLabel;
    @FXML

    public Label endTimeLabel;
    @FXML

    public Label customerIDLabel;
    @FXML

    public TextField apptIDTextfield;
    @FXML

    public ComboBox endTimeSelector;
    @FXML

    public ComboBox startTimeSelector;
    @FXML

    public ComboBox contactSelector;
    @FXML

    public ComboBox customerIDSelector;
    @FXML

    public DatePicker pickStartDate;
    @FXML

    public DatePicker pickEndDate;
    @FXML

    public ComboBox userIDSelector;
    @FXML

    public Label userIDLabel;
    @FXML

    public TextField typeTextfield;

    private  ResourceBundle resourceBundle;

    /**
     * This method navigates to the appointment main page controller
     *
     * @param actionEvent action event that has occured (clicking the back button)
     */
    public void goToAppointmentsPage(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("appointments"));
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method handles the saving of appointments. It validates the field in the create appointment page.
     * It also validates and checks for overlapping appointment times.
     *
     * @param actionEvent action event that has occured (clicking the save button)
     * @throws SQLException if there is an error communicating with the database
     */
    public void saveButtonAction(ActionEvent actionEvent) throws SQLException {
        if(!checkFieldsAreFilled()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(resourceBundle.getString("fillInAllFieldsErrorTitle"));
            alert.setContentText(resourceBundle.getString("fillInAllFieldsError"));
            alert.showAndWait();
            return;
        }
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextfield.getText();

        LocalDate startDate = pickStartDate.getValue();
        LocalDate endDate = pickEndDate.getValue();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse((String) startTimeSelector.getValue(), timeFormatter);
        LocalTime endTime = LocalTime.parse((String) endTimeSelector.getValue(), timeFormatter);


        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        ContactsModel selectedContact = (ContactsModel) contactSelector.getSelectionModel().getSelectedItem();
        String contact =selectedContact.getContactName();

        CustomersModel selectedCustomer = (CustomersModel) customerIDSelector.getSelectionModel().getSelectedItem();
        int customerID = selectedCustomer.getCustomerID();


        UsersModel selectedUser = (UsersModel) userIDSelector.getSelectionModel().getSelectedItem();
        int userID = selectedUser.getUserID();


        LocalDateTime startLocal = LocalDateTime.of(startDate, startTime);
        LocalDateTime endLocal = LocalDateTime.of(endDate,endTime);

        //converting to eastern time
        LocalDateTime startEastern = Timezones.ETConversion(startLocal);
        LocalDateTime endEastern = Timezones.ETConversion(endLocal);



        AppointmentsModel newAppt = new AppointmentsModel(title, description, location, type, start, end, customerID, userID, ContactsDAO.readContactID(contact), contact);


        DateTimeFormatter stringToLocalTime = DateTimeFormatter.ofPattern("HH:mm");
        boolean valid = true;
        if (!checkFieldsAreFilled()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(resourceBundle.getString("fillInAllFieldsErrorTitle"));
            alert.setContentText(resourceBundle.getString("fillInAllFieldsError"));
            alert.showAndWait();
            valid = false;
        }

        try {
            if (AppointmentsDAO.overlappingAppts(newAppt)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("overlapping"));
                alert.setContentText(resourceBundle.getString("overlap"));
                alert.showAndWait();
                return;
            }

            if (endDate.isBefore(startDate) || endTime.isBefore(startTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("dateTimeError"));
                alert.setContentText(resourceBundle.getString("dateTimeError"));
                alert.showAndWait();
                return;
            }

            int autoGeneratedID = AppointmentsDAO.addANewAppt(newAppt);

            if (autoGeneratedID == -1) {
                throw new SQLException("Auto generated ID not retrieved from database");
            }

            newAppt.setApptID(autoGeneratedID);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(resourceBundle.getString("apptCreated"));
            alert.setContentText(resourceBundle.getString("apptCreated"));
            alert.showAndWait();

            try {
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle(resourceBundle.getString("appointments"));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("errorSaving"));
        }
    }

    /**
     * This method cancels operations and returns to the main appointment page.
     *
     * @param actionEvent action event that has occured (clicking the cancel button)
     */
    public void cancelButtonAction(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("appointments"));
            stage.show();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method Initializes the controller and sets up colummns in the table and drop down selection options.
     * @param url URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());


        Header.setText(resourceBundle.getString("createAppt"));
        apptIDLabel.setText(resourceBundle.getString("apptID"));
        descriptionLabel.setText(resourceBundle.getString("description"));
        locationLabel.setText(resourceBundle.getString("location"));
        typeLabel.setText(resourceBundle.getString("type"));
        titleLabel.setText(resourceBundle.getString("title"));
        startDateLabel.setText(resourceBundle.getString("startDate"));
        endDateLabel.setText(resourceBundle.getString("endDate"));
        startTimeLabel.setText(resourceBundle.getString("startTime"));
        endTimeLabel.setText(resourceBundle.getString("endTime"));
        contactLabel.setText(resourceBundle.getString("contact"));
        userIDLabel.setText(resourceBundle.getString("userID"));
        customerIDLabel.setText(resourceBundle.getString("customerID"));
        saveButton.setText(resourceBundle.getString("save"));
        cancelButton.setText(resourceBundle.getString("cancel"));
        backToAppointments.setText(resourceBundle.getString("back"));

        // populating the contacts drop down menu with the contact names
        ObservableList<String> contactsList = ContactsDAO.getAllContactNames();
        try {
            ObservableList<ContactsModel> contacts = FXCollections.observableArrayList();
            if (contacts != null) {
                for (ContactsModel contact : contacts) {
                    contactsList.add(contact.getContactName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactSelector.setItems(contactsList);

        // populating the drop down menus with the customer IDs and User IDs
        ObservableList<String> customerList = CustomersDAO.getAllCustomerIDs();
        customerIDSelector.setItems(customerList);

        ObservableList<String> userList = UsersDAO.getAllUserIDs();
        userIDSelector.setItems(userList);

        // populate time drop down menus for times
        startTimeSelector.setItems(Timezones.getBusinessHours());
        endTimeSelector.setItems(Timezones.getBusinessHours());
    }

    /**
     * This method checks if all the fields are filles in on the Create appointment page
     * @return True if all fields have input, false otherwise.
     */
    private boolean checkFieldsAreFilled(){
        if(
                titleTextField.getText().isEmpty() ||
                        descriptionTextField.getText().isEmpty() ||
                        locationTextField.getText().isEmpty() ||
                        typeTextfield.getText().isEmpty() ||
                        pickStartDate.getValue() == null ||
                        pickEndDate.getValue() == null ||
                        startTimeSelector.getSelectionModel().isEmpty() ||
                        endTimeSelector.getSelectionModel().isEmpty() ||
                        contactSelector.getSelectionModel().isEmpty() ||
                        customerIDSelector.getSelectionModel().isEmpty() ||
                        userIDSelector.getSelectionModel().isEmpty()
        ) {return false; } else {return true;}
    }


}