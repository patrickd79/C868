package C868;

import C868.Entities.Customer;
import C868.Helper.DBAppointment;
import C868.Helper.DBCustomer;
import C868.Helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class ChooseCustomerToUpdateController {
    @FXML
    public TableView<Customer> tableView;
    @FXML
    public TableColumn<Customer, Integer> customerIDCol;
    @FXML
    public TableColumn<Customer, String> nameCol;
    @FXML
    public TableColumn<Customer, String> addressCol;
    @FXML
    public TableColumn<Customer, String> postalCol;
    @FXML
    public TableColumn<Customer, String> phoneCol;
    @FXML
    public TableColumn<Customer, String> createDateCol;
    @FXML
    public TableColumn<Customer, String> createdByCol;
    @FXML
    public TableColumn<Customer, String> lastUpdateDateCol;
    @FXML
    public TableColumn<Customer, String> lastUpdatedByCol;
    @FXML
    public TableColumn<Customer, Integer> divIDCol;
    public static String customerID;
    public String name;
    @FXML
    public Label deleteCustomerMessage;

    public void initialize() {
        JDBC.openConnection();
        setTableView();

    }

    /**
     * Retrieves the data for all of the customers in the database and
     * displays them in the table view.
     */
    public void setTableView(){
        tableView.setItems(DBCustomer.getAllCustomers());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Retrieves the data for the selected customer so that it can be updated in the next screen.
     */
    public void customerToUpdate() {
        ObservableList<Customer> selectedCustomer;
        selectedCustomer = tableView.getSelectionModel().getSelectedItems();
        for(Customer customer: selectedCustomer){
            customerID = String.valueOf(customer.getCustomer_ID());
            name = customer.getCustomer_Name();
        }
    }

    /**
     * Deletes a customer record from the database.
     * @param event
     * @throws IOException
     */
    public void deleteCustomer(ActionEvent event) throws IOException {
        customerToUpdate();
        DBAppointment.deleteAppointmentsForASingleCustomer(customerID);
        if(customerID != null && !name.equals(null)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm Customer Delete");
            alert.setContentText("Are you sure you want to delete Customer: "+name+"?" );
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                DBCustomer.deleteCustomer(customerID);
                deleteCustomerMessage.setText("Customer "+name+" deleted.");
                setTableView();
            }

        }else{
            deleteCustomerMessage.setText("You must select a Customer to delete first.");
        }
    }

    /**
     * Navigates to the window to update the customer selected.
     * @param event
     * @throws IOException
     */
    public void goToUpdateCustomerWindow(ActionEvent event) throws IOException {
        customerToUpdate();
        Parent updateCustomerWindow = FXMLLoader.load(getClass().getResource("customerUpdate.fxml"));
        Scene updateCustomerScene = new Scene(updateCustomerWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateCustomerScene);
        window.show();
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }



}
