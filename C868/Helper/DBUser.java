package C868.Helper;

import C868.Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a helper class to access User data
 * @author patrickdenney
 */
public class DBUser {
    /**
     *
     * @param name user name
     * @return Returns a User object with the name matching the one passed in
     */
    public static User getAUserByName(String name){
        User user = null;
        try{
            String sqlStmt = "SELECT User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                    "FROM USERS WHERE User_Name = '"+name+"';";
            PreparedStatement userPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = userPS.executeQuery();
            while(results.next()){
                int userID = results.getInt("User_ID");
                System.out.println("User_ID"+userID);
                String userName = results.getString("User_Name");
                System.out.println("User_Name"+userName);
                String password = results.getString("Password");
                System.out.println("Password"+password);
                String createDate = results.getString("Create_Date");
                System.out.println("Create_Date"+createDate);
                String createBy = results.getString("Created_By");
                System.out.println("Created_By"+createBy);
                String lastUpdate = results.getString("Last_Update");
                System.out.println("Last_Update"+lastUpdate);
                String lastUpdateBy = results.getString("Last_Updated_By");
                System.out.println("Last_Updated_By"+lastUpdateBy);
                user = new User(userID,userName,password,createDate,createBy,lastUpdate,lastUpdateBy);
            }
        }
        catch(SQLException throwable){
            System.out.println("problem getting user");
            throwable.printStackTrace();
        }
        return user;
    }

    /**
     *
     * @param id user id
     * @return Returns a User object with an id matching the id passed in
     */
    public static User getAUserByID(int id){
        User user = null;
        try{
            String sqlStmt = "SELECT User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                    "FROM USERS WHERE User_ID = "+id+";";
            PreparedStatement userPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = userPS.executeQuery();
            while(results.next()){
                int userID = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                String password = results.getString("Password");
                String createDate = results.getString("Create_Date");
                String createBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdateBy = results.getString("Last_Updated_By");
                user = new User(userID,userName,password,createDate,createBy,lastUpdate,lastUpdateBy);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return user;
    }

    /**
     *
     * @return Returns an ObservableList<User> off all users in the database
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();
        User user = null;
        try{
            String sqlStmt = "SELECT * FROM USERS;";
            PreparedStatement userPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = userPS.executeQuery();
            while(results.next()){
                int userID = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                String password = results.getString("Password");
                String createDate = results.getString("Create_Date");
                String createBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdateBy = results.getString("Last_Updated_By");
                user = new User(userID,userName,password,createDate,createBy,lastUpdate,lastUpdateBy);
                userList.add(user);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return userList;
    }
}
