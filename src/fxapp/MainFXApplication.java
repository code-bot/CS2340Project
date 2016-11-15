package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.util.ArrayList;

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


    @Override
    public void start(Stage primaryStage) {
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
        initLoginScreen();
        rootLayout.setTop(null);
        databaseModel.logout();
    }

    public void goToEditProfile() {
        initBackMenu();
        initEditRegisterScreen();
    }

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
     */
    private void initMenu() {
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
     */
    public void initBackMenu() {
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
     * Initialize the registration screen
     */
    public void initRegisterScreen() {
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
     */
    private void initEditRegisterScreen() {
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

    public void initCreateReportScreen() {
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

    public void initCreateQualityScreen() {
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

    public void initViewReportsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/ViewReportsView.fxml"));
            AnchorPane viewReportsLayout = loader.load();

            rootLayout.setCenter(viewReportsLayout);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void initQualityReportScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/QualityView.fxml"));
            AnchorPane qualityReportsLayout = loader.load();

            rootLayout.setCenter(qualityReportsLayout);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void initMapViewScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MapView.fxml"));
            AnchorPane mapLayout = loader.load();

            rootLayout.setCenter(mapLayout);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void initGraphViewScreen(ArrayList<WaterQualityReport> reports, String type, String year) {
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

    public void initCreateGraphScreen() {
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

    public static void main(String[] args) {
        launch(args);
    }


}
