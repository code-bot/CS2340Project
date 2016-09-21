package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * Created by Matt Sternberg on 9/21/16.
 */
public class MenuController {

    private MainFXApplication mainApplication;

    public void setMain(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void signOut() {
        System.out.println("Signing out...");
        mainApplication.logoutUser();
    }

}
