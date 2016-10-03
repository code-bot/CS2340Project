package controller;

/**
 * Created by Aman on 9/28/16.
 */
import fxapp.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Model;
import model.States;
import model.User;
import model.UserLevel;

import java.util.ArrayList;
import java.util.Optional;

public class ProfileController {


    private MainFXApplication mainApplication;


    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String emailConfirm;
    private String address1;
    private String address2;
    private String zipCode;
//    private ArrayList<User> users = new ArrayList<User>();
    private UserLevel userType;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField confirmEmailField;

    @FXML
    private TextField addressLine1Field;

    @FXML
    private TextField addressLine2Field;

    @FXML
    private TextField zipcodeField;

    @FXML
    private ChoiceBox<String> stateChoiceBox;

    @FXML
    private ChoiceBox<UserLevel> userTypeBox;
    @FXML
    private Button register;


    @FXML
    private void initialize() {

        stateChoiceBox.getItems().addAll(States.toList());
        stateChoiceBox.getSelectionModel().selectFirst();
        userTypeBox.getItems().addAll(UserLevel.toList());
        userTypeBox.getSelectionModel().selectFirst();
    }

    private void loadData() {
        //Initialize fields
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();
        address1 = addressLine1Field.getText();
        address2 = addressLine2Field.getText();
        zipCode = zipcodeField.getText();
        userType = userTypeBox.getValue();

    }
    @FXML
    private void registerUser() {
        loadData();
        //print error message for empty fields or emails/passwords not matching up
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Please complete all fields");
            alert.setContentText("One or more of the fields are empty.");

            alert.showAndWait();
        } else if (!(email.equals(emailConfirm))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Email Does Not Match");
            alert.setContentText("The emails provided are not the same. Please ensure you have entered the same email address.");

            alert.showAndWait();
        } else if (!(password.equals(passwordConfirm))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Password Does Not Match");
            alert.setContentText("The passwords provided are not the same. Please ensure you have entered the same password.");

            alert.showAndWait();
        } else {

            User newUser = new User(email, password, userType);
            boolean addedUser = model.Model.addUser(newUser);
            if (addedUser) {
                model.Model.setCurrentUser(newUser);
                mainApplication.initMenu(mainApplication.getMainStage());
                mainApplication.initHomeScreen(mainApplication.getMainStage());
            }
        }
    }


    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("If you cancel, the information will not be stored and you will be returned to the login page");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mainApplication.initLoginScreen(mainApplication.getMainStage());
        } else {
        }
    }

}
