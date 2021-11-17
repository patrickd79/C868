package C868;

import C868.Entities.User;
import C868.Helper.DBUser;
import C868.Helper.DataValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserController {
    @FXML
    public TextField updateUserName;
    @FXML
    public TextField updatePassword;
    @FXML
    public TextField currentPersonUserUpdatedByField;
    @FXML
    public Button updateUserBtn;
    @FXML
    public Label updateUserErrorField;
    @FXML
    public Label updateUserIDLabel;
    @FXML
    public Label updateUserCreatedBy;
    @FXML
    public Label updateUserCreatedOn;
    @FXML
    public Label updateUserLastUpdatedBy;
    @FXML
    public Label updateUserLastUpdateDate;
    public String userID;
    public CheckBox adminCheckBox;

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void populateUserData(String id) throws SQLException {
        int uid = Integer.parseInt(id);
        User user = DBUser.getAUserByID(uid);
        String userName = user.getUserName();
        updateUserIDLabel.setText(String.valueOf(user.getUserID()));
        updateUserName.setText(userName);
        if(user.isAdmin()){
            adminCheckBox.fire();
            updateUserName.setDisable(true);
            updateUserErrorField.setTextFill(Color.BLACK);
            updateUserErrorField.setText("Username cannot be changed.");
        }
        currentPersonUserUpdatedByField.setText(LoginController.user.getUserName());
        currentPersonUserUpdatedByField.setDisable(true);
        updatePassword.setText(user.getPassword());
        updateUserCreatedBy.setText(user.getCreatedBy());
        updateUserCreatedOn.setText(user.getCreatedDate());
        updateUserLastUpdatedBy.setText(user.getLastUpdatedBy());
        updateUserLastUpdateDate.setText(user.getLastUpdate());
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

    public void updateUser(ActionEvent event) throws Exception {
        String name = updateUserName.getText();
        String password = updatePassword.getText();
        String updatedBy = currentPersonUserUpdatedByField.getText();
        boolean admin = adminCheckBox.isSelected();
        if(validateFields( name,  password)) {
            try {
                DBUser.updateUser(userID, name, password, updatedBy, admin);
                updateUserErrorField.setTextFill(Color.BLACK);
                updateUserErrorField.setText("User Record Updated");
                updateUserBtn.setDisable(true);
            } catch (Exception exception) {
                updateUserErrorField.setTextFill(Color.RED);
                updateUserErrorField.setText("Please complete all fields");
                exception.printStackTrace();
            }
        }else{
            updateUserErrorField.setTextFill(Color.RED);
            updateUserErrorField.setText("Please complete all fields");
        }
    }

    public void initialize() throws SQLException {
        userID = ChooseUserToUpdateController.userID;
        System.out.println("USER ID update = " + userID);
        populateUserData(userID);
    }
}
