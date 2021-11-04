package C868.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a helper class to access the First Level Division data
 * @author patrickdenney
 */
public class DBFirstLevDiv {
    /**
     *
     * @param countryId country id
     * @return Returns an ObservableList<String> of all the division names in the database that have a
     * country id matching the id passed in
     */
    public static ObservableList<String> getDivNames(int countryId){
        ObservableList<String> divNames= FXCollections.observableArrayList();
        String sqlStmt = "Select Division_ID, Division from FIRST_LEVEL_DIVISIONS where Country_ID = "+countryId+";";
        try {
            //prepare the sql stmt
            PreparedStatement fldPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            fldPS.execute();
            ResultSet results = fldPS.executeQuery();

            while(results.next()){
                int divId = results.getInt("Division_ID");
                String divName = results.getString("Division");
                //FLDivision fld = new FLDivision(divId, divName);
                divNames.add(divName);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divNames;
    }
    /**
     *
     * @param divName division name
     * @return Returns a String with a value of the division id of the division with the same name as the name passed in
     */
    public static String getDivID(String divName) throws SQLException {
        ResultSet results = null;
        int divID = 0;
        String sqlStmt = "Select Division_ID from FIRST_LEVEL_DIVISIONS where Division = '"+divName+"';";
        try {
            //prepare the sql stmt
            PreparedStatement fldPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            fldPS.execute();
            results = fldPS.executeQuery();
            while(results.next()) {
                divID = results.getInt("Division_ID");
                }
            } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return String.valueOf(divID);
    }

    public static String getDivName(String divId) throws SQLException {
        ResultSet results = null;
        String divName = null;
        String sqlStmt = "Select Division from FIRST_LEVEL_DIVISIONS where Division_ID = '"+divId+"';";
        try {
            //prepare the sql stmt
            PreparedStatement fldPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            fldPS.execute();
            results = fldPS.executeQuery();
            while(results.next()) {
                divName = results.getString("Division");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divName;
    }

    public static String getCountryID(String divID) throws SQLException {
        ResultSet results = null;
        int countryID = 0;
        String sqlStmt = "Select Country_ID from FIRST_LEVEL_DIVISIONS where Division_ID = '"+divID+"';";
        try {
            //prepare the sql stmt
            PreparedStatement fldPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            fldPS.execute();
            results = fldPS.executeQuery();
            while(results.next()) {
                countryID = results.getInt("Country_ID");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return String.valueOf(countryID);
    }

}
