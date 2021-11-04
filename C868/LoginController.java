package C868;

import C868.Helper.TimeZones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    public Label scheduleLogInLabel;
    @FXML
    public Label languageDisplayLabel;
    @FXML
    public  TextField userNameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorMessageLabel;
    @FXML
    public Button logInBtn;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label passwordLabel;
    public static String thisUser;

    public LoginController(){
    }

    /**
     * Displays the user's language setting
     */
    public void setLanguageDisplayLabel(){
        languageDisplayLabel.setText(Main.zoneID + "   " +Main.zone + " : " + Main.userLanguage + " : " + Main.userCountry);
    }

    /**
     * Sets the information to display in the fields based on the user's language setting.
     */
    public void setLabels(){
        scheduleLogInLabel.setText(Main.resourceBundle.getString("SchedulingApplication"));
        logInBtn.setText(Main.resourceBundle.getString("login"));
        userNameLabel.setText(Main.resourceBundle.getString("username"));
        passwordLabel.setText(Main.resourceBundle.getString("password"));
    }

    /**
     * Verifies that the log in information is valid and logs the user in if it is valid. If not, it displays
     * an error message.
     * @param event
     * @throws IOException
     */
    public void logInAttempt(ActionEvent event) throws IOException {
        thisUser = userNameField.getText();
        String loginMessage;
        //System.out.println(thisUser);
        if(verifyLogIn(event)){
           loginMessage = getLoginInfo(thisUser, "Successful");
           loginLogging(loginMessage);
            changeScene("mainMenu.fxml", event);
        }else {
            loginMessage = getLoginInfo(thisUser, "Unsuccessful");
            loginLogging(loginMessage);
            errorMessageLabel.setText(Main.resourceBundle.getString("IncorrectUsernameOrPassword"));
                logInBtn.setText(Main.resourceBundle.getString("Retry"));
            }
    }

    /**
     * Writes the log in information to the login activity text file.
     * @param message
     * @throws IOException
     */
    public void loginLogging(String message) throws IOException {
        File file = new File("login_activity.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(message);
        printWriter.println(System.lineSeparator());
        printWriter.close();
    }

    /**
     *
     * @param user
     * @param result
     * @return Returns a string with the log in information so that it can be logged.
     */
    public String getLoginInfo(String user, String result){
        String timeStamp = TimeZones.getUTCTime();
        return "Login Attempt  "+"User: "+user+", Result: "+result+", Timestamp: "+timeStamp +" UTC";
    }

    /**
     * Verifies that the username and password are valid.
     * @param e
     * @return
     * @throws IOException
     */
    public boolean verifyLogIn(ActionEvent e) throws IOException {
        return userNameField.getText().equals("test") &&
                passwordField.getText().equals("test");
    }

    public void changeScene(String s, ActionEvent e) throws IOException {
        Parent newWindow = FXMLLoader.load(getClass().getResource(s));
        Scene newScene = new Scene(newWindow);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguageDisplayLabel();
        setLabels();
        System.out.println(TimeZones.getDayOfWeekEST("2021-10-19 08:00:00"));
    }
}
