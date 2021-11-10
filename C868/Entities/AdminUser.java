package C868.Entities;

/**
 * Polymorphic class if User with increased privileges.
 */
public class AdminUser extends User{

    public AdminUser(int userID, String userName, String password, String createdDate, String createdBy, String lastUpdate, String lastUpdatedBy, boolean admin) {
        super(userID, userName, password, createdDate, createdBy, lastUpdate, lastUpdatedBy, admin);
    }

    public AdminUser() {
        super();
    }

    @Override
    public boolean authorized() {
        System.out.println(this.getUserName()+ " is an admin user.");
        return true;
    }
}
