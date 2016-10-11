package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Model;
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
    }

    @FXML
    public void createReport() {
        String lat = latField.getText();
        double latNum = 33.7490;
        String lon = longField.getText();
        double lonNum = -84.3880;
        WaterSourceReport.WaterType type = typeComboBox.getValue();
        WaterSourceReport.WaterCondition condition = conditionComboBox.getValue();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateObj =  new Date();
        String date = df.format(dateObj);
        DateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String time = tf.format(dateObj);
        String name = Model.getInstance().getCurrentUser().getEmail();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (lat.equals("") || lon.equals("")) {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long not provided, using current location! Make sure all information is accurate");
        } else {
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Submit Report?");
            alert.setContentText("Lat and Long provided. Make sure all information is accurate");
            latNum = Double.parseDouble(lat);
            lonNum = Double.parseDouble(lon);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            WaterSourceReport report = new WaterSourceReport(date, time, name, latNum, lonNum, type, condition);
            System.out.println(report);
            if (Model.getInstance().addReport(report)) {
                mainApplication.initMenu(mainApplication.getMainStage());
                mainApplication.initHomeScreen(mainApplication.getMainStage());
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
            mainApplication.initMenu(mainApplication.getMainStage());
            mainApplication.initHomeScreen(mainApplication.getMainStage());
        }
    }
}
