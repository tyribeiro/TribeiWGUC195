package Controller;

import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
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
import java.util.ResourceBundle;

public class AppointmentsPageController implements Initializable {

    public TableView appointments_Table;
    public TableColumn appointmentID_Column;
    public TableColumn  title_Column;
    public TableColumn description_Column;
    public TableColumn location_Column;
    public TableColumn contact_Column;
    public TableColumn type_Column;
    public TableColumn start_Column;
    public TableColumn end_Column;
    public TableColumn startTime_Column;
    public TableColumn endTime_Column;
    public TableColumn  customerID_Column;
    public TableColumn  userID_Column;
    public Button CreateAppointmentButton;
    public Button UpdateAppointmentButton;
    public Button DeleteAppointmentButton;
    public Label Header;
    public Button CustomersButton;
    public RadioButton allAppointmentsButton;
    public RadioButton byMonthButton;
    public RadioButton byWeekButton;
    public Button SearchButton;
    public TextField SearchTextField;
    public Button ReportsButton;

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
        if(allAppointmentsButton.isSelected()){
            try {
                appts = AppointmentsDAO.getAllAppointments();
                appointments_Table.setItems(appts);
                appointments_Table.refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void searchAppointments(ActionEvent actionEvent) {
    }

    public void createAppointment(ActionEvent actionEvent){

    }

    public void updateAppointment(ActionEvent actionEvent){

    }

    public void deleteAppointment(ActionEvent actionEvent){

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
            start_Column.setCellValueFactory(new PropertyValueFactory<>("apptStartDate"));
            startTime_Column.setCellValueFactory(new PropertyValueFactory<>("apptStartTime"));
            end_Column.setCellValueFactory(new PropertyValueFactory<>("apptEndDate"));
            endTime_Column.setCellValueFactory(new PropertyValueFactory<>("apptEndTime"));
            userID_Column.setCellValueFactory(new PropertyValueFactory<>("userID"));
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}