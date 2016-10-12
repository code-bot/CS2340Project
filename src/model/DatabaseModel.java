package model;

import com.firebase.client.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Matt Sternberg on 10/7/2016.
 */
public class DatabaseModel {
    /** Set Model up as a singleton design pattern */
    private static final DatabaseModel instance = new DatabaseModel();
    public static DatabaseModel getInstance() { return instance; }

    private final String DATABASE_URL = "https://h2woah.firebaseio.com";
    private Firebase rootRef;


    DatabaseModel() {
        initFirebase();
    }

    /**
     * Initializes an instance of FirebaseDatabase to connect to the Firebase database
     */
    public void initFirebase() {
        rootRef = new Firebase(DATABASE_URL);
    }


    /** Currently logged in user */
    private static final ObjectProperty<User> currUser = new SimpleObjectProperty<>();


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
     * Create user
     */
    public boolean createUser(User newUser) {
        String emailStripped = newUser.getEmail().replaceAll("[-+.^:,@]", "");
        Firebase usersRef = rootRef.child("users").child(emailStripped);
        usersRef.setValue(newUser);
        return true;
    }

    public Firebase getRootRef() {
        return rootRef;
    }

}
