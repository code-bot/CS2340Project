package fxapp;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;

import java.io.IOException;
import java.util.Map;

/**
 * Main application class. Handles switching scenes throughout application
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class MainFXApplication extends Application {

    /** Main container for the application window */
    private Stage mainScreen;

    private BorderPane mainLayout;

    private AnchorPane loginLayout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        initStage(mainScreen);
        showLoginScreen(mainScreen);
    }

    public Stage getMainScreen() {
        return mainScreen;
    }

    public void initStage(Stage mainScreen) {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/RootView.fxml"));
            mainLayout = loader.load();

            Scene mainScene = new Scene(mainLayout);
            mainLayout.setCenter(loginLayout);

            //TODO: Give controller access to the mainScreen

            //Set title of the application
            mainScreen.setTitle("Login or Register");

            //Show the login scene with the correct layout
            mainScreen.setScene(mainScene);
            mainScreen.show();
        } catch (IOException e) {}
    }

    public void logoutUser(User user) {
        //TODO: Store user information before logging out
        Model.getInstance().clearCurrentUser();
//        initLoginScene(mainScreen);
    }

    public void showLoginScreen(Stage mainScreen) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/LoginView.fxml"));
            loginLayout = loader.load();

            mainLayout.setCenter(loginLayout);

        } catch (IOException e) {}

    }

    public static void main(String[] args) {
        launch(args);
    }
}
