package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.DatabaseModel;
import model.Report;

import java.util.Iterator;

//import javafx.scene.control.*;

/**
 * Created by Rahul on 10/28/16.
 */
@SuppressWarnings("ALL")
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
