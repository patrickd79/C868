package C868;

import C868.Entities.Customer;
import C868.Helper.DBCustomer;
import C868.Helper.DataValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerUpdateController {
    @FXML
    public Label updateCustIDLabel;
    @FXML
    public TextField updateCustNameField;
    @FXML
    public TextField updateCustAddressField;
    @FXML
    public TextField updateCustPostalCodeField;
    @FXML
    public TextField updateCustPhoneField;
    @FXML
    public Button updateCustomerBtn;
    @FXML
    public Label updateCustErrorField;
    @FXML
    public Label updateCustCreatedBy;
    @FXML
    public Label updateCustCreatedOn;
    @FXML
    public Label updateCustLastUpdatedBy;
    @FXML
    public Label updateCustLastUpdateDate;
    private String customerID;

    /**
     * Updates the selected customer's record in the database.
     * @param event
     * @throws Exception
     */
    public void updateCustomer(ActionEvent event) throws Exception {
        String name = updateCustNameField.getText();
        String address = updateCustAddressField.getText();
        String postalCode = updateCustPostalCodeField.getText();
        String phone = updateCustPhoneField.getText();
        String updatedBy = LoginController.user.getUserName();
        if (validateFields(name, address, postalCode, phone)) {
            try {
                DBCustomer.updateCustomer(customerID, name, address, postalCode, phone, updatedBy);
                updateCustErrorField.setTextFill(Color.BLACK);
                updateCustErrorField.setText("Customer Record Updated");
                updateCustomerBtn.setDisable(true);
            } catch (Exception exception) {
                updateCustErrorField.setTextFill(Color.RED);
                updateCustErrorField.setText("Please complete all fields");
                exception.printStackTrace();
            }
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

    /**
     * Sets all of the text fields with the data of the selected customer.
     * @param customerID
     */
    public void populateCustomerData(String customerID) throws SQLException {
        int cid = Integer.parseInt(customerID);
        Customer customer = DBCustomer.getACustomerByID(cid);
        updateCustIDLabel.setText(String.valueOf(customer.getCustomer_ID()));
        updateCustNameField.setText(customer.getCustomer_Name());
        updateCustAddressField.setText(customer.getAddress());
        updateCustPostalCodeField.setText(customer.getPostalCode());
        updateCustPhoneField.setText(customer.getPhone());
        updateCustCreatedBy.setText(customer.getCreatedBy());
        updateCustCreatedOn.setText(customer.getCreatedDate());
        updateCustLastUpdatedBy.setText(customer.getLastUpdatedBy());
        updateCustLastUpdateDate.setText(customer.getLastUpdate());
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() throws SQLException {
        customerID = ChooseCustomerToUpdateController.customerID;
        System.out.println("CUSTOMER ID update = " + customerID);
        populateCustomerData(customerID);
    }


}
