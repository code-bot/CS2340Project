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
        Iterator iter = DatabaseModel.getInstance().getSourceReports().iterator();
        while (iter.hasNext()) {
            Report report = (Report)iter.next();
            if (report.getTypeOfReport().equals("Source Report")) {
                items.add(report);
            }
//            System.out.println(iter.next());
        }
//        listOfReports.getItems().removeAll(items);
        listOfReports.setItems(items);
    }
}
