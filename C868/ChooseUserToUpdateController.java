package C868;

import C868.Entities.User;
import C868.Helper.DBUser;
import C868.Helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ChooseUserToUpdateController {
    @FXML
    public TableView<User> tableView;
    @FXML
    public TableColumn<User, Integer> userIDCol;
    @FXML
    public TableColumn<User, String> userNameCol;
    @FXML
    public TableColumn<User, String> passwordCol;
    @FXML
    public TableColumn<User, String> createDateCol;
    @FXML
    public TableColumn<User, String> createdByCol;
    @FXML
    public TableColumn<User, String> lastUpdateDateCol;
    @FXML
    public TableColumn<User, String> lastUpdatedByCol;
    @FXML
    public Label updateUserMessage;
    String userID;
    String name;

    public void setTableView(){
        tableView.setItems(DBUser.getAllUsers());
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void userToUpdate() {
        ObservableList<User> selectedUser;
        selectedUser = tableView.getSelectionModel().getSelectedItems();
        for(User u: selectedUser){
            userID = String.valueOf(u.getUserID());
            System.out.println("USER ID choose = " + userID);
            name = u.getUserName();
        }
    }

    public void deleteUser(ActionEvent event) throws IOException {
        userToUpdate();
        if(userID != null && !name.equals(null)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirm User Delete");
            alert.setContentText("Are you sure you want to delete User: "+name+"?" );
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                DBUser.deleteUser(userID);
                updateUserMessage.setText("User "+name+" deleted.");
                setTableView();
            }

        }else{
            updateUserMessage.setText("You must select a Customer to delete first.");
        }
    }


    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    public void initialize() {
        JDBC.openConnection();
        setTableView();

    }
}
