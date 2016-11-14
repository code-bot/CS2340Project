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
    private MainFXApplication mainApplication;

    /**
     * Set application to main application type.
     * @param main application instance to set program to
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }
    @FXML
    private ListView<String> listOfReports;
    @FXML
    public static final ObservableList items = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
//        listOfReports.getItems().clear();
        items.clear();
        Iterator iter = DatabaseModel.getInstance().getWaterQualityReports().iterator();
        while (iter.hasNext()) {
            Report report = (Report)iter.next();
            if (report.getTypeOfReport().equals("Quality Report")) {
                items.add(report);
            }
//            System.out.println(iter.next());
        }
//        listOfReports.getItems().removeAll(items);
        listOfReports.setItems(items);
    }
}
