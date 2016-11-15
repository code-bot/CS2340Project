package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import java.util.Iterator;
//import javafx.scene.control.*;
import javafx.scene.control.ListView;
import model.DatabaseModel;
import model.Report;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Created by Rahul on 10/28/16.
 */
public class QualityController {

    @FXML
    private ListView<String> listOfReports;
    @FXML
    private static final ObservableList items = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        items.clear();
        DatabaseModel databaseModel = DatabaseModel.getInstance();
        Iterator iter = databaseModel.getWaterQualityReports().iterator();
        while (iter.hasNext()) {
            Report report = (Report)iter.next();
            if ("Quality Report".equals(report.getTypeOfReport())) {
                items.add(report);
            }
        }
        listOfReports.setItems(items);
    }
}
