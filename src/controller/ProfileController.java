package controller;

/**
 * Created by Aman on 9/28/16.
 */
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Model;
import model.States;

import java.util.ArrayList;

public class ProfileController {


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

    private ArrayList<String> states = new ArrayList();

    @FXML
    private void initialize() {

       stateChoiceBox.getItems().addAll(States.toList());


    }

    private void createProfile() {
        //Initialize fields
        username = usernameField.getText();
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();
        address1 = addressLine1Field.getText();
        address2 = addressLine2Field.getText();
        zipCode = zipcodeField.getText();

    }

    public void registerUser() {
        //print error message for passwords not matching up
        if (!(password.equals(passwordField))) {

        }
        //print error message for emails not matching up
        if (!(email.equals(emailConfirm))) {

        }
    }

    @FXML
    private void register() {

    }
}
