package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

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

    private final static Map<String, User> userMap = new HashMap<String, User>();;
    private final static Set<Report> reports = new HashSet<>();

    /**
     * Create a new model
     * Add default stored data
     */
    private Model() {
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

    /**
     * Add user to the hashmap
     * @param user  the user to add
     * @return  successfully added or not
     */
    public boolean addUser(User user) {
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

    /**
     * Update a user that exists in the hashmap
     * @param previous  the user in the hashmap to update
     * @param user      the updated user
     * @param sameEmail whether the email changed or not
     * @return  successfully updated or not
     */
    public boolean updateUser(User previous, User user, boolean sameEmail) {
        if (!userMap.containsKey(previous.getEmail())) {
            System.out.println("Email does not exist");
            return false;
        } else if (!sameEmail && userMap.containsKey(user.getEmail())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Email already exists in system");
            alert.setContentText("Please go to login page to login or use forgot password feature");
            alert.showAndWait();
            return false;
        } else if (sameEmail) {
            userMap.put(previous.getEmail(), user);
            currUser.set(user);
            return true;
        } else {
            userMap.remove(previous.getEmail(), previous);
            userMap.put(user.getEmail(), user);
            currUser.set(user);
            return true;
        }
    }

    /**
     * Delete user from hashmap
     * @param user  the user to delete
     * @return  successfully deleted or not
     */
    public boolean deleteUser(User user) {
        if (userMap.containsKey(user.getEmail())) {
            userMap.remove(user.getEmail());
            return true;
        }
        return false;
    }

    /**
     * Validate if email and password match
     * @param email     the email to check
     * @param password  the password to check
     * @return  if the email and password match or not
     */
    public boolean validateUser(String email, String password) {

        if (userMap.containsKey(email)) {
            if (userMap.get(email).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the user corresponding to the email
     * @param email The email of the user
     * @return  the user associated with the email
     */
    public User getUser(String email) {
        return userMap.get(email);
    }

    /**
     * Adds a report to be stored
     * @param report    the report to be stored
     * @return  if report was successfully added to hashset
     */
    public boolean addReport(Report report) {
        return reports.add(report);
    }

    /**
     * Removes a report
     * @param report    the report to be removed
     * @return  if report was successfully removed from hashset
     */
    public boolean removeReport(Report report) {
        return reports.remove(report);
    }

    public Set getReports() { return reports; }
}
