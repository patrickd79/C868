package C868;

import C868.Helper.DBUser;
import C868.Helper.DataValidation;
import C868.Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AddUserController {
    @FXML
    public TextField userNameField;
    @FXML
    public TextField userPasswordField;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Label addUserErrorField;
    public CheckBox adminCheckBox;

    public void addUser(ActionEvent event){
        try {
            String userName = userNameField.getText();
            String password = userPasswordField.getText();
            if(validateFields(userName, password)) {
                DBUser.addUser(userName, password, LoginController.user.getUserName(),
                        adminCheckBox.isSelected());
                addUserErrorField.setTextFill(Color.BLACK);
                addUserErrorField.setText("User Record Created");
                addCustomerBtn.setDisable(true);
                JDBC.closeConnection();
            }
        }catch(Exception exception){
            addUserErrorField.setTextFill(Color.RED);
            addUserErrorField.setText("Please complete all fields");
            exception.printStackTrace();
        }
    }

    public boolean validateFields(String userName, String password){
        if(DataValidation.isValidUserName(userName) && DataValidation.isValidPassword(password)){
            return true;
        }else if (!DataValidation.isValidUserName(userName)){
            DataValidation.entryErrorAlert("User Name");
        }else if (!DataValidation.isValidPassword(password)){
            DataValidation.entryErrorAlert("Password");
        }
        return false;
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() {
        JDBC.openConnection();
    }
}
