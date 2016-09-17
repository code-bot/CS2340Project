package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Serves as Facade into the application model
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class Model {

    /** Set Model up as a singleton design pattern */
    private static final Model instance = new Model();
    public static Model getInstance() { return instance; }

    /** Currently logged in user */
    private final ObjectProperty<User> currUser = new SimpleObjectProperty<>();

    /** Hard coded user for testing purposes. Will change after registration
     is created */
    public User testUser;

    /**
     * Create a new model
     * Add default stored data
     */
    Model() {
        //TODO: Add additional default data
        testUser = new User("user", "pass");
    }

    /** Getter and setter for the currUser */
    public User getCurrentUser() { return currUser.get(); }
    public boolean setCurrentUser(User user) {
        //Can only set the current user if there is no current user (Safety measure)
        if (currUser.get() == null) {
            currUser.set(user);
            return true;
        }
        return false;
    }
    /** Clears the current user for logging out */
    public void clearCurrentUser() {
        currUser.set(null);
    }
}
