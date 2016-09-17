package fxapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main application class. Handles switching scenes throughout application
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class MainFXApplication extends Application {

    /** Main container for the application window */
    private Stage mainScreen;

    /** The main layout for the login page. Change if needed to suit view */
    private VBox loginLayout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        initLoginScene(mainScreen);
    }

    public Stage getMainScreen() {
        return mainScreen;
    }

    public void initLoginScene(Stage mainScreen) {
        //TODO: Pull layout from fxml file
        loginLayout = new VBox();

        //TODO: Give controller access to the mainScreen

        //Set title of the application
        mainScreen.setTitle("Login or Register");

        //Show the login scene with the correct layout
        Scene login = new Scene(loginLayout);
        mainScreen.setScene(login);
        mainScreen.show();
    }
}
