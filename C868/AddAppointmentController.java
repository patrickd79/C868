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
import java.util.Date;

public class AddAppointmentController {
    @FXML
    public TextField addAppointmentTitleField;
    @FXML
    public TextField addAppointmentDescField;
    @FXML
    public TextField addAppointmentLocationField;
    @FXML
    public TextField addAppointmentTypeField;
    @FXML
    public DatePicker addAppointmentStartDate;
    @FXML
    public ComboBox<String> addAppointmentCustIDField;
    @FXML
    public ComboBox<String> addAppointmentUserIDField;
    @FXML
    public ComboBox<String> addAppointmentContactNameField;
    @FXML
    public Label addApptErrorField;
    @FXML
    public TextField addApptStartTimeField;
    @FXML
    public TextField addApptEndTimeField;
    @FXML
    public RadioButton startTimeAMToggle;
    @FXML
    public ToggleGroup startTime;
    @FXML
    public RadioButton startTimePMToggle;
    @FXML
    public RadioButton endTimeAMToggle;
    @FXML
    public ToggleGroup endTime;
    @FXML
    public RadioButton endTimePMToggle;
    @FXML
    public Button addApptBtn;
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<String> userNames = FXCollections.observableArrayList();
    /**
     * Lambda to convert an integer to a String. It is useful for
     * reducing clutter in some of the methods where String.valueOf() is used frequently
     */
    StringValue sv = i -> String.valueOf(i);

    /**
     * Adds an appointment record to the database.
     * @param event
     */
    public void addAppointment(ActionEvent event){

        try {
                User user = DBUser.getAUserByName(addAppointmentUserIDField.getValue());
                Contact contact = DBContacts.getAContactByName(addAppointmentContactNameField.getValue());
                Customer customer = DBCustomer.getACustomerByName(addAppointmentCustIDField.getValue());
                String start = addApptStartTimeField.getText();
                String end = addApptEndTimeField.getText();
                String adjustedStartTime;
                String adjustedEndTime;

                //adjust time to a 24 hour clock for the start time
                /*if(startTimePMToggle.isSelected() && getHour(start) < 12){
                    adjustedStartTime = (getHour(start) + 12) +":"+ getMinutes(start);
                }else{
                    adjustedStartTime = start;
                }
                //adjust time to a 24 hour clock for the end time
                if(endTimePMToggle.isSelected() && getHour(end) < 12){
                    adjustedEndTime = (getHour(end) + 12) +":"+ getMinutes(end);
                }else{
                    adjustedEndTime = end;
                }*/
                //System.out.println("Start: "+adjustedStartTime+" End: "+adjustedEndTime);
                String startTime = addAppointmentStartDate.getValue().toString() + " "+ start;
                String endTime = addAppointmentStartDate.getValue().toString() + " " +end;
                if(isDuringOfficeHours(startTime, endTime) &&
                        !customerHasOverlappingAppointments(sv.str(customer.getCustomer_ID()),startTime, endTime,"0")){
                    DBAppointment.addAppointment(addAppointmentTitleField.getText(), addAppointmentDescField.getText(), addAppointmentLocationField.getText(),
                            addAppointmentTypeField.getText(), startTime, endTime,
                            user.getUserName(),
                            sv.str(customer.getCustomer_ID()), sv.str(user.getUserID()),
                            sv.str(contact.getContactID()));
                    addApptErrorField.setTextFill(Color.BLACK);
                    addApptErrorField.setText("Appointment Created");
                    addApptBtn.setDisable(true);

                }else if(!isDuringOfficeHours(startTime, endTime)){
                    addApptErrorField.setTextFill(Color.RED);
                    addApptErrorField.setText("Please make sure that appointment time is between 0800 EST and 2200 EST.");
                }else if(customerHasOverlappingAppointments(sv.str(customer.getCustomer_ID()),startTime, endTime,"0")){
                    addApptErrorField.setTextFill(Color.RED);
                    addApptErrorField.setText("Please change the date or time of this appointment. This appointment overlaps another one of the customer's appointments.");
                }


        } catch (Exception e) {
                addApptErrorField.setTextFill(Color.RED);
                addApptErrorField.setText("Please complete all fields");
                e.printStackTrace();
            }
    }

    /**
     *
     * @param start start date and time of the appointment
     * @param end end date and time of the appointment
     * @return Returns true is the appointment's start and end time fall within the office hours.
     */
    public static boolean isDuringOfficeHours(String start, String end){
        //System.out.println("Start: "+start+"   End: "+end);
        //convert the passed date and times to EST
        String startEST = TimeZones.convertToESTTimeZone(start);
        String endEST = TimeZones.convertToESTTimeZone(end);
        //String startDayOfWeek
        //System.out.println("Start EST Converted: "+startEST+"    End EST Converted: "+endEST);
        // extract the time portion of the string from the dates
        String startTimeEST = UpdateAppointmentController.getTime(startEST);
        String endTimeEST = UpdateAppointmentController.getTime(endEST);
        //remove the : from the time and convert to an int
        int startTimeInt = removeColonFromTime(startTimeEST);
        int endTimeInt = removeColonFromTime(endTimeEST);
        //System.out.println("Start Int: "+startTimeInt+ "    End Time Int:"+endTimeInt);

        return startTimeInt > 759 && startTimeInt < endTimeInt && endTimeInt < 2201;
    }

    /**
     *
     * @param customerID
     * @param startDate
     * @param endDate
     * @return Returns true if the customer has another appointment that has overlapping time with the one being passed in.
     */
    public static boolean customerHasOverlappingAppointments(String customerID, String startDate, String endDate, String apptID){
        ObservableList<Appointment> appts = DBAppointment.getAppointmentsForASingleCustomerByID(customerID);
        ObservableList<Date> dates = FXCollections.observableArrayList();
        Date newApptStart = TimeZones.convertStringToDate(startDate);
        //System.out.println("customerHasOverlappingAppointments newApptStart == "+ newApptStart);
        Date newApptEnd = TimeZones.convertStringToDate(endDate);
        //System.out.println("customerHasOverlappingAppointments newApptEnd == "+ newApptEnd);
            for(Appointment a : appts){
                //check if it is the appointment being updated or modified
                if(!String.valueOf(a.getAppointmentID()).equals(apptID)){
                    Date oldApptStart = TimeZones.convertStringToDate(a.getStart());
                    //System.out.println("customerHasOverlappingAppointments oldApptStart == "+ oldApptStart);
                    Date oldApptEnd = TimeZones.convertStringToDate(a.getEnd());
                    //System.out.println("customerHasOverlappingAppointments oldApptEnd == "+ oldApptEnd);
                    if((newApptStart.before(oldApptEnd) &&
                            newApptEnd.after(oldApptStart)) || newApptEnd.after(oldApptStart) && newApptStart.before(oldApptStart)){
                        //System.out.println("customerHasOverlappingAppointments a.start == "+ a.getStart());
                        //System.out.println("customerHasOverlappingAppointments a.end == "+ a.getEnd());
                        System.out.println("customerHasOverlappingAppointments == true");
                        return true;
                }

                }
            }
        System.out.println("customerHasOverlappingAppointments == false");
            return false;
    }

    /**
     *
     * @param time string representing the time portion of the date time.
     * @return Returns an int representing the time portion of the date string with the colon removed,
     * so that mathematical operations and be performed upon it.
     */
    public static int removeColonFromTime(String time){
        char[] ca = time.toCharArray();
        StringBuilder sb = new StringBuilder();
        String number;
        for(int i = 0; i < 2; i++){
            sb.append(ca[i]);
        }
        for(int i = 3; i < 5; i++){
            sb.append(ca[i]);
        }
        number = sb.toString();
        //returns the time with the ':' removed
        return Integer.parseInt(number);
    }

    /**
     *
     * @param time
     * @return Returns the minutes portion of the time string passed in.
     */
    public static String getMinutes(String time){
        String[] strings = time.split(":",2);
         return strings[1];
    }

    /**
     *
     * @param time
     * @return Returns the hours portion of the time string passed in.
     */
    public static int getHour(String time){
        String[] strings = time.split(":",2);
        int result = Integer.parseInt(strings[0]);
        //System.out.println(result);
        return result;
    }


    /**
     * Adds all the contact names to an Observable List of Strings and then sets the combo box with the values.
     */
    public void populateComboBoxContactName(){
        for(Contact c : contacts){
            contactNames.add(c.getContactName());
        }
        addAppointmentContactNameField.setItems(contactNames);
    }

    /**
     *  Adds all the customer names to an Observable List of Strings and then sets the combo box with the values.
     */
    public void populateComboBoxCustomerNames(){
        for(Customer c : customers){
            customerNames.add(c.getCustomer_Name());
        }
        addAppointmentCustIDField.setItems(customerNames);
    }

    /**
     * Adds all the user names to an Observable List of Strings and then sets the combo box with the values.
     */
    public void populateComboBoxUserNames(){
        for(User u : users){
            userNames.add(u.getUserName());
        }
        addAppointmentUserIDField.setItems(userNames);
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }


    /**
     * Prepares the screen and organizes data upon opening
     */
    public void initialize() {
        JDBC.openConnection();
        contacts = DBContacts.getAllContacts();
        customers = DBCustomer.getAllCustomers();
        users = DBUser.getAllUsers();
        populateComboBoxContactName();
        populateComboBoxCustomerNames();
        populateComboBoxUserNames();
    }
}
