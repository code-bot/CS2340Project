package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import fxapp.MainFXApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Model;
import model.States;
import model.User;
import model.UserLevel;

import java.util.Optional;

/**
 * Created by Rahul on 10/2/16.
 */
public class EditProfileController {
    private MainFXApplication mainApplication;

    private String passwordConfirm;
    private String email;
    private String password;
    private String emailConfirm;
    private String address;
    private String city;
    private String zipcode;
    private States state;

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
    private Label userTypeLbl;

    /**
     * Sets default placeholders for required fields
     * @param user  User holding the default info
     */
    public void setDefaultInfo(User user) {
        emailField.setPromptText(user.getEmail());
        addressLine1Field.setPromptText(user.getAddress());
        cityField.setPromptText(user.getCity());
        zipcodeField.setPromptText(user.getZipcode());
        stateComboBox.getItems().addAll(States.toList());
        stateComboBox.setVisibleRowCount(6);
        stateComboBox.getSelectionModel().select(user.getState().name());
        userTypeLbl.textProperty().set("User Type: " + user.getUserLevel().name());
    }

    /**
     * Set the main application
     * @param main  The main application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * Cancel the edit and return to home screen
     */
    @FXML
    private void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("If you cancel, the information will not be stored and you will be returned to the menu");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mainApplication.initMenu(mainApplication.getMainStage());
            mainApplication.initHomeScreen(mainApplication.getMainStage());
        } else {

        }
    }

    /**
     * Update the user and edit the changes as necessary
     */
    @FXML
    private void updateUser() {
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();
        address = addressLine1Field.getText();
        city = cityField.getText();
        zipcode = zipcodeField.getText();
        state = States.valueOf(stateComboBox.getSelectionModel().getSelectedItem());

        if (!(email.equals(emailConfirm))) {
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
            User oldUser = Model.getInstance().getCurrentUser();
            boolean sameEmail = false;
            if (email.equals("")) {
                sameEmail = true;
                email = oldUser.getEmail();
            }
            if (password.equals("")) {
                password = oldUser.getPassword();
            }
            if (address.equals("")) {
                address = oldUser.getAddress();
            }
            if (city.equals("")) {
                city = oldUser.getCity();
            }
            if (zipcode.equals("")) {
                zipcode = oldUser.getZipcode();
            }

            User newUser = new User(email, password, oldUser.getUserLevel(),
                    address, city, zipcode, state);
            boolean updatedUser = Model.getInstance().updateUser(oldUser, newUser, sameEmail);
            if (updatedUser) {
                mainApplication.initMenu(mainApplication.getMainStage());
                mainApplication.initHomeScreen(mainApplication.getMainStage());
            }
        }
    }
}
