package model;

import javafx.beans.property.*;

/**
 * Created by sahajbot on 10/10/16.
 */
public abstract class Report {


    private StringProperty date = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private StringProperty reporterName = new SimpleStringProperty();
    private DoubleProperty lat = new SimpleDoubleProperty();
    private DoubleProperty lon = new SimpleDoubleProperty();



    public String getDate() { return date.get(); }
    public void setDate(String dateStr) { date.set(dateStr); }

    public String getTime() { return time.get(); }
    public void setTime(String timeStr) { time.set(timeStr); }

    public String getName() { return reporterName.get(); }
    public void setName(String name) { reporterName.set(name); }

    public double getLat() { return lat.get(); }
    public void setLat(double latitude) { lat.set(latitude); }

    public double getLong() { return lon.get(); }
    public void setLong(double longitude) { lon.set(longitude); }

    public abstract String getTypeOfReport();

    Report(String date, String time, String name, double lat, double lon) {
        this.date.set(date);
        this.time.set(time);
        this.reporterName.set(name);
        this.lat.set(lat);
        this.lon.set(lon);
    }

}
