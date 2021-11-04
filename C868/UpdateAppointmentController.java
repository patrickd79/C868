package C868;

import C868.Entities.Appointment;
import C868.Entities.Contact;
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
    public ComboBox<String> contactCombo;
    @FXML
    public TextField titleField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField locationField;
    @FXML
    public TextField typeField;
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
    @FXML
    public ToggleGroup startToggle;
    @FXML
    public ToggleGroup endToggle;
    @FXML
    public RadioButton startAMToggle;
    @FXML
    public RadioButton startPMToggle;
    @FXML
    public RadioButton endAMToggle;
    @FXML
    public RadioButton endPMToggle;
    private static String apptID;
    @FXML
    public Button updateBtn;
    Appointment appt;
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    ObservableList<String> contactIDs = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    ObservableList<String> customerIDs = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<String> userNames = FXCollections.observableArrayList();
    ObservableList<String> userIDs = FXCollections.observableArrayList();

    /**
     * Updates the selected appointment record with the data provided.
     * @param event
     * @throws IOException
     */
    public void updateAppointment(ActionEvent event) throws IOException {
        String id = apptID;
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String startDate = String.valueOf(updateAppointmentStartDate.getValue());
        String startTime = startTimeField.getText();
        String endDate = String.valueOf(updateAppointmentStartDate.getValue());
        String endTime = endTimeField.getText();
        String updatedBy = updatingNowField.getText();
        Customer customer = DBCustomer.getACustomerByName(customerCombo.getValue());
        String customerID = String.valueOf(customer.getCustomer_ID());
        //User user = DBUser.getAUserByName(userCombo.getValue());
        //String customerID = String.valueOf(customer.getCustomer_ID());
        User user = DBUser.getAUserByName(userCombo.getValue());
        String userID = String.valueOf(user.getUserID());
        //String userID = String.valueOf(user.getUserID());
        String contactName = contactCombo.getValue();
        Contact contact = DBContacts.getAContactByName(contactName);
        String contactID = String.valueOf(contact.getContactID());
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

                if (!updatingNowField.getText().equals("") && AddAppointmentController.isDuringOfficeHours(startDateAndTime, endDateAndTime) &&
                        !AddAppointmentController.customerHasOverlappingAppointments(String.valueOf(customer.getCustomer_ID()),startDateAndTime, endDateAndTime, apptID)) {
                    System.out.println("update appt line 120");
                    //call DBCustomer update method
                    DBAppointment.updateAppointment(id, title, description, location, type, startDate,
                            startTime, endDate, endTime, updatedBy, customerID, userID, contactID);
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

        String contactID = String.valueOf(appt.getContactID());
        Contact contact = DBContacts.getAContactByID(contactID);
        int customerID = appt.getCustomerID();
        Customer customer = DBCustomer.getACustomerByID(customerID);
        int userID = appt.getUserID();
        User user = DBUser.getAUserByID(userID);
        apptIDLabel.setText(String.valueOf(appt.getAppointmentID()));
        titleField.setText(appt.getTitle());
        descriptionField.setText(appt.getDescription());
        locationField.setText(appt.getLocation());
        typeField.setText(appt.getType());
        updateAppointmentStartDate.setValue(LocalDate.parse(getDateNoTime(appt.getStart())));
        startTimeField.setText(getTime(appt.getStart()));
        endTimeField.setText(getTime(appt.getEnd()));
        createDateField.setText(getDateAndTimeNoSeconds(appt.getCreatedDate()));
        createdByField.setText(appt.getCreatedBy());
        lastUpdateDateField.setText(getDateAndTimeNoSeconds(appt.getLastUpdate()));
        lastUpdatedByField.setText(appt.getLastUpdatedBy());
        populateComboBoxContactName();
        populateComboBoxCustomerNames();
        populateComboBoxUserNames();
        contactCombo.setValue(contact.getContactName());
        customerCombo.setValue(customer.getCustomer_Name());
        userCombo.setValue(user.getUserName());

    }
    /**
     * Adds all the contact names to an Observable List of Strings and then sets the combo box with the values.
     */
    public void populateComboBoxContactName(){
        for(Contact c : contacts){
            contactNames.add(c.getContactName());
            contactIDs.add(String.valueOf(c.getContactID()));
        }
        contactCombo.setItems(contactNames);
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
    public void populateComboBoxUserNames(){
        for(User u : users){
            userNames.add(u.getUserName());
            userIDs.add(String.valueOf(u.getUserID()));
        }
        userCombo.setItems(userNames);
    }

    public void initialize() {
        JDBC.openConnection();
        apptID = ChooseAppointmentToUpdateController.apptID;
        appt = DBAppointment.getAppointmentByID(apptID);
        contacts = DBContacts.getAllContacts();
        customers = DBCustomer.getAllCustomers();
        users = DBUser.getAllUsers();
        populateAppointmentData();

    }
}
