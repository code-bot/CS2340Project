package fxapp;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
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
    private Stage mainStage;

    private BorderPane rootLayout;

    private AnchorPane loginLayout;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        initMainLayout(mainStage);
        showLoginScreen(mainStage);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Pane getRootLayout() { return rootLayout; }

    public void initMainLayout(Stage mainStage) {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainFXApplication.class.getResource("../view/RootView.fxml"));
            rootLayout = loader.load();

            Scene mainScene = new Scene(rootLayout);
            rootLayout.setCenter(loginLayout);

            //TODO: Give controller access to the mainScreen

            //Set title of the application
            mainStage.setTitle("Login or Register");

            //Show the login scene with the correct layout
            mainStage.setScene(mainScene);
            mainStage.show();
        } catch (IOException e) {}
    }

    public void logoutUser(User user) {
        //TODO: Store user information before logging out
        Model.getInstance().clearCurrentUser();
//        initLoginScene(mainScreen);
    }

    public void showLoginScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/LoginView.fxml"));
            loginLayout = loader.load();

            rootLayout.setCenter(loginLayout);

        } catch (IOException e) {}

    }

    public static void main(String[] args) {
        launch(args);
    }
}
