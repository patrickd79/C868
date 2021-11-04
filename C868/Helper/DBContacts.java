package C868.Helper;

import C868.Entities.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a helper class to access the Contacts data
 * @author patrickdenney
 */
public class DBContacts {
    /**
     *
     * @param name contact name
     * @return Returns the Contact object that has a name that matches the name passed
     */
    public static Contact getAContactByName(String name){
        Contact contact = null;
        try{
            String sqlStmt = "SELECT Contact_ID, Contact_Name, Email FROM CONTACTS WHERE Contact_Name = '"+name+"';";
            PreparedStatement contactPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = contactPS.executeQuery();
            while(results.next()){
                int contact_id = results.getInt("Contact_ID");
                String contact_name = results.getString("Contact_Name");
                String email = results.getString("Email");
                contact = new Contact(contact_id,contact_name,email);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return contact;
    }
    /**
     *
     * @param id contact id
     * @return Returns the Contact object that has a id as the id passed in
     */
    public static Contact getAContactByID(String id){
        Contact contact = null;
        try{
            String sqlStmt = "SELECT Contact_ID, Contact_Name, Email FROM CONTACTS WHERE Contact_ID = '"+id+"';";
            PreparedStatement contactPS = JDBC.getConnection().prepareStatement(sqlStmt);
            ResultSet results = contactPS.executeQuery();
            while(results.next()){
                int contact_id = results.getInt("Contact_ID");
                String contact_name = results.getString("Contact_Name");
                String email = results.getString("Email");
                contact = new Contact(contact_id,contact_name,email);
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return contact;
    }

    /**
     *
     * @return Returns an ObservableList<Contact> of all the contacts in the database
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        Contact contact = null;
            try{
                String sqlStmt = "SELECT * FROM CONTACTS;";
                PreparedStatement contactPS = JDBC.getConnection().prepareStatement(sqlStmt);
                ResultSet results = contactPS.executeQuery();
                while(results.next()){
                    int contact_id = results.getInt("Contact_ID");
                    String contact_name = results.getString("Contact_Name");
                    String email = results.getString("Email");
                    contact = new Contact(contact_id,contact_name,email);
                    contactList.add(contact);
                }
            }
            catch(SQLException throwable){
                throwable.printStackTrace();
            }
            return contactList;
    }
}
