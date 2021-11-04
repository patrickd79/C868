package C868.Helper;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This is the class used to access the database and open and close the connection
 * @author patrickdenney
 */
public abstract class JDBC {
    private static final String DBName = "client_schedule";
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DBName + "?connectionTimeZone = SERVER";
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DBName + "?connectionTimeZone = UTC";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DBName + "?useLegacyDatetimeCode=false&serverTimeZone = UTC";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    /**
     * Method to open the connection to the database
     */
    public static void openConnection(){
        try{
            Class.forName(driver);//locate driver
            connection = DriverManager.getConnection(DB_URL, username, password);//reference to connection object
            System.out.println("Connection successful");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            //sends an alert dialog to the user if unable to establish a connection to the DB
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Database Error");
            alert.setContentText("Unable to Connect to the Database");
            alert.showAndWait();
        }
    }

    /**
     *
     * @return Returns an instance of the Connection object
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try{
            connection.close();
            System.out.println("Connection closed");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}

