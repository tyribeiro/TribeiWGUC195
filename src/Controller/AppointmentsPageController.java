package Controller;

import DAO.AppointmentsDAO;
import DAO.CustomersDAO;
import Model.AppointmentsModel;
import Model.CustomersModel;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Controls the UI logic of the appointment page. Handles navigation to other pages,
 * filtering of the table view, and displays, creates, updates and deletes appointments.
 */

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
    public TableColumn<AppointmentsModel, LocalDate> startDate_Column;
    @FXML
    public TableColumn<AppointmentsModel, LocalDate> endDate_Column;
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
    public Button ReportsButton;
    @FXML
    static ObservableList<AppointmentsModel> appts;
    @FXML
    public Label filterLabel;
    @FXML
    public Label actionsLabel;
    @FXML
    private ToggleGroup ToggleView;

    private ResourceBundle resourceBundle;

    /**
     * This method updates the appointments_Table with information updated the updated appointments.
     */
    public static void updateTableView(TableView<AppointmentsModel> table) {
        //refresh table
        try {
            List<AppointmentsModel> appts = AppointmentsDAO.getAllAppointments();
            table.setItems(FXCollections.observableArrayList(appts));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * This method navigates to the main Customers Page.
     *
     * @param actionEvent action event that has occured (clicking the customers button)
     */
    public void goToCustomersPage(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CustomersMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("customers"));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method navigates to the Reports page.
     *
     * @param actionEvent action event that has occured (clicking the reports button)
     */
    public void goToReportsPage(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/ReportsPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("reports"));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * This method toggles the view of appointments based on the selected radio button for all, week or month.
     * It will update the tableview based on the selection.
     * @param actionEvent action event that has occured (clicking the radiobuttons)
     */
    public void viewToggle(ActionEvent actionEvent){
        try {

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

    /**
     * This method navigates  to the create appointment page.
     * @param actionEvent action event that has occured (clicking the customers button)
     */
    public void createAppt(ActionEvent actionEvent){
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateAppointmentPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("createAppt"));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method navigates to the update appointment page and handles any errors in terms of an appointment  not being selected to update or any null appointments.`
     * @param actionEvent action event that has occured (clicking the update appointment button)
     */
    public void updateAppt(ActionEvent actionEvent){
        try {
            AppointmentsModel apptSelected = appointments_Table.getSelectionModel().getSelectedItem();

            if (apptSelected == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("errorUpdating"));
                    alert.setContentText(resourceBundle.getString("noApptSelected"));
                    alert.showAndWait();
                return;
            }

            int apptID = apptSelected.getApptID();
            AppointmentsModel apptToUpdate = AppointmentsDAO.getApptsByID(apptID);
            if (apptToUpdate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "NULL APPT");
                return;
            }
                // go to update appt page
                FXMLLoader load = new FXMLLoader(getClass().getResource("/View/UpdateAppointmentPage.fxml"));
                Parent scene = load.load();
                UpdateAppointmentPageController updateController = load.getController();
                updateController.autopopulate(apptToUpdate);

                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(scene));
                stage.setTitle(resourceBundle.getString("updateAppt"));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle(resourceBundle.getString("updateAppt"));
//            alert.setContentText(resourceBundle.getString("apptUpdated"));
//            alert.showAndWait();
    }


    /**
     * This method deletes an appointment from the database.
     * It first confirms with user if they want to delete the selected appointment,
     * then it does the delete operation from the DAO,
     * then it refreshes the table view to display the updated appointments which no longer has the appointment that was deleted.
     * it alsp send the user an alert with information about the appointment that was deleted.
     * @param actionEvent action event that has occured (clicking the delete appointment button)
     * @throws SQLException if there is an error communicating with the database
     */
    public void deleteAppt(ActionEvent actionEvent) throws SQLException {
        AppointmentsModel apptToDelete = (AppointmentsModel) appointments_Table.getSelectionModel().getSelectedItem();
        if(apptToDelete != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,resourceBundle.getString("confirmDelete"));
            alert.setTitle(resourceBundle.getString("confirmDelete"));
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                AppointmentsDAO.deleteExistingAppt(apptToDelete.getApptID());
                appointments_Table.getItems().remove(apptToDelete);
                appointments_Table.refresh();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("apptDeleted"));
                alert1.setTitle(resourceBundle.getString("apptDeleted"));
                alert1.setContentText(resourceBundle.getString("apptDeleted") + "\n" + resourceBundle.getString("appointmentID") + ": " + apptToDelete.getApptID() + "\n" + resourceBundle.getString("type") + ": " + apptToDelete.getApptType());
                alert1.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("errorDeletingAppt"));
            alert.showAndWait();
        }
    }

    /**
     * This method Initializes the controller and sets up colummns in the table.
     * @param url URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources){
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        appointmentID_Column.setText(resourceBundle.getString("appointmentID"));
        title_Column.setText(resourceBundle.getString("title"));
        description_Column.setText(resourceBundle.getString("description"));
        location_Column.setText(resourceBundle.getString("location"));
        contact_Column.setText(resourceBundle.getString("contact"));
        type_Column.setText(resourceBundle.getString("type"));
        startDate_Column.setText(resourceBundle.getString("startDate"));
        startTime_Column.setText(resourceBundle.getString("startTime"));
        endDate_Column.setText(resourceBundle.getString("endDate"));
        endTime_Column.setText(resourceBundle.getString("endTime"));
        customerID_Column.setText(resourceBundle.getString("customerID"));
        userID_Column.setText(resourceBundle.getString("userID"));


        CustomersButton.setText(resourceBundle.getString("customers"));
        ReportsButton.setText(resourceBundle.getString("reports"));

        CreateAppointmentButton.setText(resourceBundle.getString("createAppt"));
        UpdateAppointmentButton.setText(resourceBundle.getString("updateAppt"));
        DeleteAppointmentButton.setText(resourceBundle.getString("deleteAppt"));

        allAppointmentsButton.setText(resourceBundle.getString("allAppts"));
        byMonthButton.setText(resourceBundle.getString("byMonth"));
        byWeekButton.setText(resourceBundle.getString("byWeek"));
        Header.setText(resourceBundle.getString("appointments"));
        filterLabel.setText(resourceBundle.getString("filterLabel"));
        actionsLabel.setText(resourceBundle.getString("actionsLabel"));


        try {
            appts = AppointmentsDAO.getAllAppointments();

            appointments_Table.setItems(appts);

            appointmentID_Column.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            title_Column.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
            description_Column.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
            location_Column.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
            contact_Column.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            type_Column.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            startDate_Column.setCellValueFactory(new PropertyValueFactory<AppointmentsModel, LocalDate>("startDate"));
            endDate_Column.setCellValueFactory(new PropertyValueFactory<AppointmentsModel, LocalDate>("endDate"));
            startTime_Column.setCellValueFactory(new PropertyValueFactory<AppointmentsModel, LocalTime>("startTime"));
            endTime_Column.setCellValueFactory(new PropertyValueFactory<AppointmentsModel, LocalTime>("endTime"));
            customerID_Column.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            userID_Column.setCellValueFactory(new PropertyValueFactory<>("userID"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}