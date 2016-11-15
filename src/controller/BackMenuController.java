package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * Created by Karan Lakhani on 10/13/16.
 */
@SuppressWarnings("ALL")
public class BackMenuController {

    private MainFXApplication mainApplication;

    /**
     * Set the main application reference.
     * @param main  The main application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * Sign out the current user.
     */
    @FXML
    private void signOut() {
        mainApplication.logoutUser();
    }

    /**
     * Go to edit profile view.
     */
    @FXML
    private void goToHomePageView() {
        mainApplication.goToHomePage();
    }
}
