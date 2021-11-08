package C868.Helper;

import C868.Entities.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBType {

    public static void addType(String name,String length, String instrument){
        String sqlStmt = "Insert into TYPES_OF_APPOINTMENTS(Appointment_Type, Length_of_Appointment_in_Minutes, Instrument)" +
                "Values(?,?,?);";
        try {
            //prepare the sql stmt
            PreparedStatement typePS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            typePS.setString(1,name);
            typePS.setString(2,length);
            typePS.setString(3,instrument);
            //execute the sql command
            typePS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void updateType(String id, String name,String length, String instrument){
        String sqlStmt = "UPDATE TYPES_OF_APPOINTMENTS " +
                "SET" +
                " Appointment_Type = ?," +
                " Length_of_Appointment_in_Minutes = ?,"+
                " Instrument = ?," +
                " WHERE TypeID = ?;";
        try {
            //prepare the sql stmt
            PreparedStatement typePS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            typePS.setString(1,name);
            typePS.setString(2,length);
            typePS.setString(3,instrument);
            typePS.setString(4,id);
            //execute the sql command
            typePS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void deleteType(String id){
        String sqlStmt = "DELETE FROM TYPES_OF_APPOINTMENTS WHERE TypeID = ?;";

        try {
            //prepare the sql stmt
            PreparedStatement typePS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            typePS.setString(4,id);
            //execute the sql command
            typePS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static Type getATypeByName(String name){
        Type type = null;
        try{
            String sqlStmt = "SELECT TypeID, Appointment_Type, Length_of_Appointment_in_Minutes, Instrument"+
                    " FROM TYPES_OF_APPOINTMENTS WHERE Appointment_Type = ?;";
            PreparedStatement typePS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            typePS.setString(1,name);
            ResultSet results = typePS.executeQuery();
            while(results.next()){
                int typeID = results.getInt("TypeID");
                String typeName = results.getString("Appointment_Type");
                String length = results.getString("Length_of_Appointment_in_Minutes");
                String instrument = results.getString("Instrument");

                type = new Type(String.valueOf(typeID),typeName,length,instrument);
            }
        }
        catch(SQLException throwable){
            System.out.println("problem getting type");
            throwable.printStackTrace();
        }
        return type;
    }

    public static ObservableList<Type> getAllTypes(){
        ObservableList<Type> types = FXCollections.observableArrayList();
        Type type = null;
        try{
            String sqlStmt = "Select * From TYPES_OF_APPOINTMENTS;";
            PreparedStatement typePS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = typePS.executeQuery();
            while(results.next()){
                int typeID = results.getInt("TypeID");
                String typeName = results.getString("Appointment_Type");
                String length = results.getString("Length_of_Appointment_in_Minutes");
                String instrument = results.getString("Instrument");
                type = new Type(String.valueOf(typeID),typeName,length,instrument);
                types.add(type);
            }
        }
        catch(SQLException throwable){
            System.out.println("problem getting types");
            throwable.printStackTrace();
        }
        return types;
    }
}
