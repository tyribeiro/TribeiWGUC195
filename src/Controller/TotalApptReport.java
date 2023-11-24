package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class TotalApptReport implements Initializable {
    public Label totalApptHeader;
    public TableColumn monthCol;
    public TableColumn typeCol;
    public TableColumn totalApptsCol;
    public ResourceBundle resourceBundle;
    @FXML
    private TableView<?> totalApptsTable;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}

