package Controller;

import DAO.CountriesDAO;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.AppointmentsModel;
import  DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import javafx.stage.Stage;


public class UpdateAppointmentPageController implements Initializable {
 public Label header;
  public Label apptTitleLabel;
  public Label apptStartDateLabel;
  public Label apptDescriptionLabel;
  public Label apptLocationLabel;
  public Label apptendDateLabel;
  public Label apptIDLabel;
  public Label apptStartTimeLabel;
  public Label apptCustomerIDLabel;
  public Label apptEndTimeLabel;
  public Label apptUserIdLabel;
  public Label apptContactLabel;
  public Label apptTypeLabel;
  public TextField titleTextfield;
  public TextField descriptionTextfield;
  public TextField locationTextfield;
  public TextField apptIDTextfield;
  public DatePicker startDatePicker;
  public DatePicker endDatePicker;
  public ComboBox startTimeComboBox;
  public ComboBox endTimeComboBox;
  public ComboBox<String> contactComboBox;
  public ComboBox<Integer> customerIDComboBox;
  public ComboBox<Integer> userIDComboBox;
  public TextField typeTextfield;
  public Button saveButton;
  public Button backButton;
  public Button cancelButton;
  private ResourceBundle resourceBundle;


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


    }

    public void autopopulate(AppointmentsModel appointment ){
        titleTextfield.setText(appointment.getApptTitle());
        apptIDTextfield.setText(String.valueOf(appointment.getApptID()));
        descriptionTextfield.setText(appointment.getApptDescription());
        typeTextfield.setText(appointment.getApptType());
        locationTextfield.setText(appointment.getApptLocation());
        startDatePicker.setValue(appointment.getApptStartDate());
        startTimeComboBox.getSelectionModel().select(appointment.getApptStartTime());
        endDatePicker.setValue(appointment.getApptEndDate());
        endTimeComboBox.getSelectionModel().select(appointment.getApptEndTime());

        customerIDComboBox.getSelectionModel().select(appointment.getCustomerID());
        contactComboBox.getSelectionModel().select(appointment.getContactName());
        userIDComboBox.getSelectionModel().select(appointment.getUserID());
    }


  public void saveUpdateAppointment(ActionEvent actionEvent) {
  }

  public void goToAppointmentsMainPage(ActionEvent actionEvent) {
    try {
      Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
      Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
      stage.setScene(new Scene(scene));
      stage.setTitle(resourceBundle.getString("appts"));
      stage.show();

    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
