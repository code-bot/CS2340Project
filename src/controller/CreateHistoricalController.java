package controller;

import fxapp.MainFXApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import model.DatabaseModel;
import model.WaterQualityReport;


import java.util.ArrayList;

import java.util.Optional;

/**
 * Created by Rahul on 11/8/16.
 */
public class CreateHistoricalController {

    private MainFXApplication mainApplication;
    private final double ATLLAT = 33.7490;
    private final double ATLLONG = -84.3880;
    @FXML
    private ToggleButton virus;

    @FXML
    private ToggleButton contaminant;

    @FXML
    private ComboBox yearBox;

    @FXML
    private ToggleButton currLocBtn;

    @FXML
    private TextField latField;

    @FXML
    private TextField longField;

    /**
     * Make the main application the application instance passed in
     * @param main the application instance to set the program to
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void initialize() {
        virus.setSelected(true);
        contaminant.setSelected(false);
        yearBox.getItems().addAll("2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025");
        yearBox.getSelectionModel().select("2016");

        ToggleGroup group = new ToggleGroup();
        virus.setToggleGroup(group);
        contaminant.setToggleGroup(group);
        virus.setUserData("Virus");
        contaminant.setUserData("Contaminant");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle != null) {
                    if (group.getSelectedToggle().getUserData().equals("Virus")) {
                        virus.setSelected(true);
                        contaminant.setSelected(false);
                    } else {
                        virus.setSelected(false);
                        contaminant.setSelected(true);
                    }
                } else {
                    if (toggle.getUserData().equals("Virus")) {
                        virus.setSelected(false);
                        contaminant.setSelected(true);
                    } else {
                        virus.setSelected(true);
                        contaminant.setSelected(false);
                    }
                }
            }
        });
    }

    @FXML
    /**
     * Method called to set variables and create graph
     */
    public void createGraph() {
        boolean coordErr = false;
        String lat = latField.getText();
        double latNum = ATLLAT;
        String lon = longField.getText();
        double lonNum = ATLLONG;
        String yearStr = (String) yearBox.getSelectionModel().getSelectedItem();
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
            alert.setContentText("Lat and Long not provided, using current location!" +
                    " Make sure all information is accurate");
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
                    if (report.getLat() == latNum && report.getLong() == lonNum
                            && report.getDate().substring(6).equals(year)) {
                        reports.add(report);
                    }
                }
                mainApplication.initGraphViewScreen(mainApplication.getMainStage(),
                        reports, isVirus ? "Virus" : "Contaminant", yearStr);
            }
        }

    }

    @FXML
    /**
     * Method called to confirm a cancel of creating graph
     */
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

    /**
     * Creating dialogue for coordinate error
     */
    public void coordError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Latitude/Longitude Error");
        alert.setContentText("Make sure the latitude and longitude are in the correct format");
        Optional<ButtonType> result = alert.showAndWait();
    }
}
