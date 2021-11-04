package C868;

import C868.Entities.Appointment;
import C868.Entities.Customer;
import C868.Entities.User;
import C868.Helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashSet;

public class ReportsController {
    @FXML
    public ComboBox<String> monthsCustomerHasApptsCombo;
    @FXML
    public TextField totalApptsforCustomerMonthField;
    @FXML
    public ComboBox<String> typesCombo;
    @FXML
    public TextField customerToQueryField;
    @FXML
    public TextField totalApptByType;
    @FXML
    public Button userApptReportBtn;
    @FXML
    public Button customerMonthReportBtn;
    @FXML
    public Button customerTypeReportBtn;
    @FXML
    public Label reportErrorMsgField;
    @FXML
    public Button getCustomerBtn;
    @FXML
    public ComboBox<String> customersCombo;
    @FXML
    public ComboBox<String> userCombo;
    @FXML
    public TextField userTotalApptField;
    public String customerID;
    public String userID;
    User user;
    String userApptCountResult;
    Customer customer;

    public static ObservableList<Appointment> appts = FXCollections.observableArrayList();
    ObservableList<String> apptMonths = FXCollections.observableArrayList();
    public static ObservableList<String> apptTypes = FXCollections.observableArrayList();
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    public static ObservableList<String> customerIDs = FXCollections.observableArrayList();
    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<String> userIDs = FXCollections.observableArrayList();

    public void initialize() {
        JDBC.openConnection();
        customers = DBCustomer.getAllCustomers();
        users = DBUser.getAllUsers();
        populateCustomerCombo();
        populateUserCombo();
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    /**
     * Retrieves the count of appointments for the specified user and sets the text field with the value.
     * @param event
     */
    public void getUserApptCount(ActionEvent event){
        userID = userCombo.getValue();
        userApptCountResult = String.valueOf(DBAppointment.getApptCountForAUser(userID));
        userTotalApptField.setText(userApptCountResult);

    }

    /**
     * Retrieves all the appointments for the specified customer, and populates the combo boxes
     * with the months of the appointments and the types.
     * @param event
     */
    public void getCustomerAppts(ActionEvent event) {
        try {
            customerID = customersCombo.getValue();
            customer = DBCustomer.getACustomerByID(Integer.parseInt(customerID));
            String name = customer.getCustomer_Name();
            appts = DBAppointment.getAppointmentsForASingleCustomerByID(customerID);
            //System.out.println("Customer Name : "+name);
            populateComboBoxCustomerApptMonths();
            populateComboBoxCustomerApptTypes();
            //System.out.println("Methods ran");
            reportErrorMsgField.setTextFill(Color.BLACK);
            reportErrorMsgField.setText("Retrieving appointments for Customer ID: " + customerID + ", Name: " + name);
        }
        catch (Exception ex){
            ex.printStackTrace();
            reportErrorMsgField.setTextFill(Color.RED);
            reportErrorMsgField.setText("Please choose a customer to query.");
        }
    }

    /**
     * Counts the number of appointments the selected customer has in the selected month.
     * @param event
     */
    public void getCustomerReportsByMonth(ActionEvent event){
        try {
            int count = 0;
            for (Appointment a : appts) {
                String month = DBAppointment.extractMonth(a.getStart());
                String requestedMonth = monthsCustomerHasApptsCombo.getValue();
                if(month.equals(requestedMonth)){
                    count++;
                }
            }
            totalApptsforCustomerMonthField.setText(String.valueOf(count));
        }
        catch (Exception ex){
            ex.printStackTrace();
            reportErrorMsgField.setText("Please choose a month.");
        }
    }
    /**
     * Counts the number of appointments the selected customer has of the selected type.
     * @param event
     */
    public void getCustomerReportsByType(ActionEvent event){
        try {
            int count = 0;
            for (Appointment a : appts) {
                String type = a.getType();
                String requestedType = typesCombo.getValue();
                if(type.equals(requestedType)){
                    count++;
                }
            }
            totalApptByType.setText(String.valueOf(count));
        }
        catch (Exception ex){
            ex.printStackTrace();
            reportErrorMsgField.setText("Please choose a type.");
        }
    }

    /**
     * Populates the customer combo box with all the customers in the database, for selection.
     */
    public void populateCustomerCombo(){
        String id;
        for(Customer c : customers){
            id = String.valueOf(c.getCustomer_ID());
            customerIDs.add(id);
        }
        customersCombo.setItems(customerIDs);
    }
    /**
     * Populates the user combo box with all the users in the database, for selection.
     */
    public void populateUserCombo(){
        String id;
        for(User u : users){
            id = String.valueOf(u.getUserID());
            userIDs.add(id);
        }
        userCombo.setItems(userIDs);
    }

    /**
     * Checks all of the customer's appointments for a start date month and adds it to a hash set if it is not
     * already contained there. Then adds all of the discreet month numbers to the observable list,
     * which then is used to set the combo box items.
     *
     */
    public void populateComboBoxCustomerApptMonths(){
        //System.out.println("populateComboBoxCustomerApptMonths Method Start");
        String month;
        HashSet<String> months = new HashSet<>();
        //System.out.println("Start for each");
        //System.out.println("APPTS SIZE: "+ appts.size());
        for(Appointment a : appts){
            //System.out.println("Appointment ID: "+a.getAppointmentID());
            month = DBAppointment.extractMonth(a.getStart());
            if(!months.contains(month)){
                months.add(month);
                //System.out.println("MONTH: "+month);
            }
        }
        apptMonths.addAll(months);
        monthsCustomerHasApptsCombo.setItems(apptMonths);
        //System.out.println("MONTH SET ITEMS WORKS");
    }

    /**
     * Checks all of the customer's appointments for the type and adds it to a hash set if it is not
     * already contained there. Then adds all of the discreet types to the observable list,
     * which then is used to set the combo box items.
     *
     */
    public void populateComboBoxCustomerApptTypes(){
        String type;
        HashSet<String> types = new HashSet<>();
        for(Appointment a : appts){
            type = a.getType();
            if(!types.contains(type)){
                types.add(type);
            }
        }
        apptTypes.addAll(types);
        typesCombo.setItems(apptTypes);
    }



}
