package C868;

import C868.Entities.Appointment;
import C868.Entities.Customer;
import C868.Entities.Type;
import C868.Entities.User;
import C868.Helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateAppointmentController {
    @FXML
    public Label apptIDLabel;
    @FXML
    public DatePicker updateAppointmentStartDate;
    @FXML
    public ComboBox<String> customerCombo;
    @FXML
    public ComboBox<String> userCombo;
    @FXML
    public TextField startTimeField;
    @FXML
    public TextField endTimeField;

    @FXML
    public TextField titleField;
    @FXML
    public TextField locationField;
    @FXML
    public TextField createDateField;
    @FXML
    public TextField createdByField;
    @FXML
    public TextField lastUpdateDateField;
    @FXML
    public TextField lastUpdatedByField;
    @FXML
    public Label updateApptErrorField;
    @FXML
    public TextField updatingNowField;
    private static String apptID;
    @FXML
    public Button updateBtn;
    public ComboBox<String> typeCombo;
    Appointment appt;
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    ObservableList<String> customerIDs = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<String> userNames = FXCollections.observableArrayList();
    ObservableList<String> userIDs = FXCollections.observableArrayList();
    ObservableList<Type> types = FXCollections.observableArrayList();
    ObservableList<String> typeNames = FXCollections.observableArrayList();

    /**
     * Updates the selected appointment record with the data provided.
     * @param event
     * @throws IOException
     */
    public void updateAppointment(ActionEvent event) throws IOException {

        String id = apptID;
        String title = titleField.getText();
        String location = locationField.getText();
        String typeName = typeCombo.getValue();
        String typeID = DBType.getATypeByName(typeName).getTypeID();
        String startDate = String.valueOf(updateAppointmentStartDate.getValue());
        String startTime = startTimeField.getText();
        String endDate = String.valueOf(updateAppointmentStartDate.getValue());
        String endTime = endTimeField.getText();
        String updatedBy = LoginController.thisUser;
        Customer customer = DBCustomer.getACustomerByName(customerCombo.getValue());
        String customerID = String.valueOf(customer.getCustomer_ID());
        //User user = DBUser.getAUserByName(userCombo.getValue());
        //String customerID = String.valueOf(customer.getCustomer_ID());
        User user = DBUser.getAUserByName("owner");
        String userID = String.valueOf(user.getUserID());
        //String userID = String.valueOf(user.getUserID());
        {
            try {
                String adjustedStartTime;
                String adjustedEndTime;

                //adjust time to a 24 hour clock for the start time
                /*if (startPMToggle.isSelected() && AddAppointmentController.getHour(startTime) < 12) {
                    adjustedStartTime = (AddAppointmentController.getHour(startTime) + 12) + ":" + AddAppointmentController.getMinutes(startTime);
                } else {
                    adjustedStartTime = startTime;
                }
                //adjust time to a 24 hour clock for the end time
                if (endPMToggle.isSelected() && AddAppointmentController.getHour(endTime) < 12) {
                    adjustedEndTime = (AddAppointmentController.getHour(endTime) + 12) + ":" + AddAppointmentController.getMinutes(endTime);
                } else {
                    adjustedEndTime = endTime;
                }*/
                String startDateAndTime = startDate +" "+startTime;
                String endDateAndTime = startDate +" "+endTime;

                if (AddAppointmentController.isDuringOfficeHours(startDateAndTime, endDateAndTime) &&
                        !AddAppointmentController.customerHasOverlappingAppointments(String.valueOf(customer.getCustomer_ID()),startDateAndTime, endDateAndTime, apptID)) {
                    System.out.println("update appt line 120");
                    //call DBCustomer update method
                    DBAppointment.updateAppointment(id, title, location, typeID, startDate,
                            startTime, endDate, endTime, updatedBy, customerID, userID);
                    updateApptErrorField.setTextFill(Color.BLACK);
                    updateApptErrorField.setText("Appointment Record Updated");
                    updateBtn.setDisable(true);

                }else if(!AddAppointmentController.isDuringOfficeHours(startDateAndTime, endDateAndTime)){
                    updateApptErrorField.setTextFill(Color.RED);
                    updateApptErrorField.setText("Please make sure that appointment time is between 0800 EST and 2200 EST.");
                }else if(AddAppointmentController.customerHasOverlappingAppointments(String.valueOf(customer.getCustomer_ID()),startDateAndTime, endDateAndTime,apptID)){
                    System.out.println("update appt line 131");
                    updateApptErrorField.setTextFill(Color.RED);
                    updateApptErrorField.setText("Please change the date or time of this appointment. This appointment overlaps another one of the customer's appointments.");
                }

            } catch (Exception exception) {
                updateApptErrorField.setTextFill(Color.RED);
                updateApptErrorField.setText("Please complete all fields");
                exception.printStackTrace();
            }
        }

    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    /**
     *
     * @param dateString
     * @return Returns a string with the date and time without the seconds.
     */
    public static String getDateAndTimeNoSeconds(String dateString){
        String date;
        StringBuilder sb = new StringBuilder();
        char[] ca = dateString.toCharArray();
        if(dateString.length() >= 11) {
            for (int i = 0; i < 16; i++) {
                sb.append(ca[i]);
            }
            date = sb.toString();
        }else{
            for (int i = 0; i < 10; i++) {
                sb.append(ca[i]);
            }
            date = sb.toString();
        }
        return date;
    }

    /**
     *
     * @param dateString
     * @return Returns a string representing the date without the time.
     */
    public static String getDateNoTime(String dateString){
        char[] ca = dateString.toCharArray();
        StringBuilder sb = new StringBuilder();
        String date;
        for(int i = 0; i < 10; i++){
            sb.append(ca[i]);
        }
        date = sb.toString();
        return date;
    }
    /**
     *
     * @param dateString
     * @return Returns a string representing the time without the date.
     */
    public static String getTime(String dateString){
        char[] ca = dateString.toCharArray();
        StringBuilder sb = new StringBuilder();
        String time;
        for(int i = 11; i < 16; i++){
            sb.append(ca[i]);
        }
        time = sb.toString();
        return time;
    }

    /**
     * Populates the text fields with the data for the selected appointment.
     */
    public void populateAppointmentData(){

        int customerID = appt.getCustomerID();
        Customer customer = DBCustomer.getACustomerByID(customerID);
        apptIDLabel.setText(String.valueOf(appt.getAppointmentID()));
        titleField.setText(appt.getTitle());
        locationField.setText(appt.getLocation());
        updateAppointmentStartDate.setValue(LocalDate.parse(getDateNoTime(appt.getStart())));
        startTimeField.setText(getTime(appt.getStart()));
        endTimeField.setText(getTime(appt.getEnd()));
        createDateField.setText(getDateAndTimeNoSeconds(appt.getCreatedDate()));
        createdByField.setText(appt.getCreatedBy());
        lastUpdateDateField.setText(getDateAndTimeNoSeconds(appt.getLastUpdate()));
        lastUpdatedByField.setText(appt.getLastUpdatedBy());
        populateComboBoxCustomerNames();
        //populateComboBoxUserNames();
        populateComboBoxTypeNames();
        typeCombo.setValue(DBType.getATypeByID(appt.getType()).getTypeName());
        customerCombo.setValue(customer.getCustomer_Name());

    }
    /**
     *  Adds all the customer names to an Observable List of Strings and then sets the combo box with the values.
     */
    public void populateComboBoxCustomerNames(){
        for(Customer c : customers){
            customerNames.add(c.getCustomer_Name());
            customerIDs.add(String.valueOf(c.getCustomer_ID()));
        }
        customerCombo.setItems(customerNames);
    }
    /**
     * Adds all the user names to an Observable List of Strings and then sets the combo box with the values.
     */
    /*public void populateComboBoxUserNames(){
        for(User u : users){
            userNames.add(u.getUserName());
            userIDs.add(String.valueOf(u.getUserID()));
        }
        userCombo.setItems(userNames);
    }*/

    public void populateComboBoxTypeNames(){
        for(Type t : types){
            typeNames.add(t.getTypeName());
        }
        typeCombo.setItems(typeNames);
    }

    public void initialize() {
        JDBC.openConnection();
        apptID = ChooseAppointmentToUpdateController.apptID;
        appt = DBAppointment.getAppointmentByID(apptID);
        customers = DBCustomer.getAllCustomers();
        types = DBType.getAllTypes();
        populateAppointmentData();

    }
}
