package C868;

import C868.Helper.DBCustomer;
import C868.Helper.DataValidation;
import C868.Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AddCustomerController {
    @FXML
    public TextField custNameField;
    @FXML
    public TextField custAddressField;
    @FXML
    public TextField custPostalCodeField;
    @FXML
    public TextField custPhoneField;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Label addCustErrorField;

    /**
     * Adds a customer record to the database.
     * @param event
     */
    public void addCustomer(ActionEvent event){
        try {
            String name = custNameField.getText();
            String address = custAddressField.getText();
            String zip = custPostalCodeField.getText();
            String phone = custPhoneField.getText();
            if(validateFields(name, address, zip, phone)) {
                DBCustomer.addCustomer(name, address, zip, phone,
                        LoginController.thisUser);
                addCustErrorField.setTextFill(Color.BLACK);
                addCustErrorField.setText("Customer Record Created");
                addCustomerBtn.setDisable(true);
                JDBC.closeConnection();
            }
        }catch(Exception exception){
            addCustErrorField.setTextFill(Color.RED);
            addCustErrorField.setText("Please complete all fields");
            exception.printStackTrace();
        }
    }

    public boolean validateFields(String name, String address, String zip, String phone){
        if(DataValidation.isValidName(name) && DataValidation.isValidAddress(address) &&
                DataValidation.isValidPostalCode(zip) && DataValidation.isValidPhoneNumber(phone)){
            return true;
        }else if (!DataValidation.isValidName(name)){
            DataValidation.entryErrorAlert("Name");
        }else if (!DataValidation.isValidAddress(address)){
            DataValidation.entryErrorAlert("Address");
        }else if (!DataValidation.isValidPostalCode(zip)){
            DataValidation.entryErrorAlert("Postal Code");
        }else if (!DataValidation.isValidPhoneNumber(phone)){
            DataValidation.entryErrorAlert("Phone");
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
