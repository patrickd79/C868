package C868;

import C868.Entities.AdminUser;
import C868.Entities.User;
import C868.Helper.DBUser;
import C868.Helper.JDBC;
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
    public static String thisUserID;
    public static User user;
    public LoginController(){
    }

    /**
     * Sets the information to display in the fields based on the user's language setting.
     */
    public void setLabels(){
        scheduleLogInLabel.setText("SchedulingApplication");
        logInBtn.setText("login");
        userNameLabel.setText("Username");
        passwordLabel.setText("Password");
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
            errorMessageLabel.setText("IncorrectUsernameOrPassword");
                logInBtn.setText("Retry");
                JDBC.closeConnection();
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
    String userName;
    String passwordEntered;
    String validPassword;

    /**
     * Verifies that the username and password are valid.
     * @param e
     * @return
     * @throws IOException
     */
    public boolean verifyLogIn(ActionEvent e) throws IOException {
       getUserInput();
        return passwordEntered.equals(validPassword);
    }

    public void getUserInput(){
        JDBC.openConnection();
        userName = userNameField.getText();
        passwordEntered = passwordField.getText();
        user = DBUser.getAUserByName(userName);
        thisUserID = String.valueOf(user.getUserID());
        validPassword = user.getPassword();
        if(user.isAdmin()){
            user = new AdminUser(user.getUserID(),user.getUserName(),user.getPassword(),
                     user.getCreatedDate(),  user.getCreatedBy(),  user.getLastUpdate(),  user.getLastUpdatedBy(),  true);
        }
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
        setLabels();
    }
}
