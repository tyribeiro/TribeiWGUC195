package Controller;

import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * This class generates the report for the total appointments in the database by month and type.
 */
public class TotalApptReport implements Initializable {
    public Label reportTypeMonth;
    public RadioButton typeRadio;
    public RadioButton monthRadio;
    public Button generateButton;
    public Button backButton;
    private ResourceBundle resourceBundle;
    @FXML
    private ToggleGroup reportToggleGroup;

    /**
     * This method initializes the UI elements of the total appointments report.
     *
     * @param url       URL
     * @param resources Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        reportTypeMonth.setText(resourceBundle.getString("totalAppts"));
        typeRadio.setText(resourceBundle.getString("type"));
        monthRadio.setText(resourceBundle.getString("month"));
        generateButton.setText(resourceBundle.getString("generateReport"));
    }

    /**
     * This method generates the report based on the radio button selected for type or month.
     *
     * @param actionEvent action event that has occured (clicking the generate report button)
     * @throws SQLException if there is an error communicating with the database
     */
    public void generateReport(ActionEvent actionEvent) throws SQLException {
        if (typeRadio.isSelected()) {
            List<AppointmentsModel> appts = AppointmentsDAO.getAllAppointments();
            Map<String, Long> types = appts.stream().collect(Collectors.groupingBy(AppointmentsModel::getApptType, Collectors.counting()));

            StringBuilder report = new StringBuilder();
            types.forEach((type, count) -> report.append(type).append(" = ").append(count).append("\n"));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("totalApptsLabel"));
            alert.setContentText("Types Report" + "\n" + report);
            alert.showAndWait();
        } else if (monthRadio.isSelected()) {
            List<AppointmentsModel> appts = AppointmentsDAO.getAllAppointments();
            Map<Integer, Long> numberOfMonth = appts.stream().collect(Collectors.groupingBy(appt -> appt.getStart().getMonthValue(), Collectors.counting()));
            StringBuilder report = new StringBuilder();
            numberOfMonth.forEach((month, count) -> {
                String currentMonth = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
                report.append(currentMonth).append(" = ").append(count).append("\n");
            });
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("totalApptsLabel"));
            alert.setContentText("Month Report" + "\n" + report);
            alert.showAndWait();
        }
    }

    /**
     * This method navigates back to the reports main page.
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

