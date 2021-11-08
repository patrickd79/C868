package C868;

import C868.Entities.Appointment;
import C868.Helper.DBAppointment;
import C868.Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerApptReportController {

    public TableView<Appointment> tableView;
    public TableColumn<Appointment, Integer> IDCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> locationCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> startCol;
    public TableColumn<Appointment, String> endCol;
    public TableColumn<Appointment, String> createDateCol;
    public TableColumn<Appointment, String> createdByCol;
    public TableColumn<Appointment, String> lastUpdateDateCol;
    public TableColumn<Appointment, String> lastUpdatedByCol;
    public TableColumn<Appointment, String> customerIDCol;
    public TableColumn<Appointment, String> userIDCol;

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void goToReportsWindow(ActionEvent event) throws IOException {
        Parent updateAppointmentWindow = FXMLLoader.load(getClass().getResource("reports.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentWindow);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(updateAppointmentScene);
        window.show();
    }

    public void setTableView(){
        tableView.setItems(DBAppointment.getAppointmentsForASingleCustomerByID(ReportsController.customerID));
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
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void initialize() {
        JDBC.openConnection();
        setTableView();
    }
}
