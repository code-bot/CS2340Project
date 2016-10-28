package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import model.DatabaseModel;
import model.Model;
import model.WaterQualityReport;
import model.WaterSourceReport;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Rahul on 10/28/16.
 */

public class CreateQualityController {

    private MainFXApplication mainApplication;

    @FXML
    private ToggleButton currLocBtn;

    @FXML
    private TextField latField;

    @FXML
    private TextField longField;

    @FXML
    private ComboBox<WaterQualityReport.WaterSafety> conditionComboBox;

    @FXML
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;


    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    public void initialize() {
        conditionComboBox.getItems().addAll(WaterQualityReport.WaterSafety.toList());
        conditionComboBox.getSelectionModel().selectFirst();
        currLocBtn.setSelected(true);
    }

    @FXML
    public void createReport() {
        boolean coordErr = false;
        String lat = latField.getText();
        double latNum = 33.7490;
        String lon = longField.getText();
        double lonNum = -84.3880;
        WaterQualityReport.WaterSafety safety = conditionComboBox.getValue();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateObj =  new Date();
        String date = df.format(dateObj);
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String time = tf.format(dateObj);
        String name = DatabaseModel.getInstance().getCurrentUser().getEmail();
        String vPPM = virusPPM.getText();
        String cPPM = contaminantPPM.getText();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (lat.equals("") || lon.equals("")) {
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
                WaterQualityReport report = new WaterQualityReport(date, time, name, latNum, lonNum, safety, vPPM, cPPM);
                System.out.println(report);
                if (Model.getInstance().addReport(report)) {
                    mainApplication.goToHomePage();
                }
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

    }}
