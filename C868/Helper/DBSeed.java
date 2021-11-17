package C868.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBSeed {
    public static void loadDatabase() throws SQLException {
        JDBC.openConnection();
        String sqlCreateStmt = "CREATE DATABASE if not exists Sample;";
        String sqlUseSample = "USE Sample;";
        String sqlCreateCustomerTable = "CREATE Table CUSTOMERS(Customer_ID INT NOT NULL AUTO_INCREMENT, " +
                "Customer_Name VARCHAR(50), Address VARCHAR(100), Postal_Code VARCHAR(6), Phone VARCHAR(10)" +
                ", Create_Date DATETIME, Created_By VARCHAR(50), Last_Update TIMESTAMP, Last_Updated_By " +
                "VARCHAR(50), PRIMARY KEY (Customer_ID));";
        String sqlCreateTypeTable = "CREATE Table TYPES_OF_APPOINTMENTS(TypeID INT NOT NULL AUTO_INCREMENT," +
                " " +
                "Appointment_Type VARCHAR(45), Length_of_Appointment_in_Minutes INT, Instrument VARCHAR(45)" +
                ", PRIMARY KEY (TypeID));";
        String sqlCreateUserTable = "CREATE Table USERS(User_ID INT NOT NULL AUTO_INCREMENT, User_Name " +
                "VARCHAR(50), Password TEXT, Create_Date DATETIME, Created_By VARCHAR(50), Last_Update " +
                "TIMESTAMP, Last_Updated_By VARCHAR(50), AdminUser TINYINT, PRIMARY KEY (User_ID)); ";
        String sqlCreateAppointmentTable = "CREATE table APPOINTMENTS(Appointment_ID INT NOT NULL " +
                "AUTO_INCREMENT, Title VARCHAR(50), Location VARCHAR(50), Type INT, Start DATETIME, End " +
                "DATETIME, Create_Date DATETIME, Created_By VARCHAR(50), Last_Update TIMESTAMP, " +
                "Last_Updated_By VARCHAR(50), Customer_ID INT, User_ID INT, PRIMARY KEY (Appointment_ID)," +
                "FOREIGN KEY (Customer_ID) REFERENCES CUSTOMERS(Customer_ID), " +
                "FOREIGN KEY (User_ID) REFERENCES USERS(User_ID));";

        try {
            PreparedStatement createDB = JDBC.getConnection().prepareStatement(sqlCreateStmt);
            createDB.execute();
            PreparedStatement useSample = JDBC.getConnection().prepareStatement(sqlUseSample);
            useSample.execute();
            PreparedStatement addCustomerTable =
                    JDBC.getConnection().prepareStatement(sqlCreateCustomerTable);
            addCustomerTable.execute();
            PreparedStatement addTypeTable = JDBC.getConnection().prepareStatement(sqlCreateTypeTable);
            addTypeTable.execute();
            PreparedStatement addUserTable = JDBC.getConnection().prepareStatement(sqlCreateUserTable);
            addUserTable.execute();
            PreparedStatement addApptsTable = JDBC.getConnection().prepareStatement(sqlCreateAppointmentTable);
            addApptsTable.execute();
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
