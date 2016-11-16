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

    /** Can only get the username, setting username only happens during user
     * creation */
    private final StringProperty email = new SimpleStringProperty();

    /** Can only get the password, setting password only happens during user
     * creation */
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty zipcode = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final ObjectProperty<States> state = new SimpleObjectProperty();

    /** Authority level of user
     * Can only get the user level, setting level only happens during user
     * creation */
    private final ObjectProperty<UserLevel> level =
            new SimpleObjectProperty<>();

    /**
     * gets the email
     * @return String containing the email
     */
    public String getEmail() { return email.get(); }

    /**
     * gets the password
     * @return String containing the password
     */
    public String getPassword() {
        return password.get();
    }

    /**
     * Gets the user level
     * @return UserLevel containing the user's level
     */
    public UserLevel getUserLevel() { return level.get(); }

    /**
     * gets the address
     * @return String containing the address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * gets the zip code
     * @return String containing the zip code
     */
    public String getZipcode() {
        return zipcode.get();
    }

    /**
     * gets the city
     * @return String containing the city
     */
    public String getCity() {
        return city.get();
    }

    /**
     * gets the state
     * @return String containing the state
     */
    public States getState() {
        return state.get();
    }

    /**
     * Main constructor
     * @param uname     Username
     * @param pass      Password
     * @param level     Authority level
     * @param address   Address
     * @param city      City
     * @param zipcode   Zip Code
     * @param state     State
     */
    public User(String uname, String pass, UserLevel level, String address,
                String city, String zipcode, States state) {
        email.set(uname);
        password.set(pass);
        this.level.set(level);
        this.city.set(city);
        this.address.set(address);
        this.zipcode.set(zipcode);
        this.state.set(state);
    }

    @Override
    public String toString() {
        States state = this.state.get();
        String stateName = state.name();
        UserLevel level = this.level.get();
        String levelName = level.name();
        return email.get() + " " + password.get() + " " + levelName +
                " " + address.get() + " " + city.get() + " " + zipcode.get() +
                " " + stateName;
    }

    /**
     * Resets the password
     * @param newPassword new password to set
     */
    public void resetPassword(String newPassword) {
        DatabaseModel databaseModel = DatabaseModel.getInstance();
        databaseModel.changePassword(getEmail(), getPassword(), newPassword);
    }
}
