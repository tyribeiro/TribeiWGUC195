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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.AppointmentsModel;
import javafx.stage.Stage;

public class UpdateAppointmentPageController implements Initializable {
    @FXML
    public Label header;
    @FXML
    public TableView appointments_Table;
    @FXML
    public Label apptTitleLabel;
    @FXML
    public Label apptStartDateLabel;
    @FXML
    public Label apptDescriptionLabel;
    @FXML
    public Label apptLocationLabel;
    @FXML
    public Label apptendDateLabel;
    @FXML
    public Label apptIDLabel;
    @FXML
    public Label apptStartTimeLabel;
    @FXML
    public Label apptCustomerIDLabel;
    @FXML
    public Label apptEndTimeLabel;
    @FXML
    public Label apptUserIdLabel;
    @FXML
    public Label apptContactLabel;
    @FXML
    public Label apptTypeLabel;
    @FXML
    public TextField titleTextfield;
    @FXML
    public TextField descriptionTextfield;
    @FXML
    public TextField locationTextfield;
    @FXML
    public TextField apptIDTextfield;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    public ComboBox startTimeComboBox;
    @FXML
    public ComboBox endTimeComboBox;
    @FXML
    public ComboBox<String> contactComboBox;
    @FXML
    public ComboBox<Integer> customerIDComboBox;
    @FXML
    public ComboBox<Integer> userIDComboBox;
    @FXML
    public TextField typeTextfield;
    @FXML
    public Button saveButton;
    @FXML
    public Button backButton;
    @FXML
    public Button cancelButton;
    private ResourceBundle resourceBundle;

    /**
     * This method initializes the UI elements of the update appointment page and populates the combo boxes with the appropriate selection options.
     *
     * @param url       URL
     * @param resources Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        backButton.setText(resourceBundle.getString("back"));
        header.setText(resourceBundle.getString("updateAppt"));
        apptTitleLabel.setText(resourceBundle.getString("title"));
        apptIDLabel.setText(resourceBundle.getString("apptID"));
        apptStartDateLabel.setText(resourceBundle.getString("startDate"));
        apptendDateLabel.setText(resourceBundle.getString("endDate"));
        apptDescriptionLabel.setText(resourceBundle.getString("description"));
        apptTypeLabel.setText(resourceBundle.getString("type"));
        apptLocationLabel.setText(resourceBundle.getString("location"));
        apptStartTimeLabel.setText(resourceBundle.getString("startTime"));
        apptEndTimeLabel.setText(resourceBundle.getString("endTime"));
        apptContactLabel.setText(resourceBundle.getString("contact"));
        apptCustomerIDLabel.setText(resourceBundle.getString("customerID"));
        apptUserIdLabel.setText(resourceBundle.getString("userID"));
        saveButton.setText(resourceBundle.getString("save"));
        cancelButton.setText(resourceBundle.getString("cancel"));

        //add contacts to the dropdown
        ObservableList<String> contactsOption = FXCollections.observableArrayList();

        try {
            ObservableList<ContactsModel> contacts = ContactsDAO.readAllContacts();;
            if (contacts != null) {
                for (ContactsModel contact: contacts) {
                    contactsOption.add(contact.getContactName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        contactComboBox.setItems(contactsOption);

        //add customerIDs to the dropdown
        ObservableList<Integer> customerIDOption = FXCollections.observableArrayList();

        try {
            ObservableList<CustomersModel> customers = CustomersDAO.readCustomers();;
            if (customers != null) {
                for (CustomersModel customer: customers) {
                    customerIDOption.add(customer.getCustomerID());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerIDComboBox.setItems(customerIDOption);

        //add userIDs to the dropdown
        ObservableList<Integer> userIDOption = FXCollections.observableArrayList();

        try {
            ObservableList<UsersModel> users = UsersDAO.readAllUsers();;
            if (users != null) {
                for (UsersModel user: users) {
                    userIDOption.add(user.getUserID());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userIDComboBox.setItems(userIDOption);

        //add times to dropdown
        startTimeComboBox.setItems(Timezones.getBusinessHours());
        endTimeComboBox.setItems(Timezones.getBusinessHours());
    }

    /**
     * This method auto-populates the fields on the page with what is in the database.
     *
     * @param appointment the appointment selected to update and whos information will be auto populated in the fields.
     * @throws SQLException if there is an error communicating with the database.
     */
    public void autopopulate(AppointmentsModel appointment) throws SQLException {

        AppointmentsModel appt = AppointmentsDAO.getApptsByID(appointment.getApptID());
        customerIDComboBox.getSelectionModel().select(Integer.valueOf(appointment.getCustomerID()));
        contactComboBox.getSelectionModel().select(appointment.getContactName());
        userIDComboBox.getSelectionModel().select(Integer.valueOf(appointment.getUserID()));

        titleTextfield.setText(appointment.getApptTitle());
        apptIDTextfield.setText(String.valueOf(appointment.getApptID()));
        descriptionTextfield.setText(appointment.getApptDescription());
        typeTextfield.setText(appointment.getApptType());
        locationTextfield.setText(appointment.getApptLocation());
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        startTimeComboBox.getSelectionModel().select(appointment.getStart().toLocalTime());
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        endTimeComboBox.getSelectionModel().select(appointment.getEnd().toLocalTime());

    }

    /**
     * This method handles the save button on this page.It gets the information entered by the user and updates it in the database, then it updates the table.
     * @param actionEvent action event that has occured (clicking the save button)
     * @throws SQLException if there is an error communicating with the database.
     */
    public void saveUpdateAppointment(ActionEvent actionEvent) throws SQLException {

        try {
            int ID = Integer.parseInt(apptIDTextfield.getText());
            String title = titleTextfield.getText();
            String description = descriptionTextfield.getText();
            String location = locationTextfield.getText();
            String type = typeTextfield.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(startTimeComboBox.getValue().toString(), timeFormatter);
            LocalTime endTime = LocalTime.parse(endTimeComboBox.getValue().toString(), timeFormatter);

            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            ContactsModel selectedContact = ContactsDAO.getContactByName(contactComboBox.getSelectionModel().getSelectedItem());
            String contact = selectedContact.getContactName();
            int contactID = ContactsDAO.getContactByName(contact).getContactID();

            CustomersModel selectedCustomer = CustomersDAO.readCustomerByID(customerIDComboBox.getSelectionModel().getSelectedItem());
            int customerID = selectedCustomer.getCustomerID();


            UsersModel selectedUser = UsersDAO.getUserByID(userIDComboBox.getSelectionModel().getSelectedItem());
            int userID = selectedUser.getUserID();

            AppointmentsModel updatedAppt = new AppointmentsModel(ID, title, description, location, type, start, end, customerID, userID, contactID, contact);

            if ((checkFields(updatedAppt))) {
                boolean updated = AppointmentsDAO.updateExistingAppt(ID, title, description, location, ContactsDAO.getContactByName(contact).getContactID(), type, startDate, endDate, startTime, endTime, customerID, userID);
                if (updated) {
                    //update tableview
                    AppointmentsPageController.updateTableView(appointments_Table);

                    goToAppointmentsMainPage(actionEvent);
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("apptUpdated"));
                alert.setTitle(resourceBundle.getString("apptUpdated"));
                alert.showAndWait();

                try {
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle(resourceBundle.getString("appts"));
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method checks if all fields are filled in and also formats the time. It lso validates the times selected to see if they are valid times and checks for overlapping appointments.
     * @param newAppt the appointment that has been created with the new updated information updated by the user.
     * @return True if the appointment is valid time and all fields are filled.
     * @throws SQLException if there is an error communicating with the database.
     */
    private boolean checkFields(AppointmentsModel newAppt) throws SQLException {
        DateTimeFormatter stringToLocalTime = DateTimeFormatter.ofPattern("HH:mm");
        boolean valid = true;
        if (!checkFieldsAreFilled()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(resourceBundle.getString("fillInAllFieldsErrorTitle"));
            alert.setContentText(resourceBundle.getString("fillInAllFieldsError"));
            alert.showAndWait();
            valid = false;
        }

        if (endDatePicker.getValue().isBefore(startDatePicker.getValue()) || LocalTime.parse(endTimeComboBox.getValue().toString(), stringToLocalTime).isBefore(LocalTime.parse(startTimeComboBox.getValue().toString(), stringToLocalTime))) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("dateTimeError"));
            alert.setContentText(resourceBundle.getString("dateTimeError"));
            alert.showAndWait();
            valid = false;
        }

        if (AppointmentsDAO.overlappingAppts(newAppt)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("overlapping"));
            alert.setContentText(resourceBundle.getString("overlap"));
            alert.showAndWait();
            valid = false;
        }

        return valid;
    }

    /**
     * This method navigates to the main appointments page.
     * @param actionEvent action event that has occured (clicking the back button)
     */
    public void goToAppointmentsMainPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("appts"));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if all the fields on the page are filled in.
     * @return True if all fields are filled in and false if there is missing information.
     */
    private boolean checkFieldsAreFilled() {
        if (
                titleTextfield.getText().isEmpty() ||
                        descriptionTextfield.getText().isEmpty() ||
                        locationTextfield.getText().isEmpty() ||
                        typeTextfield.getText().isEmpty() ||
                        startDatePicker.getValue() == null ||
                        endDatePicker.getValue() == null ||
                        startTimeComboBox.getValue() == null ||
                        endTimeComboBox.getValue() == null ||
                        contactComboBox.getValue() == null ||
                        customerIDComboBox.getValue() == null ||
                        userIDComboBox.getValue() == null
        ) {
            return false;
        } else {
            return true;
        }
    }
}