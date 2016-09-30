package controller;

import fxapp.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Model;
import model.User;
import model.UserLevel;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class LoginController {

    // Reference back to the main application if needed
    private MainFXApplication mainApplication;

    private String _username;

    private String _password;

    private Model model;

    @FXML
    private Hyperlink signUp;

    @FXML
    private TextField username;

    @FXML
    private PasswordField passwordField;

    public LoginController() {
        model = Model.getInstance();
    }

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }


    /** Ensures username and password textfields are the same as testUser */
//    private boolean validateUser() {
//        //TODO: Create way to validate any user based on username and password
//
//        //Hard-coded case
//        return _username.equals(model.getUser())
//                && _password.equals(model.testUser.getPassword());
//    }

    @FXML
    private void initialize() {

        //Initialize temp username and password:
        _username = username.getText();
        _password = passwordField.getText();
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainApplication.initRegisterScreen(mainApplication.getMainStage());
            }
        });
    }

    /** Sets the current user and goes to main user screen */
    @FXML
    private void login() {
        _username = username.getText();
        _password = passwordField.getText();
        if (model.validateUser(new User(username.getText(), passwordField.getText()))) {
            model.setCurrentUser(model.getUser(username.getText()));
            mainApplication.initMenu(mainApplication.getMainStage());
            mainApplication.initHomeScreen(mainApplication.getMainStage());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Incorrect Information");
            alert.setContentText("Wrong username or password");

            alert.showAndWait();
        }
    }



}
