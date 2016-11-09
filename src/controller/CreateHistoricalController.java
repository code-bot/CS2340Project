package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DatabaseModel;
import model.WaterQualityReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private TextField yearSelected;

    @FXML
    private ToggleButton currLocBtn;

    @FXML
    private TextField latField;

    @FXML
    private TextField longField;

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
        boolean coordErr = false;
        String lat = latField.getText();
        double latNum = 33.7490;
        String lon = longField.getText();
        double lonNum = -84.3880;
        String yearStr = yearSelected.getText();
        String year = yearStr;
        if (yearStr.length() == 4) {
            year = yearStr.substring(2);
        }
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
        } else if (lat.equals("") || lon.equals("")) {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long not provided, using current location! Make sure all information is accurate");
        } else {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long provided. Make sure all information is accurate");
            try {
                latNum = Double.parseDouble(lat);
                System.out.println(latNum);
                lonNum = Double.parseDouble(lon);
            } catch (NumberFormatException e) {     // User inputs incorrect format for latitude or longitude
                coordError();
                coordErr = true;
            }
        }

        if (!coordErr) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ArrayList<WaterQualityReport> reports = new ArrayList<>();
                for (WaterQualityReport report : DatabaseModel.getInstance().getWaterQualityReports()) {
                    if (report.getLat() == latNum && report.getLong() == lonNum && report.getDate().substring(6).equals(year)) {
                        reports.add(report);
                    }
                }
                mainApplication.initGraphViewScreen(mainApplication.getMainStage(), reports, isVirus ? "Virus" : "Contaminant", yearStr);
            }
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

    public void coordError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Latitude/Longitude Error");
        alert.setContentText("Make sure the latitude and longitude are in the correct format");
        Optional<ButtonType> result = alert.showAndWait();
    }
}
