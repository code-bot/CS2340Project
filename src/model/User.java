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

    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty zipcode = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final StringProperty state = new SimpleStringProperty();

    /** Authority level of user */
    private final ObjectProperty<UserLevel> level = new SimpleObjectProperty<>();

    /** Can only get the username, setting username only happens during user
     * creation */
    public String getEmail() {
        return email.get();
    }

    /** Can only get the password, setting password only happens during user
     * creation */
    public String getPassword() {
        return password.get();
    }

    /** Can only get the user level, setting level only happens during user
     * creation */
    public UserLevel getUserLevel() { return level.get(); }

    public String getAddress() {
        return address.get();
    }

    public String getZipcode() {
        return zipcode.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getState() {
        return state.get();
    }

    /**
     * Main constructor
     * @param uname     Username
     * @param pass      Password
     * @param level     Authority level
     */
    public User(String uname, String pass, UserLevel level, String address,
                String city, String zipcode, String state) {
        email.set(uname);
        password.set(pass);
        this.level.set(level);
        this.city.set(city);
        this.address.set(address);
        this.zipcode.set(zipcode);
        this.state.set(state);
    }

    /**
     * Additonal constructor to create user with default NORMAL capabilities
     * @param uname     Username
     * @param pass      Password
     */
    public User(String uname, String pass, String address,
                String city, String zipcode, String state) {
        email.set(uname);
        password.set(pass);
        this.level.set(UserLevel.NORMAL);
        this.city.set(city);
        this.address.set(address);
        this.zipcode.set(zipcode);
        this.state.set(state);
    }
}
