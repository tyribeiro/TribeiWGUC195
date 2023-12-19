package Controller;

import DAO.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controls the logic for the reports page.
 * It navigates to the different reports.
 */
public class ReportsPageController implements Initializable {
    @FXML
    public Label reportsTitle;
    @FXML
    public Button totalApptReport;
    @FXML
    public Button contactScheduleReport;
    @FXML
    public Button customerActivityReport;
    @FXML
    public Button backButton;
    @FXML
    public Button customerAppointmentsButton;

    private ResourceBundle resourceBundle;

    /**
     * This method generates the Total Appointments by type and month report.
     *
     * @param actionEvent action event that has occured (clicking the total appointment report button)
     * @throws IOException if there is an error loading the fxml page
     */
    public void goToTotalApptReport(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/TotalApptReport.fxml"));
            stage.setTitle(resourceBundle.getString("totalAppts"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method generates the Contact Schedule report..
     *
     * @param actionEvent action event that has occured (clicking the contact schedule report button)
     * @throws IOException if there is an error loading the fxml page
     */
    public void goToContactScheduleReport(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/ContactScheduleReport.fxml"));
            stage.setTitle(resourceBundle.getString("contactSchedule"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method go back to the main appointments page.
     *
     * @param actionEvent action event that has occured (clicking the back button)
     * @throws IOException if there is an error loading the fxml page
     */
    public void goToAppointmentsPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
            stage.setTitle(resourceBundle.getString("appointments"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initializes the labels and buttons in the page.
     * @param url URL
     * @param resources Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());
        reportsTitle.setText(resourceBundle.getString("chooseReport"));
        totalApptReport.setText(resourceBundle.getString("totalAppts"));
        customerAppointmentsButton.setText(resourceBundle.getString("customerSchedule"));
        contactScheduleReport.setText(resourceBundle.getString("contactsSchedule"));
        backButton.setText(resourceBundle.getString("back"));
    }


    /**
     * This method generates the customer appointments report that gives the number of appointments each customer has.
     */
    public void customerAppointmentReport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinSize(400, 400);
        alert.setTitle(resourceBundle.getString("appointmentsByCustomerID"));
        alert.setContentText(resourceBundle.getString("customerScheduleContext") + "\n" + resourceBundle.getString("customerScheduleContext2") + "\n" + AppointmentsDAO.getTotalApptsByCustomer());
        alert.showAndWait();
    }
}
