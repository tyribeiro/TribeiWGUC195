package Controller;

import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TotalApptReport implements Initializable {

    public ResourceBundle resourceBundle;
    public Label totalApptHeader;
    public Button generateButton;
    public TableView totalApptsTable;
    public RadioButton typeRadioButton;
    public RadioButton monthRadioButton;
    public TableColumn<AppointmentsModel, Integer> apptID;
    public TableColumn<AppointmentsModel, String> apptTitle;
    public Label totalApptsLabel;

    @FXML
    public ToggleGroup toggle = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        toggle = new ToggleGroup();
        typeRadioButton.setToggleGroup(toggle);
        monthRadioButton.setToggleGroup(toggle);

        apptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));

        typeRadioButton.setOnAction(actionEvent -> updateTable("type"));
        monthRadioButton.setOnAction(actionEvent -> updateTable("month"));

    }

    private void updateTable(String selection) {
        try {
            ObservableList<AppointmentsModel> tableData;
            if ("type".equals(selection)) {
                tableData = AppointmentsDAO.apptsType();
            } else {
                tableData = AppointmentsDAO.apptsMonth();
            }

            totalApptsTable.setItems(tableData);
            totalApptsTable.refresh();
            totalApptsLabel.setText("Total Appointments: " + tableData.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

