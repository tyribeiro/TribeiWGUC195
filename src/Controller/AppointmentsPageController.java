package Controller;

import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
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

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsPageController implements Initializable {

    @FXML
    public  TableView<AppointmentsModel> appointments_Table;

    public TableColumn<AppointmentsModel,Integer> appointmentID_Column;
    @FXML
    public TableColumn<AppointmentsModel,String>  title_Column;
    @FXML
    public TableColumn<AppointmentsModel,String>  description_Column;
    @FXML
    public TableColumn<AppointmentsModel,String>  location_Column;
    @FXML
    public TableColumn<AppointmentsModel,String> contact_Column;
    @FXML
    public TableColumn<AppointmentsModel,String>  type_Column;
    @FXML
    public TableColumn<AppointmentsModel, LocalDateTime> start_Column;
    @FXML
    public TableColumn<AppointmentsModel, LocalDateTime> end_Column;
    @FXML
    public TableColumn<AppointmentsModel, LocalTime> startTime_Column;
    @FXML
    public TableColumn<AppointmentsModel, LocalTime> endTime_Column;
    @FXML
    public TableColumn<AppointmentsModel,Integer> customerID_Column;
    @FXML
    public TableColumn<AppointmentsModel,Integer>  userID_Column;
    @FXML
    public Button CreateAppointmentButton;
    @FXML
    public Button UpdateAppointmentButton;
    @FXML
    public Button DeleteAppointmentButton;
    @FXML
    public Label Header;
    @FXML
    public Button CustomersButton;
    @FXML
    public RadioButton allAppointmentsButton;
    @FXML

    public RadioButton byMonthButton;
    @FXML
    public RadioButton byWeekButton;
    @FXML
    public Button SearchButton;
    @FXML
    public TextField SearchTextField;
    @FXML
    public Button ReportsButton;
    @FXML

    static ObservableList<AppointmentsModel> appts;

    @FXML
    private ToggleGroup ToggleView;


    public void goToCustomersPage(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CustomersMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void goToReportsPage(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/ReportsPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Reports");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void viewToggle(ActionEvent actionEvent){
        try {
            LocalDate today = LocalDate.now();
            LocalDate start;
            LocalDate end;

            if(allAppointmentsButton.isSelected()){
               appts = AppointmentsDAO.getAllAppointments();
            } else if (byWeekButton.isSelected()) {
                appts = AppointmentsDAO.getApptsThisWeek();
            } else if (byMonthButton.isSelected()) {
                appts = AppointmentsDAO.getApptThisMonth();
            }

            appointments_Table.setItems(appts);
            appointments_Table.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //when createAppt button is clicked, goes to createApptPage FXML
    public void createAppt(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateAppointmentPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Create A New Appointment");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void searchAppt(ActionEvent actionEvent) {
        String searchStatement = SearchTextField.getText().trim();

        if(!searchStatement.isEmpty()){
            ObservableList<AppointmentsModel> searchFilterText = FXCollections.observableArrayList();
            for (AppointmentsModel appt : appts){
                if(String.valueOf(appt.getApptID()).contains(searchStatement) || appt.getApptTitle().toLowerCase().contains(searchStatement.toLowerCase())){
                    searchFilterText.add(appt);
                }
            }
            appointments_Table.setItems(searchFilterText);
        }else {
            appointments_Table.setItems(appts);
        }
    }


    public void updateAppt(ActionEvent actionEvent){
        AppointmentsModel apptToUpdate = (AppointmentsModel) appointments_Table.getSelectionModel().getSelectedItem();
        if(apptToUpdate != null){
            try {
                // go to update appt page
                FXMLLoader load = new FXMLLoader(getClass().getResource("/View/UpdateAppointmentPage.fxml"));
                Parent scene = load.load();
                UpdateAppointmentPageController updateController = load.getController();
                updateController.initalizeData(apptToUpdate);

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(scene));
                stage.setTitle("Update Existing Appointment");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Updating appt error");
            alert.setContentText("No appt selected");
            alert.showAndWait();
        }
    }

    public void deleteAppt(ActionEvent actionEvent) throws SQLException {
        AppointmentsModel apptToDelete = (AppointmentsModel) appointments_Table.getSelectionModel().getSelectedItem();
        if(apptToDelete != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirm you want to delete this appointment?");
            Optional<ButtonType> confirm = alert.showAndWait();

            if(confirm.isPresent() && confirm.get() == ButtonType.YES){
                AppointmentsDAO.deleteExistingAppt(apptToDelete.getApptID());
                appointments_Table.refresh();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error deleting appt. ");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {
            appts = AppointmentsDAO.getAllAppointments();

            appointments_Table.setItems(appts);

            customerID_Column.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appointmentID_Column.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            title_Column.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
            description_Column.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
            location_Column.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
            contact_Column.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            type_Column.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            start_Column.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
            end_Column.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
            userID_Column.setCellValueFactory(new PropertyValueFactory<>("userID"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}