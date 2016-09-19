package model;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Invalid Username or Password");
    }
}
