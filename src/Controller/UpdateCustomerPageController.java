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
import javafx.fxml.FXML;
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

/**
 * This class lets the user choose a customer to update, enter the new information and update the database.
 */
public class UpdateCustomerPageController implements Initializable {
    @FXML
    public Label updateCustomerTitle;
    @FXML
    public Label customerIDLabel;
    @FXML
    public Label customerNameLabel;
    @FXML
    public Label customerPhoneLabel;
    @FXML
    public Label customerAddressLabel;
    @FXML
    public Label customerPostalLabel;
    @FXML
    public Label customerCountryLabel;
    @FXML
    public Label customerDivisionLabel;
    @FXML
    public TextField customerIDTextfield;
    @FXML
    public TextField customerNameTextfield;
    @FXML
    public TextField customerPhoneTextfield;
    @FXML
    public TextField customerAddressTextfield;
    @FXML
    public TextField customerPostalTextfield;
    @FXML
    public ComboBox customerCountryComboBox;
    @FXML
    public ComboBox customerDivisionComboBox;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;
    private CustomersModel customersModel;
    private ResourceBundle resourceBundle;
    @FXML
    private TableView<CustomersModel> customerTable;

    /**
     * This method handles the save button. If the appointment is successfully updated in the database, then it updates the table in the customers main page and returns to that page.
     *
     * @param actionEvent action event that has occured (clicking the save button)
     */
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

    /**
     * This method navigates back to the customers main page.
     * @param actionEvent
     */
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

    /**
     * This method autopopulates all the fields on this page with the information of the customer to update
     * @param customer The customer to be updated.
     */
    public void autopopulate(CustomersModel customer ){
        customerIDTextfield.setText(String.valueOf(customer.getCustomerID()));
        customerNameTextfield.setText(customer.getCustomerName());
        customerPhoneTextfield.setText(customer.getCustomerPhone());
        customerAddressTextfield.setText(customer.getCustomerAddress());
        customerPostalTextfield.setText(customer.getPostalCode());

        populateCountryComboBox(customer);
        customerCountryComboBox.getSelectionModel().select(String.valueOf(customer.getCustomerCountry()));

        populateDivisionComboBox(customer.getCustomerCountry());
        customerDivisionComboBox.getSelectionModel().select(String.valueOf(customer.getCustomerDivision()));

    }

    /**
     * This method populates the country combo box selector with the selected customers current country.
     * @param customer Selected customer to update
     */
    public void populateCountryComboBox(CustomersModel customer)
    {
        ObservableList<String> countries = CountriesDAO.readCountires();
        customerCountryComboBox.setValue(customer.getCustomerCountry());
        customerCountryComboBox.setItems(FXCollections.observableArrayList(countries));

    }

    /**
     * This method populates the division combo box selector with divisions based on country selected.
     * @param country The current country selected of the customer
     */
    public void populateDivisionComboBox(String country)
    {
        ObservableList<String> divisions = DivisionsDAO.readDivisionsByCountry(country);
        customerDivisionComboBox.setItems(FXCollections.observableArrayList(divisions));

    }

    /**
     * This method initializes all UI elements on the page and populates the countries in the country combobox.
     * @param url URL
     * @param resources Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());
        updateCustomerTitle.setText(resourceBundle.getString("updateCustomer"));
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

    /**
     * This method filters the division combo box drop down options based on the country selected.
     * @param chosenCountry Country selected in the country combobox drop down selector.
     */
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

    /**
     * This method gets the current country selected in the combo box value
     * @param actionEvent action event that has occured (selecting a country)
     */
    public void countrySelection(ActionEvent actionEvent) {
        String countrySelected = String.valueOf(customerCountryComboBox.getValue());
        if(countrySelected != null){
            filterDivisionComboBox(countrySelected);
        }
    }

    /**
     * This method sets the customer table view.
     * @param table Tableview for customers.
     */
    public void setCustomerTable(TableView<CustomersModel> table) {
        this.customerTable = table;
    }
}
