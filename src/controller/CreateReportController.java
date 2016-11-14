package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.DatabaseModel;
import model.User;
import model.WaterSourceReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by sahajbot on 10/10/16.
 */
public class CreateReportController {

    private MainFXApplication mainApplication;
    @FXML
    private ToggleButton currLocBtn;

    @FXML
    private TextField latField;

    @FXML
    private TextField longField;

    @FXML
    private ComboBox<WaterSourceReport.WaterType> typeComboBox;

    @FXML
    private ComboBox<WaterSourceReport.WaterCondition> conditionComboBox;

    private DatabaseModel databaseModel;

    /**
     * Set application to main application type.
     * @param main application instance to set program to
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll(WaterSourceReport.WaterType.toList());
        typeComboBox.getSelectionModel().selectFirst();
        conditionComboBox.getItems().addAll(WaterSourceReport.WaterCondition.toList());
        conditionComboBox.getSelectionModel().selectFirst();
        currLocBtn.setSelected(true);
        databaseModel = DatabaseModel.getInstance();
    }

    @FXML
    public void createReport() {
        final double ATLLAT = 33.7490;
        final double ATLLONG = -84.3880;
        boolean coordErr = false;
        String lat = latField.getText();
        double latNum = ATLLAT;
        String lon = longField.getText();
        double lonNum = ATLLONG;
        WaterSourceReport.WaterType type = typeComboBox.getValue();
        WaterSourceReport.WaterCondition condition = conditionComboBox.getValue();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateObj =  new Date();
        String date = df.format(dateObj);
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String time = tf.format(dateObj);
        User currUser = databaseModel.getCurrentUser();
        String name = currUser.getEmail();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if ("".equals(lat)|| "".equals(lon)) {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long not provided, using current location! " +
                    "Make sure all information is accurate");
        } else {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long provided. Make sure all information is accurate");
            try {
                latNum = Double.parseDouble(lat);
                lonNum = Double.parseDouble(lon);
            } catch (NumberFormatException e) {     // User inputs incorrect format for latitude or longitude
                coordError();
                coordErr = true;
            }
        }

        if (!coordErr) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                WaterSourceReport report = new WaterSourceReport(date, time, name, latNum, lonNum, type, condition);
                if (databaseModel.addReport(report)) {
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
        alert.showAndWait();

    }}
