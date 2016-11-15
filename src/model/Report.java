package model;


import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Created by sahajbot on 10/10/16.
 */
@SuppressWarnings("ALL")
public abstract class Report {


    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final StringProperty reporterName = new SimpleStringProperty();
    private final DoubleProperty lat = new SimpleDoubleProperty();
    private final DoubleProperty lon = new SimpleDoubleProperty();



    public String getDate() { return date.get(); }

    String getTime() { return time.get(); }

    String getName() { return reporterName.get(); }

    public double getLat() { return lat.get(); }

    public double getLong() { return lon.get(); }

    public abstract String getTypeOfReport();

    Report(String date, String time, String name, double lat, double lon) {
        this.date.set(date);
        this.time.set(time);
        this.reporterName.set(name);
        this.lat.set(lat);
        this.lon.set(lon);
    }

}
