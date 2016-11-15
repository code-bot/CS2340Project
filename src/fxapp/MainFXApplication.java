package fxapp;

import controller.BackMenuController;
import controller.CreateHistoricalController;
import controller.CreateQualityController;
import controller.CreateReportController;
import controller.EditProfileController;
import controller.GraphViewController;
import controller.LoginController;
import controller.MainViewController;
import controller.MapViewController;
import controller.MenuController;
import controller.QualityController;
import controller.RegisterController;
import controller.ViewReportsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DatabaseModel;
import model.WaterQualityReport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main application class. Handles switching scenes throughout application
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class MainFXApplication extends Application {

    /** Main container for the application window */
    private Stage mainStage;

    private BorderPane rootLayout;

    private AnchorPane loginLayout;

    private BorderPane menu;

    private DatabaseModel databaseModel;


    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        initRootLayout(mainStage);
        initLoginScreen(mainStage);
        primaryStage.setOnCloseRequest((event) -> System.exit(0));
        databaseModel = DatabaseModel.getInstance();
    }

    /**
     * gets the mainstage
     * @return Stage The stage
     */
    public Stage getMainStage() {
        return mainStage;
    }


    /**
     * Initializes the root border pane
     * @param mainStage The stage to add the layout to
     */
    private void initRootLayout(Stage mainStage) {

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
        databaseModel.clearCurrentUser();
        initLoginScreen(mainStage);
        rootLayout.setTop(null);
        databaseModel.logout();
    }

    /**
     * goes to edit profile page
     */
    public void goToEditProfile() {
        initBackMenu(mainStage);
        initEditRegisterScreen(mainStage);
    }

    /**
     * goes to home page
     */
    public void goToHomePage() {
        initMenu(mainStage);
        initMapViewScreen(mainStage);
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
    private void initMenu(Stage mainStage) {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainNavigationBar.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            MenuController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {

        }

    }

    /**
     * Initialize navigation bar
     * @param mainStage the stage to add the layout to
     */
    public void initBackMenu(Stage mainStage) {
        try {
            //Load layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/BackMenuView.fxml"));
            menu = loader.load();

            rootLayout.setTop(menu);

            BackMenuController controller = loader.getController();
            controller.setMainApp(this);
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
            AnchorPane homeLayout = loader.load();

            rootLayout.setCenter(homeLayout);

            MainViewController controller = loader.getController();
            controller.setMainApp(this);

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
            AnchorPane profileLayout = loader.load();

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
    private void initEditRegisterScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/EditProfileView.fxml"));
            AnchorPane editProfileLayout = loader.load();

            rootLayout.setCenter(editProfileLayout);

            EditProfileController controller = loader.getController();
            controller.setDefaultInfo(databaseModel.getCurrentUser());
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the create report screen
     * @param mainStage the stage to add the layout to
     */
    public void initCreateReportScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/CreateReportView.fxml"));
            AnchorPane createReportsLayout = loader.load();

            rootLayout.setCenter(createReportsLayout);

            CreateReportController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the create quality screen
     * @param mainStage the stage to add the layout to
     */
    public void initCreateQualityScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/CreateQualityView.fxml"));
            AnchorPane createQualityLayout = loader.load();

            rootLayout.setCenter(createQualityLayout);

            CreateQualityController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the view reports screen
     * @param mainStage the stage to add the layout to
     */
    public void initViewReportsScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/ViewReportsView.fxml"));
            AnchorPane viewReportsLayout = loader.load();

            rootLayout.setCenter(viewReportsLayout);

            ViewReportsController controller = loader.getController();

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    /**
     * Initializes the quality report screen
     * @param mainStage the stage to add the layout to
     */
    public void initQualityReportScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/QualityView.fxml"));
            AnchorPane qualityReportsLayout = loader.load();

            rootLayout.setCenter(qualityReportsLayout);

            QualityController controller = loader.getController();

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the map view screen
     * @param mainStage the stage to add the layout to
     */
    public void initMapViewScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MapView.fxml"));
            AnchorPane mapLayout = loader.load();

            rootLayout.setCenter(mapLayout);

            MapViewController controller = loader.getController();

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the graph view screen
     * @param mainStage the stage to add the layout to
     * @param reports list of quality reports passed in
     * @param type type of data
     * @param year year of report
     */
    public void initGraphViewScreen(Stage mainStage, ArrayList<WaterQualityReport> reports, String type, String year) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/GraphView.fxml"));
            AnchorPane viewGraphLayout = loader.load();

            rootLayout.setCenter(viewGraphLayout);

            GraphViewController controller = loader.getController();
            controller.setReports(reports);
            controller.setType(type);
            controller.loadGraph(year);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Initializes the create graph screen
     * @param mainStage the stage to add the layout to
     */
    public void initCreateGraphScreen(Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/CreateGraphView.fxml"));
            AnchorPane createGraphLayout = loader.load();

            rootLayout.setCenter(createGraphLayout);

            CreateHistoricalController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * main method
     * @param args main method
     */
    public static void main(String[] args) {
        launch(args);
    }


}
