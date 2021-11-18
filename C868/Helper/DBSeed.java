package C868.Helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBSeed {
    public static void loadDatabase() throws SQLException {
        JDBC.initialConnection();
        String sqlCreateStmt = "CREATE DATABASE if not exists client_schedule;";
        String sqlUseSample = "USE client_schedule;";
        String sqlCreateCustomerTable = "CREATE Table CUSTOMERS(Customer_ID INT NOT NULL AUTO_INCREMENT, " +
                "Customer_Name VARCHAR(50), Address VARCHAR(100), Postal_Code VARCHAR(6), Phone VARCHAR(20)" +
                ", Create_Date DATETIME, Created_By VARCHAR(50), Last_Update TIMESTAMP, Last_Updated_By " +
                "VARCHAR(50), PRIMARY KEY (Customer_ID));";
        String customerData1 = "Insert into CUSTOMERS(Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By) Values('Customer One', '123 Any St" +
                ". Atlanta, GA', '30328', '777-888-9999','2021-11-18 09:24:00', 'Admin', "+
                "'2021-11-18 09:24:00', 'Admin');";
        String customerData2 = "Insert into CUSTOMERS(Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By) Values('Customer Two', '321 Any St" +
                ". Atlanta, GA', '30328', '888-888-9999','2021-11-18 09:24:00', 'Admin', "+
                "'2021-11-18 09:24:00', 'Admin');";
        String sqlCreateTypeTable = "CREATE Table TYPES_OF_APPOINTMENTS(TypeID INT NOT NULL AUTO_INCREMENT," +
                " Appointment_Type VARCHAR(45), Length_of_Appointment_in_Minutes INT, Instrument VARCHAR(45), " +
                "PRIMARY KEY (TypeID));";
        String typeData1 = "INSERT INTO TYPES_OF_APPOINTMENTS(Appointment_Type, " +
                "Length_of_Appointment_in_Minutes, Instrument) Values('Electric Guitar Long', '60', " +
                "'Electric Guitar');";
        String typeData2 ="INSERT INTO TYPES_OF_APPOINTMENTS(Appointment_Type, " +
            "Length_of_Appointment_in_Minutes, Instrument)" +
                " Values('Electric Guitar Short', '30', 'Electric Guitar');";
        String typeData3 = "INSERT INTO TYPES_OF_APPOINTMENTS(Appointment_Type, " +
            "Length_of_Appointment_in_Minutes, Instrument)" +
                " Values('Acoustic Guitar Long', '60', 'Acoustic Guitar');";
        String typeData4 ="INSERT INTO TYPES_OF_APPOINTMENTS(Appointment_Type, " +
            "Length_of_Appointment_in_Minutes, " +
                "Instrument) Values('Acoustic Guitar Short', '30', 'Acoustic Guitar');";
        String sqlCreateUserTable = "CREATE Table USERS(User_ID INT NOT NULL AUTO_INCREMENT, User_Name " +
                "VARCHAR(50), Password TEXT, Create_Date DATETIME, Created_By VARCHAR(50), Last_Update " +
                "TIMESTAMP, Last_Updated_By VARCHAR(50), AdminUser TINYINT, PRIMARY KEY (User_ID)); ";
        String userData1 = "Insert into USERS(User_Name, Password, Create_Date, Created_By, Last_Update, " +
                "Last_Updated_By, AdminUser) Values('admin', 'admin_password', '2021-11-18 09:24:00', " +
                "'admin', '2021-11-18 09:24:00', 'admin', '1');";
        String userData2 = "Insert into USERS(User_Name, Password, Create_Date, Created_By, Last_Update, " +
                "Last_Updated_By, AdminUser) Values('owner', 'owner_password', '2021-11-18 09:24:00', " +
                "'admin', '2021-11-18 09:24:00', 'admin', '0');";
        String sqlCreateAppointmentTable = "CREATE table APPOINTMENTS(Appointment_ID INT NOT NULL " +
                "AUTO_INCREMENT, Title VARCHAR(50), Location VARCHAR(50), Type INT, Start DATETIME, End " +
                "DATETIME, Create_Date DATETIME, Created_By VARCHAR(50), Last_Update TIMESTAMP, " +
                "Last_Updated_By VARCHAR(50), Customer_ID INT, User_ID INT, PRIMARY KEY (Appointment_ID)," +
                "FOREIGN KEY (Customer_ID) REFERENCES CUSTOMERS(Customer_ID), " +
                "FOREIGN KEY (User_ID) REFERENCES USERS(User_ID));";
        String appointmentData1 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment1', " +
                "'Home', '1', '2021-11-20 09:00:00', '2021-11-20 10:00:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '1', '2');";
        String appointmentData2 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment2', " +
                "'Home', '2', '2021-11-21 09:00:00', '2021-11-21 09:30:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '1', '2');";
        String appointmentData3 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment3', " +
                "'Home', '3', '2021-12-22 09:00:00', '2021-12-22 10:00:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '1', '2');";
        String appointmentData4 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment4', " +
                "'Home', '4', '2021-11-23 09:00:00', '2021-11-23 09:30:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '1', '2');";
        String appointmentData5 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment5', " +
                "'Home', '1', '2021-11-24 09:00:00', '2021-11-24 10:00:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '2', '2');";
        String appointmentData6 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment2', " +
                "'Home', '2', '2021-12-25 09:00:00', '2021-12-25 09:30:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '2', '2');";
        String appointmentData7 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment3', " +
                "'Home', '3', '2021-11-26 09:00:00', '2021-11-26 10:00:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '2', '2');";
        String appointmentData8 = "Insert into APPOINTMENTS(Title, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) Values('Appointment4', " +
                "'Home', '4', '2021-12-27 09:00:00', '2021-12-27 09:30:00', '2021-11-18 09:24:00', 'owner'," +
                " '2021-11-18 09:24:00', 'owner', '2', '2');";


        try {
            //create DB and load table schema
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
            //load initial data into tables
            PreparedStatement loadCustomerData1 =
                    JDBC.getConnection().prepareStatement(customerData1);
            loadCustomerData1.execute();
            PreparedStatement loadCustomerData2 =
                    JDBC.getConnection().prepareStatement(customerData2);
            loadCustomerData2.execute();
            PreparedStatement loadTypeData1 =
                    JDBC.getConnection().prepareStatement(typeData1);
            loadTypeData1.execute();
            PreparedStatement loadTypeData2 =
                    JDBC.getConnection().prepareStatement(typeData2);
            loadTypeData2.execute();
            PreparedStatement loadTypeData3 =
                    JDBC.getConnection().prepareStatement(typeData3);
            loadTypeData3.execute();
            PreparedStatement loadTypeData4 =
                    JDBC.getConnection().prepareStatement(typeData4);
            loadTypeData4.execute();
            PreparedStatement loadUserData1 =
                    JDBC.getConnection().prepareStatement(userData1);
            loadUserData1.execute();
            PreparedStatement loadUserData2 =
                    JDBC.getConnection().prepareStatement(userData2);
            loadUserData2.execute();
            PreparedStatement loadApptData1 =
                    JDBC.getConnection().prepareStatement(appointmentData1);
            loadApptData1.execute();
            PreparedStatement loadApptData2 =
                    JDBC.getConnection().prepareStatement(appointmentData2);
            loadApptData2.execute();
            PreparedStatement loadApptData3 =
                    JDBC.getConnection().prepareStatement(appointmentData3);
            loadApptData3.execute();
            PreparedStatement loadApptData4 =
                    JDBC.getConnection().prepareStatement(appointmentData4);
            loadApptData4.execute();
            PreparedStatement loadApptData5 =
                    JDBC.getConnection().prepareStatement(appointmentData5);
            loadApptData5.execute();
            PreparedStatement loadApptData6 =
                    JDBC.getConnection().prepareStatement(appointmentData6);
            loadApptData6.execute();
            PreparedStatement loadApptData7 =
                    JDBC.getConnection().prepareStatement(appointmentData7);
            loadApptData7.execute();
            PreparedStatement loadApptData8 =
                    JDBC.getConnection().prepareStatement(appointmentData8);
            loadApptData8.execute();

        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
