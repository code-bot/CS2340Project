package controller;

import javafx.fxml.FXML;
import model.Model;
import model.User;
import model.UserNotFoundException;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class LoginController {

    private String _username;

    private String _password;

    private Model model;

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
        //TODO: Set the Current User only if validateUser passes

        //Initialize temp username and password:
        //_username = textboxUsername.getText
        //_password = textboxPassword.getText
    }

    /** Sets the current user and goes to main user screen */
    public void setUser() throws UserNotFoundException {
        //_username = textboxUsername.getText
        //_password = textboxPassword.getText
        if (validateUser(_username, _password)) {
            model.setCurrentUser(model.testUser);
        } else {
            throw new UserNotFoundException();
        }
    }


}
