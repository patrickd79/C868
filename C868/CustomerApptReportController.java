package C868;

import C868.Entities.Appointment;
import C868.Helper.DBAppointment;
import C868.Helper.DBType;
import C868.Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public TextField searchField;
    public Label message;
    ObservableList<Appointment> thisCustomerAppts = DBAppointment.getAppointmentsForASingleCustomerByID(ReportsController.customerID);

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
        tableView.setItems(thisCustomerAppts);
        IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public ObservableList<Appointment> searchAppointmentForTypeResultsList(String searchStr){
        ObservableList<Appointment> results = FXCollections.observableArrayList();
        String typeName;
        String typeID;
        for(Appointment a: thisCustomerAppts){
            typeID = DBAppointment.getAppointmentByID(String.valueOf(a.getAppointmentID())).getType();
            typeName = DBType.getATypeByID(typeID).getTypeName();
            if(typeName.toLowerCase().contains(searchStr.toLowerCase())){
                results.add(a);
            }
        }
        return results;
    }

    public void getSearchResults(ActionEvent event) throws IOException{
        try {
            String name = searchField.getText();
            ObservableList<Appointment> appts = searchAppointmentForTypeResultsList(name);
            tableView.setItems(appts);
            //searchField.setText("");
        }catch(Exception ex){
            ex.printStackTrace();
            message.setText("Type not found. Please search for a different type.");
        }
    }

    public void initialize() {
        JDBC.openConnection();
        setTableView();
    }
}
