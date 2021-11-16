package C868.Helper;

import javafx.scene.control.Alert;

public class DataValidation {

    public static void entryErrorAlert(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorField+ " Error");
        alert.setContentText("Please enter a valid "+ errorField);
        alert.showAndWait();
    }

    public static boolean isValidPhoneNumber(String phoneString){
        return phoneString.matches("^(\\d{3}[- .]?){2}\\d{4}$");
    }

    public static boolean isValidName(String nameString){
        return nameString.matches("^[a-zA-Z]+\\s?[a-zA-Z]?+$");
    }

    public static boolean isValidAddress(String addressString){
        return addressString.matches("^[0-9]+\\s[0-9a-zA-Z]+\\s[a-zA-Z]+\\.?+,\\s?[a-zA-Z]$");
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
