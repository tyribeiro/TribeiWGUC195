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
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the UI logic of the Customers main page. Handles navigation to other pages,
 * filtering of the table view, and displays, creates, updates and deletes customers.
 */

public class CustomersMainPageController implements Initializable {
    @FXML
    public Button goToApptsButton;
    @FXML
    public Label customersPageHeader;
    @FXML
    public Button goToReportsButton;
    @FXML
    public TableColumn<CustomersModel,String> nameColumn;
    @FXML
    public TableColumn<CustomersModel,String> addressColumn;
    @FXML
    public TableColumn<CustomersModel,Integer> customerIDColumn;
    @FXML
    public TableColumn<CustomersModel,String> postalColumn;
    @FXML
    public TableColumn<CustomersModel,Integer> divisionColumn;
    @FXML
    public TableColumn<CustomersModel,String> countryColumn;
    @FXML
    public TableColumn<CustomersModel,String> phoneColumn;
    @FXML
    public Button goToCreateCustomerButton;
    @FXML
    public Button goToUpdateCustomerButton;
    @FXML
    public Button goToDeleteCustomerButton;
    @FXML
    public TableView<CustomersModel> customersTable;

    private ResourceBundle resourceBundle;

    private static TableView<CustomersModel> staticCustomersTable;

    /**
     * This method updates the appointments_Table with information updated the updated appointments.
     *
     * @param table the table to be updated
     */
    public static void updateTableView(TableView<CustomersModel> table){
        //fill iin customer table
        try {
            List<CustomersModel> customers = CustomersDAO.readCustomers();
            table.setItems(FXCollections.observableArrayList(customers));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * This method navigates to the appointments page
     *
     * @param actionEvent action event that has occured (clicking the back/appointments button)
     */
    public void goToAppointmentsPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("appts"));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method navigates to the reports page
     * @param actionEvent action event that has occured (clicking the reports button)
     */
    public void goToReportsPage(ActionEvent actionEvent) {
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
     * This method navigates to the create customer page
     * @param actionEvent action event that has occured (clicking the create customer button)
     */
    public void goToCreateCustomerPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CreateCustomerPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("createCustomer");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method navigates to the update customer page
     * @param actionEvent action event that has occured (clicking the update customer button)
     */
    public void goToUpdateCustomerPage(ActionEvent actionEvent) {
        try {

            CustomersModel selectedCustomer = (CustomersModel) customersTable.getSelectionModel().getSelectedItem();
            if(selectedCustomer == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorUpdating"));
                alert.setContentText(resourceBundle.getString("noCustomersSelected"));
                alert.showAndWait();
                return;
            }

            FXMLLoader load = new FXMLLoader(getClass().getResource("/View/UpdateCustomerPage.fxml"));
            Parent scene = load.load();
            UpdateCustomerPageController controller = load.getController();
            controller.setCustomerTable(customersTable);
            controller.autopopulate(selectedCustomer);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("updateCustomer"));
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method navigates to the delete customer page
     * @param actionEvent action event that has occured (clicking the delete customer button)
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        CustomersModel customer = (CustomersModel) customersTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("confirmDelete"));
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                CustomersDAO.deleteCustomer(customer.getCustomerID());
                customersTable.getItems().remove(customer);
                customersTable.refresh();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("customerDeleted"));
                alert1.setTitle(resourceBundle.getString("customerDeleted"));
                alert1.setContentText(resourceBundle.getString("customerDeleted"));
                alert1.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("selectCustomer"));
            alert.showAndWait();
        }
    }


    /**
     * This method Initializes the controller and sets up colummns and buttons in the table and page.
     * @param url URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        nameColumn.setText(resourceBundle.getString("name"));
        customerIDColumn.setText(resourceBundle.getString("customerID"));
        addressColumn.setText(resourceBundle.getString("address"));
        postalColumn.setText(resourceBundle.getString("postal"));
        divisionColumn.setText(resourceBundle.getString("division"));
        countryColumn.setText(resourceBundle.getString("country"));
        phoneColumn.setText(resourceBundle.getString("phone"));

        //initializing the columns and buttons
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        goToCreateCustomerButton.setText(resourceBundle.getString("createCustomer"));
        goToUpdateCustomerButton.setText(resourceBundle.getString("updateCustomer"));
        goToDeleteCustomerButton.setText(resourceBundle.getString("deleteCustomer"));
        customersPageHeader.setText(resourceBundle.getString("appointments"));
        goToApptsButton.setText(resourceBundle.getString("back"));
        goToReportsButton.setText(resourceBundle.getString("reports"));

        updateTableView(customersTable);

    }


}
