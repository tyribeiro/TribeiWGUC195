package Controller;

import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Model.CountriesModel;
import Model.CustomersModel;
import Model.DivisionsModel;
import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import Controller.CustomersMainPageController;
import Controller.CustomersMainPageController;
public class UpdateCustomerPageController implements Initializable {

    public Label updateCustomerTitle;
    public Label customerIDLabel;
    public Label customerNameLabel;
    public Label customerPhoneLabel;
    public Label customerAddressLabel;
    public Label customerPostalLabel;
    public Label customerCountryLabel;
    public Label customerDivisionLabel;
    public TextField customerIDTextfield;
    public TextField customerNameTextfield;
    public TextField customerPhoneTextfield;
    public TextField customerAddressTextfield;
    public TextField customerPostalTextfield;
    public ComboBox customerCountryComboBox;
    public ComboBox customerDivisionComboBox;
    public Button saveButton;
    public Button cancelButton;
    private CustomersModel customersModel;
    private ResourceBundle resourceBundle;
    private TableView<CustomersModel> customerTable;

    public void saveUpdateCustomer(ActionEvent actionEvent) {
        try {
            int ID = Integer.parseInt(customerIDTextfield.getText());
            String name = customerNameTextfield.getText();
            String address = customerAddressTextfield.getText();
            String phone = customerPhoneTextfield.getText();
            String postal = customerPostalTextfield.getText();
            String country = customerCountryComboBox.getSelectionModel().getSelectedItem().toString();
            String division = customerDivisionComboBox.getSelectionModel().getSelectedItem().toString();

            //update database
            boolean updated = CustomersDAO.updateCustomer(ID,name,phone,address,postal,division);

            if(updated){
                //update tableview
                CustomersMainPageController.updateTableView(customerTable);

                goBackToCustomerMainPage(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBackToCustomerMainPage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/CustomersMainPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle(resourceBundle.getString("customers"));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autopopulate(CustomersModel customer ){
        customerIDTextfield.setText(String.valueOf(customer.getCustomerID()));
        customerNameTextfield.setText(customer.getCustomerName());
        customerPhoneTextfield.setText(customer.getCustomerPhone());
        customerAddressTextfield.setText(customer.getCustomerAddress());
        customerPostalTextfield.setText(customer.getPostalCode());

        populateCountryComboBox();
        customerCountryComboBox.getSelectionModel().select(String.valueOf(customer.getCustomerCountry()));

        populateDivisionComboBox(customer.getCustomerCountry());
        customerDivisionComboBox.getSelectionModel().select(String.valueOf(customer.getCustomerDivision()));

    }

    public void populateCountryComboBox()
    {
        ObservableList<String> countries = CountriesDAO.readCountires();
        customerCountryComboBox.setItems(FXCollections.observableArrayList(countries));

    }
    public void populateDivisionComboBox(String country)
    {
        ObservableList<String> divisions = DivisionsDAO.readDivisionsByCountry(country);
        customerDivisionComboBox.setItems(FXCollections.observableArrayList(divisions));

    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        customerIDLabel.setText(resourceBundle.getString("customerID"));
        customerNameLabel.setText(resourceBundle.getString("name"));
        customerPhoneLabel.setText(resourceBundle.getString("phone"));
        customerAddressLabel.setText(resourceBundle.getString("address"));
        customerPostalLabel.setText(resourceBundle.getString("postal"));
        customerCountryLabel.setText(resourceBundle.getString("country"));
        customerDivisionLabel.setText(resourceBundle.getString("division"));
        saveButton.setText(resourceBundle.getString("save"));
        cancelButton.setText(resourceBundle.getString("cancel"));

        try {
            ObservableList<CountriesModel> countries = CountriesDAO.readAllCountries();
            customerCountryComboBox.setItems(FXCollections.observableArrayList(countries));

        }catch (Exception e){
            e.printStackTrace();
        }



    }
    private void filterDivisionComboBox(String chosenCountry){
        //add divisions to the drop down and filter it based on the country selected by user
        ObservableList<String> divisionList = DivisionsDAO.readDivisionsByCountry(chosenCountry);

        try {
            ObservableList<DivisionsModel> divisions = FXCollections.observableArrayList();
            if (divisions != null) {
                for (DivisionsModel division : divisions) {
                    divisionList.add(division.getDivisionName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerDivisionComboBox.setItems(divisionList);
    }

    public void countrySelection(ActionEvent actionEvent) {
        String countrySelected = String.valueOf(customerCountryComboBox.getValue());
        if(countrySelected != null){
            filterDivisionComboBox(countrySelected);
        }
    }

    public void setCustomerTable(TableView<CustomersModel> table) {
        this.customerTable = table;
    }
}
