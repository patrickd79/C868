package C868;

import C868.Entities.Country;
import C868.Helper.DBCountries;
import C868.Helper.DBCustomer;
import C868.Helper.DBFirstLevDiv;
import C868.Helper.JDBC;
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
import java.util.HashMap;

public class AddCustomerController {
    @FXML
    public ComboBox<String> addCustomerComboDivId;
    @FXML
    public ComboBox<String> addCustomerComboCountry;
    @FXML
    public TextField custNameField;
    @FXML
    public TextField custAddressField;
    @FXML
    public TextField custPostalCodeField;
    @FXML
    public TextField custPhoneField;
    @FXML
    public TextField customerCreatedByField;
    @FXML
    public Button addCustomerBtn;
    @FXML
    public Label addCustErrorField;
    @FXML
    public TextField custCityField;
    private ObservableList<Country> countries = FXCollections.observableArrayList();
    private ObservableList<String> countryNames = FXCollections.observableArrayList();
    private final HashMap<String, Integer> countryIdAndName = new HashMap<>();


    /**
     * Adds a customer record to the database.
     * @param event
     */
    public void addCustomer(ActionEvent event){
        try {
            DBCustomer.addCustomer(custNameField.getText(), custAddressField.getText()+", "+
                    custCityField.getText(),
                    custPostalCodeField.getText(), custPhoneField.getText(),
                    customerCreatedByField.getText(), DBFirstLevDiv.getDivID(addCustomerComboDivId.getValue()));
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

    /**
     * Populates the combo box with countries from the database.
     */
    public void populateComboBoxCountry(){
        countryNames = FXCollections.observableArrayList();
        for(Country c : countries){
            countryNames.add(c.getCountryName());
            countryIdAndName.put(c.getCountryName(), c.getCountryID());
        }
        addCustomerComboCountry.setItems(countryNames);
    }

    /**
     * Populates the combo box with the first level divisions that are a part of the country chosen
     * in the country combo box.
     * @param event
     */
    public void populateComboBoxDivId(ActionEvent event){
        if(countryNames != null){
            String country = addCustomerComboCountry.getValue().toString();
            Integer countryId = countryIdAndName.get(country);
            ObservableList<String> divNames = DBFirstLevDiv.getDivNames(countryId);
            addCustomerComboDivId.setItems(divNames);
        }else{
            addCustErrorField.setTextFill(Color.RED);
            addCustErrorField.setText("Please choose a Country First");
        }
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() {
        JDBC.openConnection();
        countries = DBCountries.getAllCountries();
        populateComboBoxCountry();
    }



}
