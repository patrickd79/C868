package C868;

import C868.Entities.Appointment;
import C868.Entities.User;
import C868.Helper.DBAppointment;
import C868.Helper.DBUser;
import C868.Helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MainMenuController {

    @FXML
    public Button addAppointmentBtn;
    public Connection connection;
    public static User user;
    @FXML
    public Label mainMenuMessages;
    public String userID;
    public static Paint color;


    public void initialize() {
        JDBC.openConnection();
        //System.out.println("UserName:  "+ LoginController.userNameField.getText().trim());
        user = DBUser.getAUserByName(LoginController.thisUser);
        //user = DBUser.getAUserByName("User One");
        userID = String.valueOf(user.getUserID());
        populateMainMenuLabel();


    }

    /**
     * Populates the message text for the warning if the user has an appointment within 15 minutes of logging in.
     */
    public void populateMainMenuLabel(){
        mainMenuMessages.setText(checkUserAppts(user));
        mainMenuMessages.setTextFill(color);
    }

    /**
     * Checks all of the appointments the user has in the database, and if it finds one that starts
     * within 15 minutes of the log in, returns that as the message string.
     * @param user
     * @return Message string
     */

    public static String checkUserAppts(User user) {
        String msg = null;
        ObservableList<Appointment> appts;
        appts = DBAppointment.getAppointmentsForASingleUserByID(String.valueOf(user.getUserID()));
        for (Appointment a : appts) {
                if(differenceInTime(a) >= 0 && differenceInTime(a) < 15){

                        msg = "You have an upcoming appointment, Title: " + a.getTitle() + " at " + a.getStart();
                        color = Color.RED;
                        break;
                    } else {
                        msg = "You have no upcoming appointments within the next 15 minutes.";
                        color = Color.BLACK;
                    }

        }
        return msg;
    }

    /**
     *
     * @param appt appt to check for time difference from current time
     * @return Returns the difference in minutes from the current tome to the appointment time to
     * determine if it is within 15 minutes of the current time.
     */
    public static int differenceInTime(Appointment appt){
        //get current time locally
        //get appointment time in local time
        //get difference
        int difference = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        int year = 0;
        int month = 0;
        int day = 0;
        String time = null;
        int hour = 0;
        int minutes = 0;
        String localDateTimeOFAppt;
        LocalDateTime apptTime;
            localDateTimeOFAppt = appt.getStart();
            year = Integer.parseInt(DBAppointment.extractYear(localDateTimeOFAppt));
            month = Integer.parseInt(DBAppointment.extractMonth(localDateTimeOFAppt));
            day = Integer.parseInt(DBAppointment.extractDay(localDateTimeOFAppt));
            time = UpdateAppointmentController.getTime(localDateTimeOFAppt);
            hour = AddAppointmentController.getHour(time);
            minutes = Integer.parseInt(AddAppointmentController.getMinutes(time));
            apptTime = LocalDateTime.of(year, month, day, hour, minutes);
            //System.out.println("Appt Time = " + apptTime);
            //System.out.println("Current Time = " + currentDateTime);
            difference = (int) ChronoUnit.MINUTES.between(currentDateTime, apptTime);
            //System.out.println("DIFFERENCE = " + difference);

            return difference;
    }

    /**
     *
     * @return Returns the current system time of the user's system.
     */
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    /**
     * Changes window the Add Customer window.
     * @param event
     * @throws IOException
     */
    public void goToAddCustomerWindow(ActionEvent event) throws IOException {
        Parent addCustomerWindow = FXMLLoader.load(getClass().getResource("addCustomer.fxml"));
        Scene addCustomerScene = new Scene(addCustomerWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }
    /**
     * Changes window the Update Customer window.
     * @param event
     * @throws IOException
     */
    public void goToUpdateCustomerWindow(ActionEvent event) throws IOException {
        Parent updateCustomerWindow = FXMLLoader.load(getClass().getResource("chooseCustomerToUpdate.fxml"));
        Scene updateCustomerScene = new Scene(updateCustomerWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateCustomerScene);
        window.show();
    }
    /**
     * Changes window the Add Appointment window.
     * @param event
     * @throws IOException
     */
    public void goToAddAppointmentWindow(ActionEvent event) throws IOException {
        Parent addAppointmentWindow = FXMLLoader.load(getClass().getResource("addAppointment.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppointmentScene);
        window.show();
    }
    /**
     * Changes window the View Appointment window.
     * @param event
     * @throws IOException
     */
    public void goToViewAppointmentWindow(ActionEvent event) throws IOException {
        Parent viewAppointmentWindow = FXMLLoader.load(getClass().getResource("viewAppointments.fxml"));
        Scene viewAppointmentScene = new Scene(viewAppointmentWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewAppointmentScene);
        window.show();
    }
    /**
     * Changes window the Update Appointment window.
     * @param event
     * @throws IOException
     */
    public void goToUpdateAppointmentWindow(ActionEvent event) throws IOException {
        Parent updateAppointmentWindow = FXMLLoader.load(getClass().getResource("chooseAppointmentToUpdate.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateAppointmentScene);
        window.show();
    }
    /**
     * Changes window the Reports window.
     * @param event
     * @throws IOException
     */
    public void goToUpdateReportsWindow(ActionEvent event) throws IOException {
        Parent updateAppointmentWindow = FXMLLoader.load(getClass().getResource("reports.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateAppointmentScene);
        window.show();
    }

}
