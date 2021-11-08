package C868;

import C868.Entities.Appointment;
import C868.Entities.Customer;
import C868.Entities.Type;
import C868.Helper.DBCustomer;
import C868.Helper.DBType;
import C868.Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;

public class ReportsController {

    @FXML
    public ComboBox<String> typesCombo;
    @FXML
    public Label reportErrorMsgField;
    @FXML
    public ComboBox<String> customersCombo;
    Customer customer;
    Type type;
    public static String customerID;
    public static String typeID;

    public static ObservableList<Appointment> appts = FXCollections.observableArrayList();
    public static ObservableList<Type> allTypes = FXCollections.observableArrayList();
    public static ObservableList<String> typeNames = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<String> customerNames = FXCollections.observableArrayList();

    public void initialize() {
        JDBC.openConnection();
        customers = DBCustomer.getAllCustomers();
        allTypes = DBType.getAllTypes();
        populateCustomerCombo();
        populateComboBoxTypes();
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void goToCustomerReportWindow(ActionEvent event) throws IOException {
        Parent updateCustomerWindow = FXMLLoader.load(getClass().getResource("customerApptReport.fxml"));
        Scene updateCustomerScene = new Scene(updateCustomerWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateCustomerScene);
        window.show();
    }

    public void goToTypeReportWindow(ActionEvent event) throws IOException {
        Parent updateCustomerWindow = FXMLLoader.load(getClass().getResource("typeReport.fxml"));
        Scene updateCustomerScene = new Scene(updateCustomerWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateCustomerScene);
        window.show();
    }



    public void getCustomerAppts(ActionEvent event) {
        try {
            String name = customersCombo.getValue();
            customer = DBCustomer.getACustomerByName(name);
            customerID = String.valueOf(customer.getCustomer_ID());
            customersCombo.getItems().clear();
            goToCustomerReportWindow(event);
        }
        catch (Exception ex){
            ex.printStackTrace();
            reportErrorMsgField.setTextFill(Color.RED);
            reportErrorMsgField.setText("Please choose a customer to query.");
        }
    }

    public void getApptByTypes(ActionEvent event){
        try{
            String name = typesCombo.getValue();
            type = DBType.getATypeByName(name);
            typeID = type.getTypeID();
            goToTypeReportWindow(event);
        }catch(Exception ex){
            ex.printStackTrace();
            reportErrorMsgField.setTextFill(Color.RED);
            reportErrorMsgField.setText("Please choose an appointment type to query.");
        }

    }

    public void populateCustomerCombo(){
        String name;
        for(Customer c : customers){
            name = String.valueOf(c.getCustomer_Name());
            customerNames.add(name);
        }
        customersCombo.setItems(customerNames);
    }

    public void populateComboBoxTypes(){
        String typeName;
        HashSet<String> types = new HashSet<>();
        for(Type t : allTypes){
            typeName = t.getTypeName();
            if(!types.contains(typeName)){
                types.add(typeName);
            }
        }
        typeNames.addAll(types);
        typesCombo.setItems(typeNames);
    }



}
