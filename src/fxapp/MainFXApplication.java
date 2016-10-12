package fxapp;
import com.google.firebase.*;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sun.javaws.Main;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import controller.EditProfileController;
import controller.LoginController;
import controller.MenuController;
import controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
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

    private AnchorPane editProfileLayout;

    private BorderPane menu;

    private DatabaseModel databaseModel;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        initRootLayout(mainStage);
        initLoginScreen(mainStage);
        databaseModel = DatabaseModel.getInstance();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Pane getRootLayout() { return rootLayout; }

    /**
     * Initializes the root border pane
     * @param mainStage The stage to add the layout to
     */
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

    /**
     * Log out the user and remove nav bar
     */
    public void logoutUser() {
        //TODO: Store user information before logging out
        Model.getInstance().clearCurrentUser();
        initLoginScreen(mainStage);
        rootLayout.setTop(null);
    }

    public void goToEditProfile() {
        initEditRegisterScreen(mainStage);
    }

    /**
     * Initialize login screen on the main stage
     * @param mainStage the stage to add the layouts to
     */
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

    /**
     * Initialize navigation bar
     * @param mainStage the stage to add the layout to
     */
    public void initMenu(Stage mainStage) {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainNavigationBar.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            MenuController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {

        }

    }

    /**
     * Initialize the home screen
     * @param mainStage the stage to add the layout to
     */
    public void initHomeScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainView.fxml"));
            homeLayout = loader.load();

            rootLayout.setCenter(homeLayout);

        } catch (IOException e) {}
    }

    /**
     * Initialize the registration screen
     * @param mainStage the stage to add the layout to
     */
    public void initRegisterScreen(Stage mainStage) {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/RegisterView.fxml"));
            profileLayout = loader.load();

            rootLayout.setCenter(profileLayout);

            RegisterController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Initialize the edit profile screen
     * @param mainStage the stage to add the layout to
     */
    public void initEditRegisterScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/EditProfileView.fxml"));
            editProfileLayout = loader.load();

            rootLayout.setCenter(editProfileLayout);

            EditProfileController controller = loader.getController();
            controller.setDefaultInfo(Model.getInstance().getCurrentUser());
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
