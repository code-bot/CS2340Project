package fxapp;

import com.sun.javaws.Main;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import controller.LoginController;
import controller.MenuController;
import controller.ProfileController;
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

    private AnchorPane homeLayout;

    private AnchorPane profileLayout;

    private BorderPane menu;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        initRootLayout(mainStage);
        initLoginScreen(mainStage);
        Model.addUser(new User("user", "pass", "123 Techwood Drive", "Atlanta", "30332", "Georgia"));
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Pane getRootLayout() { return rootLayout; }

    public void initRootLayout(Stage mainStage) {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainFXApplication.class.getResource("../view/RootView.fxml"));
            rootLayout = loader.load();

            Scene mainScene = new Scene(rootLayout);
            rootLayout.setCenter(loginLayout);

            //Set title of the application
            mainStage.setTitle("Login or Register");

            //Show the login scene with the correct layout
            mainStage.setScene(mainScene);
            mainStage.show();
        } catch (IOException e) {}
    }

    public void logoutUser() {
        //TODO: Store user information before logging out
        Model.getInstance().clearCurrentUser();
        initLoginScreen(mainStage);
        rootLayout.setTop(null);
    }

    public void goToProfile() {
        initRegisterScreen(mainStage);
    }

    public void initLoginScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/LoginView.fxml"));
            loginLayout = loader.load();

            rootLayout.setCenter(loginLayout);
            rootLayout.setTop(null);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {}
    }

    public void initMenu(Stage mainStage) {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/SignOutPanel.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            MenuController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {

        }

    }

    public void initHomeScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainView.fxml"));
            homeLayout = loader.load();

            rootLayout.setCenter(homeLayout);

        } catch (IOException e) {}
    }

    public void initRegisterScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/ProfileView.fxml"));
            profileLayout = loader.load();

            rootLayout.setCenter(profileLayout);

            ProfileController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
