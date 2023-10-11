package Controller;

import DAO.AppointmentsDAO;
import Model.AppointmentsModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.List;

public class AppointmentsPageController {
    public TableView AppointmentsTable;
    public TableColumn AppointmentID;
    public TableColumn Title;
    public TableColumn Location;
    public TableColumn Description;
    public TableColumn Type;
    public TableColumn Contact;
    public TableColumn StartDateTime;
    public TableColumn CustomerID;
    public TableColumn EndDateTime;
    public TableColumn UserID;
    public Button CreateAppointmentButton;
    public Button UpdateCustomerButton;
    public Button DeleteAppointmentButton;
    public Label Header;
    public Button CustomersButton;
    public RadioButton AllRadioButton;
    public ToggleGroup ToggleView;
    public RadioButton WeekRadioButton;
    public RadioButton MonthRadioButton;
    public Button SearchButton;
    public TextField SearchTextField;
    public Button ReportsButton;

    static ObservableList <AppointmentsModel> appts;
    public void createAppointment (){
        
    }
    
    public  void updateAppointment(){
        
    }
    
    public void deleteAppointment(){
        
    }

    public void goToCustomers(ActionEvent actionEvent) {
    }

    public void toggleView(ActionEvent actionEvent) {
        if(AllRadioButton.isSelected()){
            try {
                appts = (ObservableList<AppointmentsModel>) AppointmentsDAO.readAllAppts();
                AppointmentsTable.setItems(appts);
                AppointmentsTable.refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void searchAppointments(ActionEvent actionEvent) {
    }

    public void goToReportsPage(ActionEvent actionEvent) {
    }
}
