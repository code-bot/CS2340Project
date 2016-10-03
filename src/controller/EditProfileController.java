package controller;

import fxapp.MainFXApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Model;
import model.User;

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
    private Model model;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField confirmEmailField;

    public EditProfileController() {
        model = Model.getInstance();
    }

    public TextField getEmailTextField() { return emailField; }

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

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

    @FXML
    private void setUser() {
        password = passwordField.getText();
        passwordConfirm = confirmPasswordField.getText();
        email = emailField.getText();
        emailConfirm = confirmEmailField.getText();

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
            User oldUser = model.getCurrentUser();
            if (!email.isEmpty() && !emailConfirm.isEmpty()) {
                model.getCurrentUser().setEmail(emailConfirm);
            }
            if (!password.isEmpty() && !passwordConfirm.isEmpty()) {
                model.getCurrentUser().setPassword(passwordConfirm);
            }
            mainApplication.initMenu(mainApplication.getMainStage());
            mainApplication.initHomeScreen(mainApplication.getMainStage());
            System.out.println("Email: " + model.getCurrentUser().getEmail());
            System.out.println("Password: " + model.getCurrentUser().getPassword());

            User newUser = new User(model.getCurrentUser().getEmail(), model.getCurrentUser().getPassword(), model.getCurrentUser().getUserLevel());
            boolean addedUser = model.addUser(newUser);
            if (addedUser) {
                model.deleteUser(oldUser);
                model.setCurrentUser(newUser);
                mainApplication.initMenu(mainApplication.getMainStage());
                mainApplication.initHomeScreen(mainApplication.getMainStage());
            }
        }
    }
}
