package controller;

import fxapp.MainFXApplication;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import model.WaterQualityReport;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sahajbot on 11/8/16.
 */
public class GraphViewController {
    private MainFXApplication mainApplication;

    private final ObjectProperty<ArrayList<WaterQualityReport>> reports = new SimpleObjectProperty<>();

    public void setReports(ArrayList<WaterQualityReport> r) {
        reports.set(r);
        System.out.println(reports.get());
    }

    private final StringProperty type = new SimpleStringProperty();

    public void setType(String t) { type.set(t); }

    @FXML
    private ScatterChart chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    /**
     * Set the main application reference
     * @param main  The main application
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private void initialize() {
        xAxis.setLabel("Month");
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(12);
    }

    public void loadGraph(String year) {
        yAxis.setLabel(type.get() + " (ppm)");
        chart.setTitle("Monthly Change in Water Quality for " + year);
        if (reports.get() != null) {
            Series<Integer, Double> series = new Series<>();
            for (WaterQualityReport report : reports.get()) {
                Double yVal;
                if (type.get().equals("Virus")) {
                    yVal = report.getVppm();
                } else {
                    yVal = report.getCppm();
                }
                Integer xVal = Integer.parseInt(report.getDate().substring(0, 2));
                series.getData().add(new XYChart.Data<>(xVal, yVal));

            }
            chart.getData().add(series);
        }
    }
}
