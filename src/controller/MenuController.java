package controller;

import fxapp.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by Matt Sternberg on 9/21/16.
 */
public class MenuController {

    private MainFXApplication mainApplication;

    @FXML
    private MenuBar pageMenu;

    @FXML
    public void initialize() {
        Menu goToPage = new Menu("Go To Page");

        MenuItem profile = new MenuItem("My Profile");
        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToEditProfileView();
            }
        });

        MenuItem createReport = new MenuItem("Create Source Report");
        createReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createReport();
            }
        });

        MenuItem viewReport = new MenuItem("View Source Reports");
        viewReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewReports();
            }
        });

        MenuItem createQualityReport = new MenuItem("Create Quality Reports");
        createQualityReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createQualityReports();
            }
        });

        MenuItem viewQualityReport = new MenuItem("View Quality Reports");
        viewQualityReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewQualityReports();
            }
        });

        goToPage.getItems().addAll(profile, createReport, viewReport, createQualityReport,
                                    viewQualityReport);
        pageMenu.getMenus().addAll(goToPage);

    }

    /**
     * Set the main application reference
     * @param main  The main application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * Sign out the current user
     */
    @FXML
    private void signOut() {
        System.out.println("Signing out...");
        mainApplication.logoutUser();
    }

    /**
     * Go to edit profile view
     */
    @FXML
    private void goToEditProfileView() {
        mainApplication.goToEditProfile();
    }

    @FXML
    public void createReport() {
        mainApplication.initCreateReportScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }

    @FXML
    public void viewReports() {
        mainApplication.initViewReportsScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());

    }

    @FXML
    public void goToMap() {
        mainApplication.initMapViewScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());

    }

    @FXML
    public void createQualityReports() {
        mainApplication.initCreateQualityScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }

    @FXML
    public void viewQualityReports() {
        mainApplication.initQualityReportScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }
}
