package Controller;
import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Model.CountriesModel;
import Model.DivisionsModel;
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

public class CreateCustomerPageController implements Initializable {

    @FXML
    private Label header;
    @FXML
    private Button backToCustomersMainPage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label postalLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label divisionLabel;
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField addressTextfield;
    @FXML
    private TextField phoneTextfield;
    @FXML
    private TextField postalTextfield;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> divisionComboBox;
    @FXML
    private Button addCustomerButton;
    private  ResourceBundle resourceBundle;

    public void goToCustomersMainPage(ActionEvent actionEvent) {
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

    public void addCustomer(ActionEvent actionEvent) {
       boolean allFieldsFilled = fieldsRequired(nameTextfield.getText(),addressTextfield.getText(),postalTextfield.getText(),phoneTextfield.getText());

       if(allFieldsFilled){
           try {
               CustomersDAO.createCustomer(nameTextfield.getText(),addressTextfield.getText(),phoneTextfield.getText(), DivisionsDAO.readDivID(divisionComboBox.getValue()).getDivisionID(),postalTextfield.getText());
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("customerCreated"));
               alert.showAndWait();
               goToCustomersMainPage(actionEvent);

           } catch (Exception e) {
               e.printStackTrace();
               Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("errorCreatingCustomer"));
           }
       }
    }

    public boolean fieldsRequired(String name, String address, String postal, String phone){
        if(name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("fillInAllFieldsErrorTitle"));
            alert.setContentText(resourceBundle.getString("fillInAllFieldsError"));
            alert.showAndWait();
            return false;
        } else if(countryComboBox.getSelectionModel().isEmpty()  || divisionComboBox.getSelectionModel().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("countryAndDivisionSelectionMissingTitle"));
            alert.setContentText(resourceBundle.getString("countryAndDivisionSelectionMissingTitleError"));
            alert.showAndWait();
            return false;
        }else return true;
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

        divisionComboBox.setItems(divisionList);
    }

    public void countrySelection(ActionEvent actionEvent) {
        String countrySelected = countryComboBox.getValue();
        if(countrySelected != null){
            filterDivisionComboBox(countrySelected);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());


        addCustomerButton.setText(resourceBundle.getString("add"));
        backToCustomersMainPage.setText(resourceBundle.getString("back"));
        header.setText(resourceBundle.getString("createCustomer"));
        nameLabel.setText(resourceBundle.getString("name"));
        phoneLabel.setText(resourceBundle.getString("phone"));
        addressLabel.setText(resourceBundle.getString("address"));
        postalLabel.setText(resourceBundle.getString("postal"));
        countryLabel.setText(resourceBundle.getString("country"));
        divisionLabel.setText(resourceBundle.getString("division"));

        //add countries to the dropdown
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<CountriesModel> countries = CountriesDAO.readAllCountries();;
            if (countries != null) {
                for (CountriesModel country: countries) {
                    countryList.add(country.getCountryName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        countryComboBox.setItems(countryList);


    }


}
