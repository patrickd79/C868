package C868;

import C868.Helper.DBCustomer;
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
            DBCustomer.addCustomer(custNameField.getText(), custAddressField.getText(),
                    custPostalCodeField.getText(), custPhoneField.getText(),
                    LoginController.thisUser);
            addCustErrorField.setTextFill(Color.BLACK);
            addCustErrorField.setText("Customer Record Created");
            addCustomerBtn.setDisable(true);
            JDBC.closeConnection();
        }catch(Exception exception){
            addCustErrorField.setTextFill(Color.RED);
            addCustErrorField.setText("Please complete all fields");
            exception.printStackTrace();
        }
    }

    public static void checkInputs(){
            //w
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() {
        JDBC.openConnection();
    }
}
