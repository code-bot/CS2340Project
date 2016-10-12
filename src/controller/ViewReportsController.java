package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import java.util.Iterator;
import javafx.scene.control.*;
import model.Model;
import model.WaterSourceReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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

        Iterator iter = Model.getInstance().getReports().iterator();
        while (iter.hasNext()) {
            items.add(iter.next());
            items.add("");
//            System.out.println(iter.next());
        }
        listOfReports.setItems(items);

    }
}