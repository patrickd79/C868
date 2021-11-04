package C868;

import C868.Entities.Appointment;
import C868.Helper.DBAppointment;
import C868.Helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class ViewAppointmentsController {
    @FXML
    public TableView<Appointment> tableView;
    @FXML
    public TableColumn<Appointment, Integer> IDCol;
    @FXML
    public TableColumn<Appointment, String> titleCol;
    @FXML
    public TableColumn<Appointment, String> descriptionCol;
    @FXML
    public TableColumn<Appointment, String> locationCol;
    @FXML
    public TableColumn<Appointment, String> typeCol;
    @FXML
    public TableColumn<Appointment, String> startCol;
    @FXML
    public TableColumn<Appointment, String> endCol;
    @FXML
    public TableColumn<Appointment, String> createDateCol;
    @FXML
    public TableColumn<Appointment, String> createdByCol;
    @FXML
    public TableColumn<Appointment, String> lastUpdateDateCol;
    @FXML
    public TableColumn<Appointment, String> lastUpdatedByCol;
    @FXML
    public TableColumn<Appointment, String> customerIDCol;
    @FXML
    public TableColumn<Appointment, String> userIDCol;
    @FXML
    public TableColumn<Appointment, String> contactIDCol;
    public static String apptID;
    public static String title;
    public static Appointment thisAppt;
    @FXML
    public Label viewApptErrorField;

    public void initialize() {
        JDBC.openConnection();
        setAllAppointmentsTableView();
    }

    /**
     * Retrieves data for all of the appointments and sets the table view with the data.
     */
    public void setAllAppointmentsTableView(){
        tableView.setItems(DBAppointment.getAllAppointments());
        IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    /**
     * Retrieves data for all of the appointments that are scheduled for the current month
     * and sets the table view with the data.
     */
    public void setCurrentMonthTableView(ActionEvent event) throws IOException{
        try {
            tableView.setItems(DBAppointment.getAppointmentsByMonth());
            IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
            createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            viewApptErrorField.setTextFill(Color.BLACK);
            viewApptErrorField.setText("Showing appointments for the current month");

        }catch(Exception ex){
            viewApptErrorField.setTextFill(Color.RED);
            viewApptErrorField.setText("There are no appointments for the current month.");
        }
    }
    /**
     * Retrieves data for all of the appointments that are scheduled for the current week
     * and sets the table view with the data.
     */
    public void setCurrentWeekTableView(ActionEvent event){
        try {
            tableView.setItems(DBAppointment.getAppointmentsByWeek());
            IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
            createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            viewApptErrorField.setTextFill(Color.BLACK);
            viewApptErrorField.setText("Showing appointments for the current week");

        }catch (Exception ex){
            viewApptErrorField.setTextFill(Color.RED);
            viewApptErrorField.setText("There are no appointments for the current week.");
        }
    }

    /**
     * Adds the selected appointment to ObservableList<Appointment> selectedAppt.
     */
    public void apptToUpdate(){
        ObservableList<Appointment> selectedAppt;
        selectedAppt = tableView.getSelectionModel().getSelectedItems();
        for(Appointment appt: selectedAppt){
            ChooseAppointmentToUpdateController.apptID = String.valueOf(appt.getAppointmentID());
            ChooseAppointmentToUpdateController.title = appt.getTitle();
            ChooseAppointmentToUpdateController.thisAppt = appt;
        }
    }

    /**
     * Changes to the Update Appointment window to edit the appointment selected.
     * @param event
     * @throws IOException
     */
    public void goToUpdateAppointmentWindow(ActionEvent event) throws IOException {
        apptToUpdate();
        Parent updateAppointmentWindow = FXMLLoader.load(getClass().getResource("updateAppointment.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentWindow);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(updateAppointmentScene);
        window.show();
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

}
