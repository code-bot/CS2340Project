package controller;

import fxapp.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.DatabaseModel;
import model.UserLevel;

/**
 * Created by Matt Sternberg on 9/21/16.
 */
@SuppressWarnings("ALL")
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
                editProfileView();
            }
        });

        MenuItem createSourceReport = new MenuItem("Create Source Report");
        createSourceReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createSourceReport();
            }
        });

        goToPage.getItems().addAll(profile, createSourceReport);

        DatabaseModel model = DatabaseModel.getInstance();

        if (model.getCurrentUser().getUserLevel() != UserLevel.NORMAL) {
            MenuItem viewSourceReport = new MenuItem("View Source Reports");
            viewSourceReport.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    viewSourceReports();
                }
            });

            MenuItem createQualityReport = new MenuItem("Create Quality Reports");
            createQualityReport.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    createQualityReport();
                }
            });

            goToPage.getItems().addAll(viewSourceReport, createQualityReport);

            if (model.getCurrentUser().getUserLevel() != UserLevel.WORKER) {
                MenuItem viewQualityReport = new MenuItem("View Quality Reports");
                viewQualityReport.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        viewQualityReports();
                    }
                });

                MenuItem createHistoricalGraph
                        = new MenuItem("Create Historical Report");
                createHistoricalGraph.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        createHistoricalGraph();
                    }
                });

                goToPage.getItems().addAll(viewQualityReport, createHistoricalGraph);
            }
        }

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
        mainApplication.logoutUser();
    }

    /**
     * Go to edit profile view
     */
    @FXML
    private void editProfileView() {
        mainApplication.goToEditProfile();
    }

    @FXML
    private void createSourceReport() {
        mainApplication.initCreateReportScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }

    @FXML
    private void viewSourceReports() {
        mainApplication.initViewReportsScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());

    }

    @FXML
    private void createQualityReport() {
        mainApplication.initCreateQualityScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }

    @FXML
    private void viewQualityReports() {
        mainApplication.initQualityReportScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }

    @FXML
    private void createHistoricalGraph() {
        //mainApplication.initGraphViewScreen(mainApplication.getMainStage(),
        // new ArrayList(DatabaseModel.getInstance().getWaterQualityReports()), "Virus");
        mainApplication.initCreateGraphScreen(mainApplication.getMainStage());
        mainApplication.initBackMenu(mainApplication.getMainStage());
    }
}
