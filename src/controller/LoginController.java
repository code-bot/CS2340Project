package controller;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import fxapp.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import model.*;

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
        _username = username.getText().replaceAll("[-+.^:,@]", "");
        _password = passwordField.getText();
        Firebase usersRef = DatabaseModel.getInstance().getRootRef().child("users");
        usersRef.orderByChild("password").equalTo(_password).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.hasChild(_username));
                System.out.println(dataSnapshot.getValue());
                if (dataSnapshot.getValue() == null || !dataSnapshot.hasChild(_username)) {
                    loginError();
                } else {
                    DataSnapshot userChild = dataSnapshot.child(_username);
                    UserLevel userLevel = UserLevel.stringToUserLevel((String) userChild.child("userLevel").getValue());
                    String address = (String) userChild.child("address").getValue();
                    String city = (String) userChild.child("city").getValue();
                    String zipcode = (String) userChild.child("zipcode").getValue();
                    States state = States.stringToState((String) userChild.child("state").getValue());
                    DatabaseModel.getInstance().setCurrentUser(new User(_username, _password, userLevel,
                            address, city, zipcode, state));
                    mainApplication.initMenu(mainApplication.getMainStage());
                    mainApplication.initHomeScreen(mainApplication.getMainStage());

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
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
