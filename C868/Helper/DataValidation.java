package C868.Helper;

import javafx.scene.control.Alert;

public class DataValidation {

    public static void entryErrorAlert(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorField+ " Error");
        alert.setContentText("Please enter a valid "+ errorField);
        alert.showAndWait();
    }

    public static boolean isValidUserName(String userNameString){
        return userNameString.matches("^[a-zA-Z0-9_]{1,50}$");
    }

    public static boolean isValidPassword(String passwordString){
        return passwordString.matches("^[a-zA-Z0-9$%&*!_]{8,20}$");
    }

    public static boolean isValidPhoneNumber(String phoneString){
        return phoneString.matches("^(\\d{3}[- .]?){2}\\d{4}$");
    }

    public static boolean isValidName(String nameString){
        return nameString.matches("^[a-zA-Z]{1,20}+[ ]+[a-zA-Z]{1,20}+$");
    }
    public static boolean isValidAddress(String addressString){
        return addressString.matches("^[0-9]+\\s?[a-zA-Z0-9]+\\s?[A-Za-z]+[.]?+\\s?+[,]?+" +
                "\\s?+[A-Za-z]+[,]?+\\s?+[A-Za-z]+$");
    }

    public static boolean isValidPostalCode(String zipString){
        return zipString.matches("^\\d\\d\\d\\d\\d$");
    }

    public static boolean isValidTime(String timeString){
        return timeString.matches("^(0?[0-9]|1[0-9]|2[0-3]):[0-9]+$");
    }

    public static boolean isValidDate(String dateString){
        return dateString.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean isValidLocation(String locationString){
        return !locationString.equals("");
    }
    public static boolean isValidTitle(String titleString){
        return !titleString.equals("");
    }



}
