package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;

/**
 * Serves as Facade into the application model
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class Model {

    /** Set Model up as a singleton design pattern */
    private static final Model instance = new Model();
    public static Model getInstance() { return instance; }

    /** Currently logged in user */
    private static final ObjectProperty<User> currUser = new SimpleObjectProperty<>();

    /** Hard coded user for testing purposes. Will change after registration
     is created */
    public User testUser;

    private static Map<String, User>  userMap = new HashMap<String, User>();

    /**
     * Create a new model
     * Add default stored data
     */
    Model() {
        //TODO: Add additional default data


    }

    /** Getter and setter for the currUser */
    public User getCurrentUser() { return currUser.get(); }


    public static boolean setCurrentUser(User user) {
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

    public static boolean addUser(User user) {
        if (userMap.containsKey(user.getEmail())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Email already exists in system");
            alert.setContentText("Please go to login page to login or use forgot password feature");
            alert.showAndWait();
            return false;
        } else {
            userMap.put(user.getEmail(), user);
            return true;
        }
    }

    public boolean validateUser(String email, String password) {

        if (userMap.containsKey(email)) {
            if(userMap.get(email).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String email) {
        return userMap.get(email);
    }

}
