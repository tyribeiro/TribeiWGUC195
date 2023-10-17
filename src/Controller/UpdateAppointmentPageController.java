package Controller;

import Model.AppointmentsModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentPageController implements Initializable {
  private AppointmentsModel appt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initalizeData(AppointmentsModel appt){
        this.appt = appt;
    }
}
