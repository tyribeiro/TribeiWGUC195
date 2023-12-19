package Controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controls the contact schedule reports page. Initializes the tableview and combobox
 * drop down selector and displays reports based on the contact selected.
 */
public class ContactScheduleReport implements Initializable {
    @FXML
    public TextArea reportTextArea;
    @FXML
    public Button back;
    @FXML
    public TableColumn apptIDCol;
    @FXML
    public TableColumn titleCol;
    @FXML
    public TableColumn typeCol;
    @FXML
    public TableColumn descriptionCol;
    @FXML
    public TableColumn startDateCol;
    @FXML
    public TableColumn endTimeCol;
    @FXML
    public TableColumn customerIDCol;
    @FXML
    public ComboBox contacts;
    @FXML
    public TableView appts;
    @FXML
    public Label contactScheduleTitle;
    @FXML
    private TableColumn startTimeCol;
    private ResourceBundle resourceBundle;

    /**
     * This method Initializes the controller and sets up colummns in the table and drop down selection options.
     *
     * @param url       URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());
        contactScheduleTitle.setText(resourceBundle.getString("contactsSchedule"));
        back.setText(resourceBundle.getString("back"));

        contacts.setPromptText(resourceBundle.getString("contacts"));
        contacts.setItems(ContactsDAO.getAllContactNames());
        contacts.setOnAction(actionEvent -> showAppts());

        apptIDCol.setText(resourceBundle.getString("apptID"));
        titleCol.setText(resourceBundle.getString("apptTitle"));
        typeCol.setText(resourceBundle.getString("apptType"));
        descriptionCol.setText(resourceBundle.getString("description"));
        startDateCol.setText(resourceBundle.getString("startDate"));
        startTimeCol.setText(resourceBundle.getString("startTime"));
        endTimeCol.setText(resourceBundle.getString("endTime"));

        //initialize table
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    }

    /**
     * This method displays the appointments in the tableview based on the option selected in the drop down menu.
     */
    private void showAppts() {
        String contactNameSelected = contacts.getSelectionModel().getSelectedItem().toString();
        if (contactNameSelected != null) {
            try {
                appts.setItems(AppointmentsDAO.getApptsByContactName(contactNameSelected));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method navigates back to main reports page.
     *
     * @param actionEvent action event that has occured (clicking the back button)
     */
    public void goToReportsPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/ReportsPage.fxml"));
            stage.setTitle(resourceBundle.getString("reports"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
