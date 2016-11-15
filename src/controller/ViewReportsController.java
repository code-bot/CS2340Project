package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.DatabaseModel;
import model.Report;

import java.util.Iterator;

/**
 * Created by karanlakhani on 10/11/16.
 */
@SuppressWarnings("ALL")
public class ViewReportsController {

    @FXML
    private ListView<String> listOfReports;
    @FXML
    private static final ObservableList items = FXCollections.observableArrayList();


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
