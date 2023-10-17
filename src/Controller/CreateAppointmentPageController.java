package Controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import Model.AppointmentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
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
    public ComboBox typeCombo;

    public void SelectType(ActionEvent actionEvent) {
    }

    public void goToAppointmentsPage(ActionEvent actionEvent){

    }

    public void saveButtonAction(ActionEvent actionEvent) throws SQLException {
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = (String) typeCombo.getSelectionModel().getSelectedItem();
        LocalDate startDate = pickStartDate.getValue();
        LocalDate endDate = pickEndDate.getValue();
        LocalTime startTime = (LocalTime) startTimeSelector.getValue();
        LocalTime endTime = (LocalTime) endTimeSelector.getValue();
        LocalDateTime startDateTime = LocalDateTime.of((startDate),(startTime));
        LocalDateTime endDateTime = LocalDateTime.of((endDate),(endTime));
        String contact = (String) contactSelector.getSelectionModel().getSelectedItem();
        int customerID = (int) customerIDSelector.getSelectionModel().getSelectedItem();
        int userID = (int) userIDSelector.getSelectionModel().getSelectedItem();
        int apptID = Integer.parseInt(apptIDTextfield.getText());

        LocalDateTime startLocalDT = LocalDateTime.of(startDate,startTime);
        LocalDateTime endLocalDT = LocalDateTime.of(endDate,endTime);

        //LDT --> ZonedDT conversion
        ZonedDateTime startZoned = startLocalDT.atZone(ZoneId.systemDefault());
        ZonedDateTime endZoned = endLocalDT.atZone(ZoneId.systemDefault());

        AppointmentsModel newAppt = new AppointmentsModel(apptID,title,description,location,type,startZoned.toLocalDateTime(),endZoned.toLocalDateTime(),customerID,userID, ContactsDAO.readContactID(contact),contact);
        try {
            if(AppointmentsDAO.overlappingAppts(newAppt)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Overlapping");
                alert.setContentText("Appointment you are trying to create overlaps with another existing appointment");
                alert.showAndWait();
                return;
            }
            AppointmentsDAO.addANewAppt(newAppt);

            Stage stage = (Stage) titleTextField.getScene().getWindow();
            stage.close();
        }
        catch (Exception e)
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setContentText("Cannot save appointment");
               alert.showAndWait();
               e.printStackTrace();

           }
    }
    public void cancelButtonAction(ActionEvent actionEvent){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactSelector.setItems(ContactsDAO.readAllContacts());
            customerIDSelector.setItems(CustomersDAO.readCustomers());
            userIDSelector.setItems(UsersDAO.readAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<String> businessHours = FXCollections.observableArrayList("8:00","09:00", "10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");

        startTimeSelector.setItems(businessHours);
        endTimeSelector.setItems(businessHours);
    }
}