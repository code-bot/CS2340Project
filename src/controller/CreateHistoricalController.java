package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DatabaseModel;
import model.WaterQualityReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Rahul on 11/8/16.
 */
public class CreateHistoricalController {

    private MainFXApplication mainApplication;

    @FXML
    private ToggleButton virus;

    @FXML
    private ToggleButton contaminant;

    @FXML
    private TextField location;

    @FXML
    private TextField yearSelected;

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void initialize() {
        virus.setSelected(true);
        contaminant.setSelected(false);
    }

    @FXML
    public void createGraph() {
        String loc = location.getText();
        int year = Integer.parseInt(yearSelected.getText());
        boolean isVirus = false;
        boolean isContaminant = false;
        if (virus.isSelected()) {
            isVirus = true;
        }
        if (contaminant.isSelected()) {
            isContaminant = true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (!isVirus && !isContaminant) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Cannot Create Graph");
            alert.setContentText("Choose either virus or contaminant." +
                    " Currently you have picked neither");
        } else if (isVirus && isContaminant) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Cannot Create Graph");
            alert.setContentText("Choose either virus or contaminant." +
                    " Currently you have picked both.");
        }
    }

    @FXML
    public void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("If you cancel, the information will not be stored and you will be returned to the menu");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mainApplication.goToHomePage();
        }
    }
}
