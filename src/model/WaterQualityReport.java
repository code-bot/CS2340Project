package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Rahul on 10/28/16.
 */
public class WaterQualityReport extends Report {

    public enum WaterSafety {
        SAFE, TREATABLE, UNSAFE;

        public static ObservableList<WaterSafety> toList() {
            ObservableList<WaterSafety> list = FXCollections.observableArrayList();

            for (WaterSafety value : values()) {
                list.add(value);
            }
            return list;
        }
    }

    public ObjectProperty<WaterSafety> safety = new SimpleObjectProperty<>();

    public WaterSafety getSafety() { return safety.get(); }
    public void setType(WaterSafety waterSafety) { safety.set(waterSafety); }

    public ObjectProperty<String> virusPPM = new SimpleObjectProperty<>();
    public ObjectProperty<String> contaminantPPM = new SimpleObjectProperty<>();

    public WaterQualityReport(String date, String time, String name, double lat,
                              double lon, WaterSafety safety, String virusPPM, String contaminantPPM) {
        super(date, time, name, lat, lon);
        this.safety.set(safety);
        this.virusPPM.set(virusPPM);
        this.contaminantPPM.set(contaminantPPM);
    }

    @Override
    public String toString() {
        return "Num: " + super.getNum() + "\n" + "Date: " + super.getDate() + "\n"
                + "Time: " + super.getTime() + "\n" + "Name: " + super.getName()
                + "\n" + "Lat: " + super.getLat() + "\n" + "Long: " + super.getLong()
                + "\n" + "Type: " + this.getSafety();
    }
}
