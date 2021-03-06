package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import java.util.Iterator;
import javafx.scene.control.*;
import model.Model;
import model.Report;
import model.WaterSourceReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Created by Rahul on 10/28/16.
 */
public class QualityController {
    private MainFXApplication mainApplication;

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
        Iterator iter = Model.getInstance().getReports().iterator();
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
