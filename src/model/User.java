package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a user for the app
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class User {

    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    /** Authority level of user */
    private final ObjectProperty<UserLevel> level = new SimpleObjectProperty<>();

    /** Can only get the username, setting username only happens during user
     * creation */
    public String getEmail() { return email.get(); }
    public void setEmail(String email) {
        this.email.set(email);
    }
    /** Can only get the password, setting password only happens during user
     * creation */
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password.set(password);
    }

    /** Can only get the user level, setting level only happens during user
     * creation */
    public UserLevel getUserLevel() { return level.get(); }

    /**
     * Main constructor
     * @param uname     Username
     * @param pass      Password
     * @param level     Authority level
     */
    public User(String uname, String pass, UserLevel level) {
        email.set(uname);
        password.set(pass);
        this.level.set(level);
    }

    /**
     * Additonal constructor to create user with default NORMAL capabilities
     * @param uname     Username
     * @param pass      Password
     */
    public User(String uname, String pass) {
        email.set(uname);
        password.set(pass);
        this.level.set(UserLevel.NORMAL);
    }
}
