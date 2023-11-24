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

/**
 * The Main class extends the Application class from JavaFX and is the beginning to the app.
 * It initializes the database connection and sets up the login page.
 */
public class Main extends Application {

    /**
     * Main method that launches the app.
     * Connects the database , opens the connection when app is ran, and closes the connection when app is exited.
     *
     * @param args arguments
     * @throws Exception if an error occurs.
     */
    public static void main(String[] args) throws Exception {
        DBConnecter.getConnection();
        launch(args);
        DBConnecter.closeConnection();

    }

    /**
     * Starts the application's initial view.
     * Try-Catch block handles any erros and alerts user if there is an issue loading the new page.
     *
     * @param primary Primary stage for the app.
     * @throws IOException  if an I/O error occurs.
     * @throws SQLException if database access error occurs.
     */
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

