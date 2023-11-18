import DAO.AppointmentsDAO;
import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import Helper.DBConnecter;
import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
import Model.CustomersModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        DBConnecter.getConnection();
        launch(args);
        DBConnecter.closeConnection();

    }

    public void start(Stage primary) throws IOException, SQLException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/LoginPage.fxml"));
            primary.setTitle("Appointment Scheduler");
            primary.setScene(new Scene(root,500,400));
            primary.show();
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error loading screen.");
            alert.showAndWait();
        }
    }
}

