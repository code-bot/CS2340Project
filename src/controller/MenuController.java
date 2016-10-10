package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * Created by Matt Sternberg on 9/21/16.
 */
public class MenuController {

    private MainFXApplication mainApplication;

    /**
     * Set the main application reference
     * @param main  The main application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * Sign out the current user
     */
    @FXML
    private void signOut() {
        System.out.println("Signing out...");
        mainApplication.logoutUser();
    }

    /**
     * Go to edit profile view
     */
    @FXML
    private void goToEditProfileView() {
        System.out.println("Loading profile...");
        mainApplication.goToEditProfile();
    }
}
