package C868;

import C868.Helper.DBUser;
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
    public TextField userCreatedByField;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Label addUserErrorField;
    public CheckBox adminCheckBox;

    public void addUser(ActionEvent event){
        try {
            DBUser.addUser(userNameField.getText(), userPasswordField.getText(), LoginController.user.getUserName(), adminCheckBox.isSelected());
            addUserErrorField.setTextFill(Color.BLACK);
            addUserErrorField.setText("User Record Created");
            addCustomerBtn.setDisable(true);
            JDBC.closeConnection();
        }catch(Exception exception){
            addUserErrorField.setTextFill(Color.RED);
            addUserErrorField.setText("Please complete all fields");
            exception.printStackTrace();
        }
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() {
        JDBC.openConnection();

    }
}
