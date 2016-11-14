package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import java.util.Iterator;
import javafx.scene.control.*;
import model.DatabaseModel;

import model.Report;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Created by karanlakhani on 10/11/16.
 */
public class ViewReportsController {

    @FXML
    private ListView<String> listOfReports;
    @FXML
    public static final ObservableList items = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
//        listOfReports.getItems().clear();
        items.clear();
        DatabaseModel databaseModel = DatabaseModel.getInstance();
        Iterator iter = databaseModel.getSourceReports().iterator();
        while (iter.hasNext()) {
            Report report = (Report)iter.next();
            if ("Source Report".equals(report.getTypeOfReport())) {
                items.add(report);
            }
        }
        listOfReports.setItems(items);
    }
}
