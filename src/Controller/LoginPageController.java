package Controller;

import DAO.AppointmentsDAO;
import DAO.UsersDAO;
import Model.AppointmentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

interface ActivityLog {
    public String getFileName();
}
public class LoginPageController implements Initializable {
    //lambda expression
    ActivityLog activitylog = () -> {
        return "activity.txt";
    };
    public Label Description;
    public Label Title;
    public Label Username_Label;
    public Label Password_Label;
    public TextField Username_TextField;
    public TextField Password_TextField;
    public Label Location_Label;
    public Label Timezone_Label;
    public Label Location_Text;
    public Button Login_Button;
    public Label Timezone_Text;
    public Button Cancel_Button;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());


        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
            Title.setText(resourceBundle.getString("title"));
            Description.setText(resourceBundle.getString("description"));
            Username_Label.setText(resourceBundle.getString("userName"));
            Password_Label.setText(resourceBundle.getString("password"));
            Location_Label.setText(resourceBundle.getString("location"));
            Location_Text.setText(resourceBundle.getString("country"));
            Timezone_Label.setText(resourceBundle.getString("timezone"));
            Timezone_Text.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            Login_Button.setText(resourceBundle.getString("login"));
            Cancel_Button.setText(resourceBundle.getString("cancel"));

        }
    }


    public void validateLogin(javafx.event.ActionEvent actionEvent) {
        if(Username_TextField.getText().isEmpty() && Password_TextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorDialog"));
            alert.setContentText(resourceBundle.getString("usernameAndPasswordRequired"));
            alert.showAndWait();
        }else{
            if(Username_TextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorDialog"));
                alert.setContentText(resourceBundle.getString("usernameRequired"));
                alert.showAndWait();
            }
            if(Password_TextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("errorDialog"));
                alert.setContentText(resourceBundle.getString("passwordRequired"));
                alert.showAndWait();
            }
        }

        try {
            File file = new File(activitylog.getFileName());
            if(file.createNewFile()){
                System.out.println("File Created:" + file.getName());
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        try{
            boolean isValid = UsersDAO.validateUsernamePassword(Username_TextField.getText(),Password_TextField.getText());
            if(isValid){
                try {
                    FileWriter write = new FileWriter(activitylog.getFileName(),true);
                    SimpleDateFormat dateandtime = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
                    Date currentDate = new Date(System.currentTimeMillis());
                    write.write("Login Success. Username = " + Username_TextField.getText() + "Date and Time = " + dateandtime.format(currentDate) + " Timezone: " + Timezone_Text.getText() + "\n" );
                    write.close();
                }catch (Exception e ){
                    e.printStackTrace();
                }
                upcomingAlertAppointment();

                try {
                    System.out.println("inside try appt page login");
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/View/AppointmentMainPage.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Appointments");
                    stage.show();

                 }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                try {
                    FileWriter write = new FileWriter(activitylog.getFileName(),true);
                    SimpleDateFormat dateandtime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Date currentDate = new Date(System.currentTimeMillis());
                    write.write("Login Failure. Attempted Username = " + Username_TextField.getText() + "    -----  Attempted Password = " + Password_TextField.getText()  + "   ------ Date and Time = " + dateandtime.format(currentDate) + "   -----  Timezone: " + Timezone_Text.getText() + "\n" );
                    write.close();
                }catch (Exception e ){
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void upcomingAlertAppointment() throws SQLException {
        LocalTime currentDateTime = LocalTime.now();
        LocalTime minutes15 = currentDateTime.plusMinutes(15);

        ObservableList<AppointmentsModel> upcomingAppointments = FXCollections.observableArrayList();

        List<AppointmentsModel> appointments = AppointmentsDAO.getAllAppointments();

        if(appointments != null){
            for(AppointmentsModel appointment : appointments){
                if(appointment.getApptStartTime().isAfter(currentDateTime) && appointment.getApptStartTime().isBefore(minutes15)){
                    upcomingAppointments.add(appointment);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("appointmentAlert"));
                    alert.setContentText(
                            resourceBundle.getString("lessThanFifteen") + "\n" + appointment.getApptTitle() + "\n" + resourceBundle.getString("appointmentID") + " " + appointment.getApptID() + "\n" + resourceBundle.getString("date") + " " + appointment.getApptStartDate() + " \n" + resourceBundle.getString("time") + " " + appointment.getApptStartTime());
                            alert.showAndWait();
                }
            }
        }

        if(upcomingAppointments.size() < 1){
            if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(resourceBundle.getString("appointmentAlert"));
                alert.setContentText(resourceBundle.getString("noUpcomingAppointments"));
                alert.showAndWait();
            }
        }
    }

    public void closeApp(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("cancelConfirmation"));
        alert.showAndWait();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
