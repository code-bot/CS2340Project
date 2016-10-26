package controller;

import com.firebase.client.*;
import fxapp.MainFXApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import model.*;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Matt Sternberg on 9/18/16.
 */
public class LoginController {

    // Reference back to the main application if needed
    private MainFXApplication mainApplication;

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
                                model.setCurrentUser(
                                        new User(uname, pass, userLevel, address, city, zipcode, state));
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println(firebaseError.getMessage());
                            }
                        });
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainApplication.goToHomePage();
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
                                loginError();
                            }
                        });
                    }
                });
    }

    public void loginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Incorrect Information");
        alert.setContentText("Wrong username or password");
        alert.showAndWait();

    }



}
