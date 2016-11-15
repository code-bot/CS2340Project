package controller;

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

import java.util.ArrayList;

/**
 * Created by sahajbot on 11/8/16.
 */
@SuppressWarnings("ALL")
public class GraphViewController {

    private final ObjectProperty<ArrayList<WaterQualityReport>> reports = new SimpleObjectProperty<>();

    /**
     * Sets the reports to be a part of the graph
     * @param r the reports to be viewed
     */
    public void setReports(ArrayList<WaterQualityReport> r) {
        reports.set(r);
    }

    private final StringProperty type = new SimpleStringProperty();

    /**
     * Sets the type
     * @param t String indicating the type
     */
    public void setType(String t) { type.set(t); }

    @FXML
    private ScatterChart chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private final int months = 12;



    /**
     * initalize the graphs
     */
    @FXML
    private void initialize() {
        xAxis.setLabel("Month");
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(months);
    }

    /**
     * The graphs to load
     * @param year the year to start with
     */
    public void loadGraph(String year) {
        yAxis.setLabel(type.get() + " (ppm)");
        chart.setTitle("Monthly Change in Water Quality for " + year);
        if (reports.get() != null) {
            Series<Integer, Double> series = new Series<>();
            Double[] monthlyAverages = new Double[months];
            int[] numMonthlyReports = new int[months];
            for (int i = 0; i < months; i++) {
                monthlyAverages[i] = 0.0;
                numMonthlyReports[i] = 0;
            }
            for (WaterQualityReport report : reports.get()) {
                Double yVal;
                if ("Virus".equals(type.get())) {
                    yVal = report.getVppm();
                } else {
                    yVal = report.getCppm();
                }
                Integer xVal = Integer.parseInt(report.getDate().substring(0, 2));
                monthlyAverages[xVal] += yVal;
                numMonthlyReports[xVal] += 1;
                //series.getData().add(new XYChart.Data<>(xVal, yVal));

            }
            for (int i = 0; i < months; i++) {
                int numReports = numMonthlyReports[i];
                if (numReports != 0) {
                    monthlyAverages[i] /= numReports;
                    series.getData().add(new XYChart.Data<>(i, monthlyAverages[i]));
                }
            }
            chart.getData().add(series);
        }
    }
}
