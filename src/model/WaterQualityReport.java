package model;

import javafx.beans.property.*;
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

    public static WaterSafety stringToSafety(String s) {
        switch(s) {
            case "SAFE":
                return WaterSafety.SAFE;
            case "TREATABLE":
                return WaterSafety.TREATABLE;
            case "UNSAFE":
                return WaterSafety.UNSAFE;
        }
        return WaterSafety.UNSAFE;
    }

    private static int num = 1;

    private IntegerProperty reportNum = new SimpleIntegerProperty();
    public int getNum() { return reportNum.get(); }
    public void setNum(int num) { reportNum.set(num); }

    public ObjectProperty<WaterSafety> safety = new SimpleObjectProperty<>();

    public WaterSafety getSafety() { return safety.get(); }
    public void setType(WaterSafety waterSafety) { safety.set(waterSafety); }

    private DoubleProperty virusPPM = new SimpleDoubleProperty();
    private DoubleProperty contaminantPPM = new SimpleDoubleProperty();

    public WaterQualityReport(String date, String time, String name, double lat,
                              double lon, WaterSafety safety, double virusPPM, double contaminantPPM) {
        super(date, time, name, lat, lon);

        this.safety.set(safety);
        this.virusPPM.set(virusPPM);
        this.contaminantPPM.set(contaminantPPM);
        this.reportNum.set(num);
        num++;
    }

    public WaterQualityReport(int num, String date, String time, String name, double lat,
                              double lon, WaterSafety safety, double virusPPM, double contaminantPPM) {
        super(date, time, name, lat, lon);

        this.safety.set(safety);
        this.virusPPM.set(virusPPM);
        this.contaminantPPM.set(contaminantPPM);
        this.reportNum.set(num);
        WaterQualityReport.num++;
    }

    public double getVppm() {
        return virusPPM.get();
    }

    public double getCppm() {
        return contaminantPPM.get();
    }

    public String getTypeOfReport() {
        return "Quality Report";
    }

    @Override
    public String toString() {
        return "Num: " + this.getNum() + "\n" + "Date: " + super.getDate() + "\n"
                + "Time: " + super.getTime() + "\n" + "Name: " + super.getName()
                + "\n" + "Lat: " + super.getLat() + "\n" + "Long: " + super.getLong()
                + "\n" + "Type: " + this.getSafety() + "\n" + "Virus PPM: " + virusPPM.get()
                + "\n" + "Contaminant PPM: " + contaminantPPM.get();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        WaterQualityReport that = (WaterQualityReport) other;
        if (that.getNum() != this.getNum()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return num;
    }
}
