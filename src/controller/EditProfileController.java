package controller;

import com.firebase.client.utilities.Pair;
import fxapp.MainFXApplication;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import model.*;

import java.util.Optional;

/**
 * Created by Rahul on 10/2/16.
 */
public class EditProfileController {
    private MainFXApplication mainApplication;

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
            mainApplication.goToHomePage();
        }
    }

    /**
     * Update the user and edit the changes as necessary
     */
    @FXML
    private void updateUser() {
        String passwordConfirm;
        String email;
        String password;
        String emailConfirm;
        String address;
        String city;
        String zipcode;
        States state;
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
                mainApplication.goToHomePage();
            }
        }
    }

    /**
     * Allows the user to change their password
     */
    @FXML
    public void changePassword() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Password Dialog");
        dialog.setHeaderText("Change your password");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY);

        // Create the password and confirm labels and fields.
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField pword = new TextField();
        pword.setPromptText("Password");
        PasswordField confirm = new PasswordField();
        confirm.setPromptText("Confirm Password");

        grid.add(new Label("New Password:"), 0, 0);
        grid.add(pword, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirm, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (pword.getText().equals(confirm.getText())) {
                String password = pword.getText();
                DatabaseModel.getInstance().getCurrentUser().resetPassword(password);
            } else {
                incorrectPasswordSubmit();
            }
        }
    }

    public void incorrectPasswordSubmit() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("New Password Error");
        alert.setHeaderText("The two password fields do not match");
        alert.setContentText("Please try changing password again");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            changePassword();
        }
    }
}
