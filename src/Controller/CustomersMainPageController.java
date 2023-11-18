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
import java.util.ResourceBundle;

public class CustomersMainPageController implements Initializable {
    public Button goToApptsButton;
    public Label customersPageHeader;
    public Button goToReportsButton;
    public TableColumn<CustomersModel,String> nameColumn;
    public TableColumn<CustomersModel,String> addressColumn;
    public TableColumn<CustomersModel,Integer> customerIDColumn;
    public TableColumn<CustomersModel,String> postalColumn;
    public TableColumn<CustomersModel,Integer> divisionColumn;
    public TableColumn<CustomersModel,String> countryColumn;
    public TableColumn<CustomersModel,String> phoneColumn;
    public Button goToCreateCustomerButton;
    public Button goToUpdateCustomerButton;
    public Button goToDeleteCustomerButton;

    @FXML
    public TableView<CustomersModel> customersTable;

    private ResourceBundle resourceBundle;

    private static TableView<CustomersModel> staticCustomersTable;
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

    public void goToDeleteCustomerPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/DeleteCustomersPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("deleteCustomer"));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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

        updateTableView(customersTable);

    }


}
