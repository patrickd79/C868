package C868.Helper;

import C868.Entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * This is a helper class to access Customer data
 * @author patrickdenney
 */
public class DBCustomer {


    /**
     * Updates the data for the customer specified by the customer id
     * @param id customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param updatedBy user updating the record
     */
    public static void updateCustomer(String id, String name, String address, String postalCode, String phone, String updatedBy){

        //this creates a local timestamp
        Date date = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
        String update_date = TimeZones.convertToUTCTimeZone(sqlDate.toString()) ;
        System.out.println(update_date);

        String sqlStmt = "UPDATE CUSTOMERS " +
                "SET" +
                " Customer_Name = ?," +
                " Address = ?," +
                " Postal_Code = ?," +
                " Phone = ?," +
                " Last_Update = ?," +
                " Last_Updated_By = ?"+
                " WHERE Customer_ID = ?;";

        System.out.println(sqlStmt);
        try {
            //prepare the sql stmt
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            customerPS.setString(1,name);
            customerPS.setString(2,address);
            customerPS.setString(3,postalCode);
            customerPS.setString(4,phone);
            customerPS.setString(5,TimeZones.getUTCTime());
            customerPS.setString(6,updatedBy);
            customerPS.setString(7,id);
            //execute the sql command
            customerPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    /**
     * Deletes the specified customer from the database
     * @param id customer id
     */
    public static void deleteCustomer(String id){
        String sqlStmt = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?;";

        try {
            //prepare the sql stmt
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            customerPS.setString(1,id);
            //execute the sql command
            customerPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Adds a customer object to the database with the specified data
     * @param name customer name
     * @param address customer address
     * @param postalCode customer's postal code
     * @param phone customer's phone number
     * @param createdBy user creating the record
     */
    public static void addCustomer(String name, String address, String postalCode, String phone, String createdBy){

        String sqlStmt = "Insert into CUSTOMERS(Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By)" +
                "Values(?,?,?,?,?,?,?,?);";
        try {
            //prepare the sql stmt
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            customerPS.setString(1,name);
            customerPS.setString(2,address);
            customerPS.setString(3,postalCode);
            customerPS.setString(4,phone);
            customerPS.setString(5,TimeZones.getUTCTime());
            customerPS.setString(6,createdBy);
            customerPS.setString(7,TimeZones.getUTCTime());
            customerPS.setString(8,createdBy);
            //execute the sql command
            customerPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     *
     * @param name customer name
     * @return returns a Customer object that has a name matching the name passed in
     */
    public static Customer getACustomerByName(String name){
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        Customer cust = null;

        try{
            String sqlStmt = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                    "FROM CUSTOMERS WHERE Customer_Name = ?;";
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            customerPS.setString(1,name);
            ResultSet results = customerPS.executeQuery();
            while(results.next()){
                int customerID = results.getInt("Customer_ID");
                String Customer_Name = results.getString("Customer_Name");
                String address = results.getString("Address");
                String postalCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);

                cust = new Customer(customerID,Customer_Name,address,postalCode,phone,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy);
                customer.add(cust);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return cust;
    }

    /**
     *
     * @param id customer id
     * @return Returns a Customer object with an id matching the id passed in
     */
    public static Customer getACustomerByID(int id){
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        Customer cust = null;
        try{
            String sqlStmt = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                    "FROM CUSTOMERS WHERE Customer_ID = ?;";
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //then insert value to prevent SQL injection attack
            customerPS.setInt(1,id);
            ResultSet results = customerPS.executeQuery();
            while(results.next()){
                int customerID = results.getInt("Customer_ID");
                String Customer_Name = results.getString("Customer_Name");
                String address = results.getString("Address");
                String postalCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);

                cust = new Customer(customerID,Customer_Name,address,postalCode,phone,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy);
                customer.add(cust);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return cust;
    }

    /**
     *
     * @return Returns an ObservableList<Customer> of all customers in the database
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Customer cust = null;
        try{
            String sqlStmt = "SELECT * FROM CUSTOMERS;";
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = customerPS.executeQuery();

            while(results.next()){
                int customerID = results.getInt("Customer_ID");
                String Customer_Name = results.getString("Customer_Name");
                String address = results.getString("Address");
                String postalCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);

                cust = new Customer(customerID,Customer_Name,address,postalCode,phone,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy);
                customerList.add(cust);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return customerList;
    }

}
