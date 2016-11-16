package fxapp;

import controller.BackMenuController;
import controller.CreateHistoricalController;
import controller.CreateQualityController;
import controller.CreateReportController;
import controller.EditProfileController;
import controller.GraphViewController;
import controller.LoginController;
import controller.MenuController;
import controller.RegisterController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DatabaseModel;
import model.WaterQualityReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
/**
 * Main application class. Handles switching scenes throughout application
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class MainFXApplication extends Application {

    /** Main container for the application window */

    private BorderPane rootLayout;

    private AnchorPane loginLayout;

    private BorderPane menu;

    private DatabaseModel databaseModel;

    private static final org.apache.log4j.Logger logger =
            org.apache.log4j.Logger.getLogger(LoginController.class);

    private final String log4jConfigFile = System.getProperty("user.dir")
            + File.separator + "log4j.properties";

    @Override
    public void start(Stage primaryStage) {
        PropertyConfigurator.configure(log4jConfigFile);
        primaryStage.setResizable(false);
        initRootLayout(primaryStage);
        initLoginScreen();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        databaseModel = DatabaseModel.getInstance();
    }

    /**
     * Initializes the root border pane
     * @param mainStage The stage to add the layout to
     */
    private void initRootLayout(Stage mainStage) {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/RootView.fxml"));
            rootLayout = loader.load();

            Scene mainScene = new Scene(rootLayout);
            rootLayout.setCenter(loginLayout);

            //Set title of the application
            mainStage.setTitle("Login or Register");

            //Show the login scene with the correct layout
            mainStage.setScene(mainScene);
            mainStage.show();
        } catch (IOException e) {
            logger.error("Unable to initialize root layout. Exception: "
                    + e.toString());
        }
    }

    /**
     * Log out the user and remove nav bar
     */
    public void logoutUser() {
        databaseModel.clearCurrentUser();
        initLoginScreen();
        rootLayout.setTop(null);
        databaseModel.logout();
    }

    /**
     * goes to edit profile page
     */
    public void goToEditProfile() {
        initBackMenu();
        initEditRegisterScreen();
    }

    /**
     * goes to home page
     */
    public void goToHomePage() {
        initMenu();
        initMapViewScreen();
    }

    /**
     * Initialize login screen on the main stage
     */
    public void initLoginScreen() {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/LoginView.fxml"));
            loginLayout = loader.load();

            rootLayout.setCenter(loginLayout);
            rootLayout.setTop(null);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.error("Unable to initialize login screen. Exception: "
                    + e.toString());
        }
    }

    /**
     * Initialize navigation bar
     */
    private void initMenu() {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/MainNavigationBar.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            MenuController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.error("Unable to initialize menu screen. Exception: "
                    + e.toString());
        }

    }

    /**
     * Initialize navigation bar
     */
    public void initBackMenu() {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/BackMenuView.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            BackMenuController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.error("Unable to initialize back screen. Exception: "
                    + e.toString());

        }

    }

    /**
     * Initialize the registration screen
     */
    public void initRegisterScreen() {
        try {
            //Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/RegisterView.fxml"));
            AnchorPane profileLayout = loader.load();

            rootLayout.setCenter(profileLayout);

            RegisterController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.error("Unable to initialize register screen. Exception: "
                    + e.toString());
        }

    }

    /**
     * Initialize the edit profile screen
     */
    private void initEditRegisterScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/EditProfileView.fxml"));
            AnchorPane editProfileLayout = loader.load();

            rootLayout.setCenter(editProfileLayout);

            EditProfileController controller = loader.getController();
            controller.setDefaultInfo(databaseModel.getCurrentUser());
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.error("Unable to initialize edit register screen. " +
                    "Exception: " + e.toString());

        }
    }

    /**
     * Initializes the create report screen
     */
    public void initCreateReportScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/CreateReportView.fxml"));
            AnchorPane createReportsLayout = loader.load();

            rootLayout.setCenter(createReportsLayout);

            CreateReportController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.error("Unable to initialize create report screen. " +
                    "Exception: " + e.toString());
        }
    }

    /**
     * Initializes the create quality screen
     */
    public void initCreateQualityScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/CreateQualityView.fxml"));
            AnchorPane createQualityLayout = loader.load();

            rootLayout.setCenter(createQualityLayout);

            CreateQualityController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            logger.error("Unable to initialize quality report screen. " +
                    "Exception: " + e.toString());
        }
    }

    /**
     * Initializes the view reports screen
     */
    public void initViewReportsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/ViewReportsView.fxml"));
            AnchorPane viewReportsLayout = loader.load();

            rootLayout.setCenter(viewReportsLayout);

        } catch (IOException e) {
            logger.error("Unable to initialize view reports screen. Exception: "
                    + e.toString());

        }
    }

    /**
     * Initializes the quality report screen
     */
    public void initQualityReportScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/QualityView.fxml"));
            AnchorPane qualityReportsLayout = loader.load();

            rootLayout.setCenter(qualityReportsLayout);

        } catch (IOException e) {
            logger.error("Unable to initialize quality report screen. " +
                    "Exception: " + e.toString());        }
    }

    /**
     * Initializes the map view screen
     */
    private void initMapViewScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/MapView.fxml"));
            AnchorPane mapLayout = loader.load();

            rootLayout.setCenter(mapLayout);

        } catch (IOException e) {
            logger.error("Unable to initialize map view screen. " +
                    "Exception: " + e.toString());        }
    }

    /**
     * Initializes the graph view screen
     * @param reports list of quality reports passed in
     * @param type type of data
     * @param year year of report
     */
    public void initGraphViewScreen(ArrayList<WaterQualityReport> reports,
                                    String type, String year) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/GraphView.fxml"));
            AnchorPane viewGraphLayout = loader.load();

            rootLayout.setCenter(viewGraphLayout);

            GraphViewController controller = loader.getController();
            controller.setReports(reports);
            controller.setType(type);
            controller.loadGraph(year);
        } catch (IOException e) {
            logger.error("Unable to initialize graph view screen. " +
                    "Exception: " + e.toString());        }
    }

    /**
     * Initializes the create graph screen
     */
    public void initCreateGraphScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource(
                    "../view/CreateGraphView.fxml"));
            AnchorPane createGraphLayout = loader.load();

            rootLayout.setCenter(createGraphLayout);

            CreateHistoricalController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.error("Unable to initialize create graph screen. " +
                    "Exception: " + e.toString());        }
    }

    /**
     * main method
     * @param args main method
     */
    public static void main(String[] args) {
        launch(args);
    }


}
