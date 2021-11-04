

package C868;

import C868.Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    //sets the user language and country settings based on user's local settings
    public static String userLanguage = Locale.getDefault().getLanguage();
    public static String userCountry = Locale.getDefault().getCountry();
    //public static String userLanguage = "fr";
    //public static String userCountry = "CA";
    //sets the zone string based on user's local system settings
    public static String zone = ZoneId.systemDefault().getDisplayName(TextStyle.FULL,
            Locale.getDefault());
    public static String zoneID = ZoneId.systemDefault().getId();
    //public static Locale locale = new Locale(userLanguage, userCountry);
    public static ResourceBundle resourceBundle;
    //creates an instance of Resource bundle depending on results of setResourceBundle method
    public static ResourceBundle french = ResourceBundle.getBundle("C868/Bundle", new Locale("fr", "CA"));
    public static ResourceBundle english = ResourceBundle.getBundle("C868/Bundle", new Locale("en", "US"));
    /**
     * Lambda expression to simplify returning to the main menu from the other windows in the
     * application. It reduces some of the clutter in the code and abstracts away some
     * of the repeated code to a simpler lambda expression.
     */
    static GoToMain mainScreen = event -> {
        JDBC.closeConnection();
        Parent mainMenu = FXMLLoader.load(Main.class.getResource("mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    };

    /**
     * Selects the resource bundle to be used based on the user's language settings
     * @param userLanguage user's default system language
     */
    public void setResourceBundle(String userLanguage){
        if(userLanguage.equals("fr")){
            resourceBundle = french;
        }else{
            resourceBundle = english;
        }
    }



    @Override
    public void start(Stage stage) throws Exception{
        //set the resource bundle to use for the current user's experience
        setResourceBundle(userLanguage);
        Date date = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
        System.out.println(sqlDate.toString());
        // Load the FXML file
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        // Build the scene graph
        Scene scene = new Scene(parent);
        // Display window using the scene graph
        stage.setTitle(resourceBundle.getString("SchedulingApplication"));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> exitApplication());
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * closes the application on the click of the exit button
     */
    public void exitApplication(){
        JDBC.closeConnection();
        System.out.println("Closing");
        System.exit(0);
    }
}
