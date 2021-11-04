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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseAppointmentToUpdateController {
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
    @FXML
    public Label deleteAppointmentMessage;
    public static String apptID;
    public static String title;
    public static Appointment thisAppt;

    public void initialize() {
        JDBC.openConnection();
        setTableView();
    }

    /**
     * Retrieves the data for all appointments and displays them in a table view
     * to allow one to be chosen for editing and updating.
     */
    public void setTableView(){
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
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Navigates to the window that allows the appointment to be updated.
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

    /**
     * Retrieves the data for the specific appointment that is selected from the table view.
     */
    public void apptToUpdate(){
            ObservableList<Appointment> selectedAppt;
            selectedAppt = tableView.getSelectionModel().getSelectedItems();
            for(Appointment appt: selectedAppt){
                apptID = String.valueOf(appt.getAppointmentID());
                title = appt.getTitle();
                thisAppt = appt;
            }
        }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    /**
     * Deletes a specific appointment from the database
     * @param event
     * @throws IOException
     */
    public void deleteAppointment(ActionEvent event) throws IOException {
        apptToUpdate();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm Appointment Delete");
        alert.setContentText("Are you sure you want to delete Appointment: "+title+ ","+thisAppt.getDescription()+"?" );
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                DBAppointment.deleteAppointment(apptID);
                deleteAppointmentMessage.setText("Appointment Title: " + title + ", ID: " + apptID + " deleted.");
                setTableView();
            } catch (Exception e) {
                deleteAppointmentMessage.setText("You must select an Appointment to delete first.");
            }
        }
    }
}
