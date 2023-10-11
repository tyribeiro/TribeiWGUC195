package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginPageController implements Initializable {
    public Label Description;
    public Label Title;
    public Label UsernameLabel;
    public Label PasswordLabel;
    public TextField UsernameTextField;
    public TextField PasswordTextField;
    public Label LocationLabel;
    public Label TimezoneLabel;
    public Label LocationText;
    public Button LoginButton;
    public Label TimezoneText;
    public Button CancelButton;

    private ResourceBundle resourceBundle;

    @FXML
    private void validateLogin(){


    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        resourceBundle = ResourceBundle.getBundle("Resource/Language/language", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
            Title.setText(resourceBundle.getString("title"));
            Description.setText(resourceBundle.getString("description"));
            UsernameLabel.setText(resourceBundle.getString("userName"));
            PasswordLabel.setText(resourceBundle.getString("password"));
            LocationLabel.setText(resourceBundle.getString("location"));
            LocationText.setText(resourceBundle.getString("country"));
            TimezoneLabel.setText(resourceBundle.getString("timezone"));
            TimezoneText.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            LoginButton.setText(resourceBundle.getString("login"));
            CancelButton.setText(resourceBundle.getString("cancel"));



        }
    }
}
