package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

/**
 * Created by sahajbot on 10/10/16.
 */
public class MainViewController {

    private MainFXApplication mainApplication;

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    public void initalize() {

    }

    @FXML
    public void createReport() {
        mainApplication.initCreateReportScreen(mainApplication.getMainStage());
    }
}
