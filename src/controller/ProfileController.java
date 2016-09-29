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

import java.util.ArrayList;

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
    private Button register;
    private ArrayList<String> states = new ArrayList();

    @FXML
    private void initialize() {

       stateChoiceBox.getItems().addAll(States.toList());

    }

    private void createProfile() {
        //Initialize fields
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();
        address1 = addressLine1Field.getText();
        address2 = addressLine2Field.getText();
        zipCode = zipcodeField.getText();

    }
    @FXML
    private void registerUser() {
        System.out.println("YES");
        createProfile();
        //print error message for emails not matching up
        if (!(email.equals(emailConfirm))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Email Does Not Match");
            alert.setContentText("The emails provided are not the same. Please ensure you have entered the same email address.");

            alert.showAndWait();
        }
        //print error message for passwords not matching up
        if (!(password.equals(passwordField))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error!");
            alert.setHeaderText("Password Does Not Match");
            alert.setContentText("The passwords provided are not the same. Please ensure you have entered the same email address.");

            alert.showAndWait();
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

        mainApplication.initLoginScreen(mainApplication.getMainStage());

    }

}
