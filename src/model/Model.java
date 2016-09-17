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

    /**
     * Create a new model with
     * Add default stored data
     */
    Model() {
        /** Hard coded user for testing purposes. Will change after registration
         is created */
        currUser.set(new User("user", "pass"));
    }

    /** Getter and setter for the currUser */
    public User getCurrentUser() { return currUser.get(); }
    public void setCurrentUser(User user) { currUser.set(user); }
}
