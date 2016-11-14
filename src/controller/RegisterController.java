package controller;

/**
 * Created by Aman on 9/28/16.
 */

import fxapp.MainFXApplication;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DatabaseModel;
import model.States;
import model.User;
import model.UserLevel;

import java.util.Optional;

public class RegisterController {


    private MainFXApplication mainApplication;


    //private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String emailConfirm;
    private String address;
    private String zipcode;
    private UserLevel userType;
    private String state;
    private String city;

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
    private TextField cityField;

    @FXML
    private TextField zipcodeField;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private ComboBox<UserLevel> userTypeBox;
    
    @FXML
    private Button register;


    /**
     * Gets called when controller is created. Sets ComboBox properties
     */
    @FXML
    private void initialize() {

        stateComboBox.getItems().addAll(States.toList());
        stateComboBox.getSelectionModel().selectFirst();
        stateComboBox.setVisibleRowCount(6);
        userTypeBox.getItems().addAll(UserLevel.toList());
        userTypeBox.getSelectionModel().selectFirst();
        userTypeBox.setVisibleRowCount(4);
    }

    /**
     * Load data from the correct inputs
     */
    private void loadData() {
        //Initialize fields
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();
        address = addressLine1Field.getText();
        city = cityField.getText();
        zipcode = zipcodeField.getText();
        state = stateComboBox.getValue();
        userType = userTypeBox.getValue();

    }

    /**
     * Register the user, handle exceptions, and store the user in the model
     */
    @FXML
    private void registerUser() {
        loadData();
        //print error message for empty fields or emails/passwords not matching up
        if (email.isEmpty() || password.isEmpty() || address.isEmpty() || zipcode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Please complete all fields");
            alert.setContentText("One or more of the fields are empty.");

            alert.showAndWait();
        } else if (!(email.equals(emailConfirm))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error!");
                alert.setHeaderText("Email Does Not Match");
                alert.setContentText("The emails provided are not the same. " +
                        "Please ensure you have entered the same email address.");

                alert.showAndWait();
            } else if (!(password.equals(passwordConfirm))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error!");
                alert.setHeaderText("Password Does Not Match");
                alert.setContentText("The passwords provided are not the same. " +
                        "Please ensure you have entered the same password.");

                alert.showAndWait();
        } else {
            User newUser = new User(email, password, userType, address, city, zipcode, States.valueOf(state));
            boolean addedUser = DatabaseModel.getInstance().createUser(newUser);
            if (addedUser) {
                DatabaseModel.getInstance().setCurrentUser(newUser);
                mainApplication.goToHomePage();
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

    /**
     * Cancels the registration and the user is not saved in the model
     */
    @FXML
    private void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("If you cancel, the information will not be stored " +
                "and you will be returned to the login page");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            mainApplication.initLoginScreen(mainApplication.getMainStage());
        }
    }

}
