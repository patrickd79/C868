package C868.Helper;

import C868.Entities.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is a helper class for accessing and manipulating Appointment data.
 * @author Patrick Denney
 */
public class DBAppointment {
    public Connection connection;

    /**
     *
     * @param id user ID
     * @return Returns the total number of appointments for a specified user
     */
    public static int getApptCountForAUser(String id){
        int apptCount = 0;
        String sqlStmt = "SELECT COUNT(Appointment_ID) AS Appointment_Count FROM APPOINTMENTS WHERE User_ID = '"+id+"';";
        try {
            //prepare the sql stmt
            PreparedStatement getApptCountPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            ResultSet results = getApptCountPS.executeQuery();
            while(results.next()) {
                 apptCount = results.getInt("Appointment_Count");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return apptCount;
    }

    /**
     * Deletes specified appointment
     * @param id appointment ID
     */
    public static void deleteAppointment(String id){
        String sqlStmt = " DELETE FROM APPOINTMENTS WHERE Appointment_ID= '"+id+"';";
        try {
            //prepare the sql stmt
            PreparedStatement deleteAppointPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            deleteAppointPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Deletes all the appointments for a specified customer
     * @param id customer ID
     */
    public static void deleteAppointmentsForASingleCustomer(String id){
        String sqlStmt = " DELETE FROM APPOINTMENTS WHERE Customer_ID= '"+id+"';";
        try {
            //prepare the sql stmt
            PreparedStatement deleteAppointPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            deleteAppointPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     *
     * @param id customer ID
     * @return Returns an observable list of all the appointments for a specified customer
     */
    public static ObservableList<Appointment> getAppointmentsForASingleCustomerByID(String id){
        Appointment appt;
        ObservableList<Appointment> appts = FXCollections.observableArrayList();
        String sqlStmt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS WHERE Customer_ID= '"+id+"';";
        try {
            //prepare the sql stmt
            PreparedStatement customerAppointPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            ResultSet results = customerAppointPS.executeQuery();

            while(results.next()) {
                int apptID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                String start = results.getString("Start");
                String end = results.getString("End");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int custID = results.getInt("Customer_ID");
                int userID = results.getInt("User_ID");
                int contactID = results.getInt("Contact_ID");
                String localStart = TimeZones.convertToCurrentTimeZone(start);
                String localEnd = TimeZones.convertToCurrentTimeZone(end);
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);
                appt = new Appointment(apptID, title, description, location, type, localStart, localEnd, createDateLocal, createdBy, updateDateLocal, lastUpdatedBy, custID, userID, contactID);
                appts.add(appt);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appts;
    }

    /**
     *
     * @param id user id
     * @return Returns an observable list of all appointments for a specified user
     */
    public static ObservableList<Appointment> getAppointmentsForASingleUserByID(String id){
        Appointment appt;
        ObservableList<Appointment> appts = FXCollections.observableArrayList();
        String sqlStmt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID FROM APPOINTMENTS WHERE User_ID= '"+id+"';";
        System.out.println("Get User SQL stmt: "+sqlStmt);
        try {
            //prepare the sql stmt
            PreparedStatement userAppointPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            ResultSet results = userAppointPS.executeQuery();

            while(results.next()){
                int apptID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                String start = results.getString("Start");
                String end = results.getString("End");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int custID = results.getInt("Customer_ID");
                int userID = results.getInt("User_ID");
                int contactID = results.getInt("Contact_ID");
                String localStart = TimeZones.convertToCurrentTimeZone(start);
                String localEnd = TimeZones.convertToCurrentTimeZone(end);
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);
                appt = new Appointment(apptID,title,description,location,type,localStart,localEnd,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy,custID,userID,contactID);
                appts.add(appt);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appts;
    }

    /**
     * Adds an appointment to the database
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date and time
     * @param end appointment end date and time
     * @param createdBy user who created the appointment
     * @param customerId customer associated with the appointment
     * @param userId user associated with the appointment
     * @param contactId contact associated with the appointment
     */
    public static void addAppointment(String title,String description,String location,String type,
                                      String start, String end, String createdBy, String customerId,
                                      String userId, String contactId){

        Date date = new Date();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
        String create_date = sqlDate.toString();

        String sqlStmt = "INSERT into APPOINTMENTS(Title, Description, Location, Type, Start, "+
                "End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, "+
                "User_ID, Contact_ID)Values('"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+
                TimeZones.convertToUTCTimeZone(start)+"', '"+TimeZones.convertToUTCTimeZone(end)+"', '"+TimeZones.getUTCTime()+"', '"+createdBy+"', '"+TimeZones.getUTCTime()+"', '"+createdBy+"', '"+customerId+
                "', '"+userId+"', '"+contactId+"');";
        try {
            //prepare the sql stmt
            PreparedStatement appointPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            appointPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Updates the data for a specified appointment
     * @param id appointment id
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startDate start date
     * @param startTime start time
     * @param endDate end date
     * @param endTime end time
     * @param updatedBy user updating the appointment
     * @param customerID customer associated with the appointment
     * @param userID user associated with the appointment
     * @param contactID contact associated with the appointment
     */
    public static void updateAppointment(String id, String title, String description, String location,
                                         String type, String startDate,String startTime, String endDate,String endTime,String updatedBy, String customerID,
                                         String userID, String contactID){


        String start = startDate+" "+startTime;
        String end = endDate+" "+endTime;

        String sqlStmt = "UPDATE APPOINTMENTS " +
                "SET" +
                " Title = '"+title+"'," +
                " Description = '"+description+"'," +
                " Location = '"+location+"'," +
                " Type = '"+type+"'," +
                " Start = '"+TimeZones.convertToUTCTimeZone(start)+"',"+
                " End = '"+TimeZones.convertToUTCTimeZone(end)+"',"+
                " Last_Update = '"+TimeZones.getUTCTime()+"'," +
                " Last_Updated_By = '"+updatedBy+"'," +
                " Customer_ID = "+customerID+"," +
                " User_ID = "+userID+"," +
                " Contact_ID = "+contactID+"" +
                " WHERE Appointment_ID = "+id+";";

        System.out.println(sqlStmt);
        try {
            //prepare the sql stmt
            PreparedStatement customerPS = JDBC.getConnection().prepareStatement(sqlStmt);
            //execute the sql command
            customerPS.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    /**
     *
     * @return Returns an observable list of all the appointments in the database
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
        Appointment appt = null;
        try{
            String sqlStmt = "SELECT * FROM APPOINTMENTS;";
            PreparedStatement apptPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = apptPS.executeQuery();

            while(results.next()){
                int apptID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                String start = results.getString("Start");
                String end = results.getString("End");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int custID = results.getInt("Customer_ID");
                int userID = results.getInt("User_ID");
                int contactID = results.getInt("Contact_ID");
                String localStart = TimeZones.convertToCurrentTimeZone(start);
                String localEnd = TimeZones.convertToCurrentTimeZone(end);
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);
                appt = new Appointment(apptID,title,description,location,type,localStart,localEnd,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy,custID,userID,contactID);
                apptList.add(appt);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }

        return apptList;
    }

    /**
     *
     * @param id appointment id
     * @return Returns an appointment object
     */
    public static Appointment getAppointmentByID(String id) {
        Appointment appt = null;
        try{
            String sqlStmt = "SELECT " +
                    "Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID" +
                    " FROM APPOINTMENTS" +
                    " WHERE Appointment_ID = "+id+";";
            PreparedStatement apptPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = apptPS.executeQuery();

            while(results.next()){
                int apptID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                String start = results.getString("Start");
                String end = results.getString("End");
                String createDate = results.getString("Create_Date");
                String createdBy = results.getString("Created_By");
                String lastUpdate = results.getString("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int custID = results.getInt("Customer_ID");
                int userID = results.getInt("User_ID");
                int contactID = results.getInt("Contact_ID");
                String localStart = TimeZones.convertToCurrentTimeZone(start);
                String localEnd = TimeZones.convertToCurrentTimeZone(end);
                String createDateLocal = TimeZones.convertToCurrentTimeZone(createDate);
                String updateDateLocal = TimeZones.convertToCurrentTimeZone(lastUpdate);
                appt = new Appointment(apptID,title,description,location,type,localStart,localEnd,createDateLocal,createdBy,updateDateLocal,lastUpdatedBy,custID,userID,contactID);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return appt;
    }

    /**
     *
     * @param date a String of the date value of which to extract the month value from
     * @return a String with a value of the month from 1-12
     */
    public static String extractMonth(String date){
        char[] ca = date.toCharArray();
        StringBuilder sb = new StringBuilder();
        String month;
        for(int i = 5; i < 7; i++){
            sb.append(ca[i]);
        }
        month = sb.toString();
        //System.out.println("Month ="+month+" : date = "+date);
        return month;
    }

    /**
     *
     * @param date a String of the date value of which to extract the year value from
     * @return a 4 digit string which corresponds to the value of the year
     */
    public static String extractYear(String date){
        char[] ca = date.toCharArray();
        StringBuilder sb = new StringBuilder();
        String year;
        for(int i = 0; i < 4; i++){
            sb.append(ca[i]);
        }
        year = sb.toString();
        //System.out.println("Year ="+year+" : date = "+date);
        return year;
    }

    /**
     *
     * @param date a String of the date value of which to extract the day value from
     * @return a 2 digit string corresponding to the day of the month value
     */
    public static String extractDay(String date){
        char[] ca = date.toCharArray();
        StringBuilder sb = new StringBuilder();
        String day;
        for(int i = 8; i < 10; i++){
            sb.append(ca[i]);
        }
        day = sb.toString();
        //System.out.println("Day ="+day+" : date = "+date);
        return day;
    }

    /**
     *
     * @param Date a String of the date value of which to extract the week of the year value from
     * @return a 2 digit string corresponding to the week of the year value
     */
    public static String extractWeek(String Date)  {
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(extractYear(Date));
        //have to subtract 1 from the month to account for the fact that calendar months are zero indexed
        int month = Integer.parseInt(extractMonth(Date))-1;
        int day = Integer.parseInt(extractDay(Date));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        //System.out.println("Date calendar set to:  "+calendar.getTime());
        return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     *
     * @return Returns an observable list of appointments that have start dates matching the current month
     */
    public static ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> appts = getAllAppointments();
        ObservableList<Appointment> apptsThatMatch = FXCollections.observableArrayList();
        LocalDate date = LocalDate.now();
        int monthNumber = date.getMonthValue();
        int yearNumber = date.getYear();
        String currentMonth = String.valueOf(monthNumber);
        String currentYear = String.valueOf(yearNumber);


        for(Appointment a: appts){
            String apptMonth = extractMonth(a.getStart());
            String apptYear = extractYear(a.getStart());
            if(apptMonth.equals(currentMonth) && apptYear.equals(currentYear)){
                apptsThatMatch.add(a);
            }
        }
        return apptsThatMatch;
    }

    /**
     *
     * @return Returns an observable list of appointments that have start dates falling within the current week
     */
    public static ObservableList<Appointment> getAppointmentsByWeek() {
        ObservableList<Appointment> appts = getAllAppointments();
        ObservableList<Appointment> apptsThatMatch = FXCollections.observableArrayList();
        //get current week number
        Calendar now = Calendar.getInstance();
        String currentWeek = String.valueOf(now.get(Calendar.WEEK_OF_YEAR));
        LocalDate date = LocalDate.now();
        int yearNumber = date.getYear();
        String currentYear = String.valueOf(yearNumber);

        for(Appointment a: appts){
            String apptWeek = extractWeek(a.getStart());
            String apptYear = extractYear(a.getStart());

            if(apptWeek.equals(currentWeek) && apptYear.equals(currentYear)){
                apptsThatMatch.add(a);
            }
            //System.out.println(currentWeek +": "+ extractWeek(a.getStart()));

        }

        return apptsThatMatch;
    }

}
