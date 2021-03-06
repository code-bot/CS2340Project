package controller;

import com.firebase.client.*;
import com.firebase.client.Logger;
import fxapp.MainFXApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import model.*;
import org.apache.log4j.*;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class LoginController {

    // Reference back to the main application if needed
    private MainFXApplication mainApplication;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoginController.class);

    private String log4jConfigFile = System.getProperty("user.dir")
            + File.separator + "log4j.properties";

    private String _username;

    private String _password;

    private DatabaseModel model;

    @FXML
    private Hyperlink signUp;

    @FXML
    private TextField username;

    @FXML
    private PasswordField passwordField;


    public LoginController() {
        model = DatabaseModel.getInstance();
    }

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }


    /**
     * Gets called when controller is first initialized
     */
    @FXML
    private void initialize() {

        PropertyConfigurator.configure(log4jConfigFile);
        //Initialize temp username and password:
        _username = username.getText();
        _password = passwordField.getText();
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainApplication.initRegisterScreen(mainApplication.getMainStage());
            }
        });
        //You can press enter while in TextField to initiate login
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    /** Sets the current user and goes to main user screen */
    @FXML
    private void login() {
        _username = username.getText();
        _password = passwordField.getText();
        Firebase rootRef = model.getRootRef();
        rootRef.authWithPassword(_username, _password,
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        Firebase users = rootRef.child("users");
                        users.orderByKey().equalTo(authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DataSnapshot uid = dataSnapshot.child(authData.getUid());
                                String uname = (String) uid.child("email").getValue();
                                String pass = (String) uid.child("password").getValue();
                                UserLevel userLevel = UserLevel.stringToUserLevel((String) uid.child("userLevel").getValue());
                                String address = (String) uid.child("address").getValue();
                                String city = (String) uid.child("city").getValue();
                                String zipcode = (String) uid.child("zipcode").getValue();
                                States state = States.stringToState((String) uid.child("state").getValue());
                                System.out.println(model.setCurrentUser(
                                        new User(uname, pass, userLevel, address, city, zipcode, state)));
                                model.setUid(authData.getUid());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainApplication.goToHomePage();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println(firebaseError.getMessage());
                            }
                        });
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
                        System.out.println(error.getMessage());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                loginError(error.getMessage());
                            }
                        });
                    }
                });
    }

    public void loginError(String error) {
        logger.info("Login attempt. Userid: \"" + username.getText() + "\" Bad Login Attempt -" +
                " " + error);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Incorrect Information");
        alert.setContentText("Wrong username or password");
        alert.showAndWait();
    }

    public void resetPassword() {
        TextInputDialog dialog = new TextInputDialog(username.getText());
        dialog.setTitle("Forgot Password Dialog");
        dialog.setHeaderText("Password Reset");
        dialog.setContentText("Please enter your email address:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (isValidEmail(result.get())) {
                DatabaseModel.getInstance().forgotPassword(result.get());
            } else {
                incorrectResetEmail();
            }
        }
    }

    public void incorrectResetEmail() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Reset Error");
        alert.setHeaderText("Incorrect Email Format");
        alert.setContentText("The given email address is in an incorrect format");
        alert.showAndWait();
    }

    public static boolean isValidEmail(String emailAddress) {
        return emailAddress.contains(" ") == false && emailAddress.matches(".+@.+\\.[a-z]+");
    }


}
