package C868;

import C868.Entities.Country;
import C868.Entities.Customer;
import C868.Helper.DBCountries;
import C868.Helper.DBCustomer;
import C868.Helper.DBFirstLevDiv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class CustomerUpdateController {
    @FXML
    public Label updateCustIDLabel;
    @FXML
    public ComboBox<String> updateCustomerComboDivId;
    @FXML
    public ComboBox<String> updateCustomerComboCountry;
    @FXML
    public TextField updateCustNameField;
    @FXML
    public TextField updateCustAddressField;
    @FXML
    public TextField updateCustPostalCodeField;
    @FXML
    public TextField updateCustPhoneField;
    @FXML
    public TextField currentPersonCustomerUpdatedByField;
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
    private ObservableList<Country> countries = FXCollections.observableArrayList();
    private ObservableList<String> countryNames = FXCollections.observableArrayList();
    private HashMap<String, Integer> countryIdAndName = new HashMap<>();
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
        String updatedBy = currentPersonCustomerUpdatedByField.getText();
        String divID = DBFirstLevDiv.getDivID(updateCustomerComboDivId.getValue());
        if(!divID.equals("0") && !updatedBy.isEmpty() && !name.isEmpty() && !address.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty() && !updatedBy.isEmpty()) {
            try {
                //call DBCustomer update method
                DBCustomer.updateCustomer(customerID, name, address, postalCode, phone, updatedBy, divID);
                updateCustErrorField.setTextFill(Color.BLACK);
                updateCustErrorField.setText("Customer Record Updated");
                updateCustomerBtn.setDisable(true);
            } catch (Exception exception) {
                updateCustErrorField.setTextFill(Color.RED);
                updateCustErrorField.setText("Please complete all fields");
                exception.printStackTrace();
            }
        }else{
            updateCustErrorField.setTextFill(Color.RED);
            updateCustErrorField.setText("Please complete all fields");
        }
    }

    /**
     * Sets all of the text fields with the data of the selected customer.
     * @param customerID
     */
    public void populateCustomerData(String customerID) throws SQLException {
        int cid = Integer.parseInt(customerID);
        Customer customer = DBCustomer.getACustomerByID(cid);
        String divID = String.valueOf(customer.getDivisionID());
        String countryID = DBFirstLevDiv.getCountryID(divID);
        String countryName = DBCountries.getCountryName(countryID);
        String divName = DBFirstLevDiv.getDivName(divID);
        updateCustIDLabel.setText(String.valueOf(customer.getCustomer_ID()));
        updateCustNameField.setText(customer.getCustomer_Name());
        updateCustAddressField.setText(customer.getAddress());
        updateCustPostalCodeField.setText(customer.getPostalCode());
        updateCustPhoneField.setText(customer.getPhone());
        updateCustCreatedBy.setText(customer.getCreatedBy());
        updateCustCreatedOn.setText(customer.getCreatedDate());
        updateCustLastUpdatedBy.setText(customer.getLastUpdatedBy());
        updateCustLastUpdateDate.setText(customer.getLastUpdate());
        updateCustomerComboCountry.setValue(countryName);
        updateCustomerComboDivId.setValue(divName);

    }

    /**
     * Populates the combo box with countries from the database.
     */
    public void populateComboBoxCountry(){
        countryNames = FXCollections.observableArrayList();
        for(Country c : countries){
            countryNames.add(c.getCountryName());
            countryIdAndName.put(c.getCountryName(), c.getCountryID());
        }
        updateCustomerComboCountry.setItems(countryNames);
    }
    /**
     * Populates the combo box with the first level divisions that are a part of the country chosen
     * in the country combo box.
     * @param event
     */
    public void populateComboBoxDivId(ActionEvent event){
        if(countryNames != null){
            String country = updateCustomerComboCountry.getValue().toString();
            Integer countryId = countryIdAndName.get(country);
            ObservableList<String> divNames = DBFirstLevDiv.getDivNames(countryId);
            updateCustomerComboDivId.setItems(divNames);
        }else{
            updateCustErrorField.setTextFill(Color.RED);
            updateCustErrorField.setText("Please choose a Country First");
        }
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() throws SQLException {
        //JDBC.openConnection();
        countries = DBCountries.getAllCountries();
        populateComboBoxCountry();
        customerID = ChooseCustomerToUpdateController.customerID;
        populateCustomerData(customerID);

        System.out.println(customerID);
    }


}
