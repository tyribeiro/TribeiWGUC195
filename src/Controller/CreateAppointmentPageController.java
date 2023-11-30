package Controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import Model.AppointmentsModel;
import Model.ContactsModel;
import Model.CustomersModel;
import Model.UsersModel;
import Utils.Timezones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateAppointmentPageController implements Initializable {


    public Label Header;
    public Label contactLabel;
    public Label titleLabel;
    public Label descriptionLabel;
    public Label locationLabel;
    public Label typeLabel;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public Button saveButton;
    public Button cancelButton;
    public Button backToAppointments;
    public Label apptIDLabel;
    public Label startDateLabel;
    public Label startTimeLabel;
    public Label endDateLabel;
    public Label endTimeLabel;
    public Label customerIDLabel;
    public TextField apptIDTextfield;
    public ComboBox endTimeSelector;
    public ComboBox startTimeSelector;
    public ComboBox contactSelector;
    public ComboBox customerIDSelector;
    public DatePicker pickStartDate;
    public DatePicker pickEndDate;
    public ComboBox userIDSelector;
    public Label userIDLabel;
    public TextField typeTextfield;

    private  ResourceBundle resourceBundle;

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

        if (!Timezones.businessHours(startEastern) || !Timezones.businessHours(endEastern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with Time");
            alert.setContentText(resourceBundle.getString("businessHourError"));
            alert.showAndWait();
            return;
        }


        AppointmentsModel newAppt = new AppointmentsModel(title, description, location, type, startDate, startLocal.toLocalTime(), endDate, endLocal.toLocalTime(), customerID, userID, ContactsDAO.readContactID(contact), contact);
        try {
            if(AppointmentsDAO.overlappingAppts(newAppt)){
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

            if(autoGeneratedID == -1){
                throw new SQLException("Auto generated ID not retrieved from database");
            }

            try{
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

                }catch (Exception e) {
                    e.printStackTrace();
                }


            }catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("errorSaving"));
            }
        } catch (Exception e)
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle(resourceBundle.getString("error"));
               alert.setContentText(resourceBundle.getString("errorSaving"));
               alert.showAndWait();
               e.printStackTrace();

           }
    }


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

        try {
           ObservableList<ContactsModel> contacts = ContactsDAO.readAllContacts();
           contactSelector.setItems(contacts);
           contactSelector.setCellFactory(a -> new ListCell<ContactsModel>(){
           @Override
               protected void updateItem(ContactsModel contact, boolean empty){
               super.updateItem(contact,empty);
               setText(empty ? "" : contact.getContactName());
           }
           });

           contactSelector.setConverter(new StringConverter<ContactsModel>() {
               @Override
               public String toString(ContactsModel contact) {
                   return contact == null ? null : contact.getContactName();
               }

               @Override
               public ContactsModel fromString(String s) {
                   return null;
               }
           });


           ObservableList<CustomersModel> customer = CustomersDAO.readCustomers();
           customerIDSelector.setItems(customer);
           customerIDSelector.setCellFactory(a -> new ListCell<CustomersModel>(){
               @Override
               protected void updateItem(CustomersModel customer, boolean empty){
                   super.updateItem(customer,empty);
                   setText(empty ? "" : String.valueOf(customer.getCustomerID()));
               }
           });

           customerIDSelector.setConverter(new StringConverter<CustomersModel>() {
               @Override
               public String toString(CustomersModel customer){
                   return customer == null ? null : String.valueOf(customer.getCustomerID());
               }

               @Override
               public CustomersModel fromString(String s) {
                   return null;
               }
           });

           ObservableList<UsersModel> users = UsersDAO.readAllUsers();
           userIDSelector.setItems(users);
            userIDSelector.setCellFactory(a -> new ListCell<UsersModel>(){
                @Override
                protected void updateItem(UsersModel user, boolean empty){
                    super.updateItem(user,empty);
                    setText(empty ? "" : String.valueOf(user.getUserID()));
                }
            });

            userIDSelector.setConverter(new StringConverter<UsersModel>() {
                @Override
                public String toString(UsersModel user){
                    return user == null ? null : String.valueOf(user.getUserID());
                }

                @Override
                public UsersModel fromString(String s) {
                    return null;
                }
            });


            ObservableList<String> businessHours = FXCollections.observableArrayList("08:00", "08:30","09:00", "09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00");

            startTimeSelector.setItems(businessHours);
            endTimeSelector.setItems(businessHours);
        } catch (SQLException e) {
               e.printStackTrace();
            } catch (Exception e) {
               e.printStackTrace();
        }
    }


    private boolean checkFieldsAreFilled(){
        if(
                titleTextField.getText().isEmpty() ||
                descriptionTextField.getText().isEmpty() ||
                locationTextField.getText().isEmpty() ||
                        typeTextfield.getText().isEmpty() ||
                pickStartDate.getValue() ==null ||
                pickEndDate.getValue() == null ||
                startTimeSelector.getSelectionModel().isEmpty() ||
                        endTimeSelector.getSelectionModel().isEmpty() ||
                        contactSelector.getSelectionModel().isEmpty() ||
                        customerIDSelector.getSelectionModel().isEmpty() ||
                        userIDSelector.getSelectionModel().isEmpty()
        ) {return false; } else {return true;}
    }
}