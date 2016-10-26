package controller;

import fxapp.MainFXApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * Created by sahajbot on 10/10/16.
 */
public class MainViewController {

    private MainFXApplication mainApplication;

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void createReport() {
        mainApplication.initCreateReportScreen(mainApplication.getMainStage());
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
}
