package Controller;

import DAO.AppointmentsDAO;
import javafx.event.ActionEvent;
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

public class ReportsPageController implements Initializable {
    public Label reportsTitle;
    public Button totalApptReport;
    public Button contactScheduleReport;
    public Button customerActivityReport;
    public Button backButton;
    public Button customerAppointmentsButton;
    private ResourceBundle resourceBundle;

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

    public void goToContactScheduleReport(ActionEvent actionEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());
        reportsTitle.setText(resourceBundle.getString("chooseReport"));
        totalApptReport.setText(resourceBundle.getString("totalAppts"));
        customerAppointmentsButton.setText(resourceBundle.getString("customerSchedule"));
        contactScheduleReport.setText(resourceBundle.getString("contactsSchedule"));
        backButton.setText(resourceBundle.getString("back"));
    }


    public void customerAppointmentReport() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinSize(400, 400);
        alert.setTitle(resourceBundle.getString("appointmentsByCustomerID"));
        alert.setContentText(resourceBundle.getString("customerScheduleContext") + "\n" + resourceBundle.getString("customerScheduleContext2") + "\n" + AppointmentsDAO.getTotalApptsByCustomer());
        alert.showAndWait();
    }
}
