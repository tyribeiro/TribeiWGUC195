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


public class ContactScheduleReport implements Initializable {
    public TextArea reportTextArea;
    public Button back;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startDateCol;
    public TableColumn endTimeCol;
    public TableColumn customerIDCol;
    public ComboBox contacts;
    public TableView appts;
    @FXML
    private TableColumn startTimeCol;
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());
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

    private void showReport() {
        Map<String, String> report = generateReport();
        StringBuilder reportContext = new StringBuilder();
        report.forEach((contact, data) -> {
            reportContext.append(contact).append(":/n").append(data).append("/n");
        });
        reportTextArea.setText(reportContext.toString());
    }

    private Map<String, String> generateReport() {
        try {
            return AppointmentsDAO.getApptsByContact();
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

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
