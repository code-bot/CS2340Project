package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Model;
import model.User;
import view.*;
import model.UserNotFoundException;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class LoginController {

    private String _username;

    private String _password;

    private Model model;

    @FXML
    private TextField username;

    @FXML
    private PasswordField passwordField;

    public LoginController() {
        model = Model.getInstance();
    }

    /** Ensures username and password textfields are the same as testUser */
    public boolean validateUser(String uname, String pword) {
        //TODO: Create way to validate any user based on username and password

        //Hard-coded case
        return uname.equals(model.testUser.getUsername())
                && pword.equals(model.testUser.getPassword());
    }

    @FXML
    private void initialize() {

        //Initialize temp username and password:
        _username = username.getText();
        _password = passwordField.getText();
    }

    /** Sets the current user and goes to main user screen */
    public void setUser() throws UserNotFoundException {
        _username = username.getText();
        _password = passwordField.getText();
        if (validateUser(_username, _password)) {
            model.setCurrentUser(model.testUser);
        } else {
            throw new UserNotFoundException();
        }
    }


}
