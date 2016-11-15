package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import model.DatabaseModel;
import model.User;
import model.WaterQualityReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;



/**
 * Created by Rahul on 10/28/16.
 */

@SuppressWarnings("ALL")
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


    private DatabaseModel databaseModel;

    /**
     * Set application to main application type.
     * @param main application instance to set program to
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * put items into the comboboxes so that user can select from existing enum
     */
    @FXML
    public void initialize() {
        conditionComboBox.getItems().addAll(WaterQualityReport.WaterSafety.toList());
        conditionComboBox.getSelectionModel().selectFirst();
        currLocBtn.setSelected(true);
        databaseModel = DatabaseModel.getInstance();
    }

    /**
     * Create report with information given from the user
     */
    @FXML
    public void createReport() {
        final double ATLLAT = 33.7490;
        final double ATLLONG = -84.3880;
        boolean coordErr = false;
        String lat = latField.getText();
        double latNum = ATLLAT;
        String lon = longField.getText();
        double lonNum = ATLLONG;
        WaterQualityReport.WaterSafety safety = conditionComboBox.getValue();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateObj =  new Date();
        String date = df.format(dateObj);
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String time = tf.format(dateObj);
        User currUser = databaseModel.getCurrentUser();
        String name = currUser.getEmail();
        String vPPMString = virusPPM.getText();
        String cPPMString = contaminantPPM.getText();
        double vPPM = Double.parseDouble(vPPMString);
        double cPPM = Double.parseDouble(cPPMString);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if ("".equals(lat)|| "".equals(lon)) {
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
                lonNum = Double.parseDouble(lon);
            } catch (NumberFormatException e) {     // User inputs incorrect format for latitude or longitude
                coordError();
                coordErr = true;
            }
        }

        if (!coordErr) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                WaterQualityReport report = new WaterQualityReport(date,
                        time, name, latNum, lonNum, safety, vPPM, cPPM);
                if (databaseModel.addReport(report)) {
                    mainApplication.goToHomePage();
                }
            }
        }
    }

    /**
     * Stop creating the quality report and confirm you want to cancel creation
     */
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

    /**
     * Throw an error if there is a coordinate error
     */
    private void coordError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Latitude/Longitude Error");
        alert.setContentText("Make sure the latitude and longitude are in the correct format");
        alert.showAndWait();
    }
}
