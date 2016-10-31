package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by sahajbot on 10/10/16.
 */
public class WaterSourceReport extends Report {

    public enum WaterType {
        BOTTLED, WELL, STREAM, LAKE, SPRING, OTHER;

        /**
         * Transforms the enum into a list
         * @return  the final list
         */
        public static ObservableList<WaterType> toList() {
            ObservableList<WaterType> list = FXCollections.observableArrayList();

            for (WaterType value : values()) {
                list.add(value);
            }
            return list;
        }
    }

    public enum WaterCondition {
        WASTE, TREATABLECLEAR, TREATABLEMUDDY, POTABLE;

        /**
         * Transforms the enum into a list
         * @return  the final list
         */
        public static ObservableList<WaterCondition> toList() {
            ObservableList<WaterCondition> list = FXCollections.observableArrayList();

            for (WaterCondition value : values()) {
                list.add(value);
            }
            return list;
        }
    }

    private ObjectProperty<WaterType> type = new SimpleObjectProperty<>();
    private ObjectProperty<WaterCondition> condition = new SimpleObjectProperty<>();

    public WaterType getType() { return type.get(); }
    public void setType(WaterType waterType) { type.set(waterType); }

    public WaterCondition getCondition() { return condition.get(); }
    public void setCondition(WaterCondition waterCondition) { condition.set(waterCondition); }

    public WaterSourceReport(String date, String time, String name, double lat, double lon, WaterType type, WaterCondition condition) {
        super(date, time, name, lat, lon);
        this.type.set(type);
        this.condition.set(condition);
    }

    public static WaterCondition stringToCondition(String s) {
        switch(s) {
            case "WASTE":
                return WaterCondition.WASTE;
            case "TREATABLECLEAR":
                return WaterCondition.TREATABLECLEAR;
            case "TREATABLEMUDDY":
                return WaterCondition.TREATABLEMUDDY;
            case "POTABLE":
                return WaterCondition.POTABLE;
            default:
                return WaterCondition.WASTE;
        }
    }

    public static WaterType stringToWaterType(String s) {
        // BOTTLED, WELL, STREAM, LAKE, SPRING, OTHER;
        switch(s) {
            case "BOTTLED":
                return WaterType.BOTTLED;
            case "WELL":
                return WaterType.WELL;
            case "STREAM":
                return WaterType.STREAM;
            case "LAKE":
                return WaterType.LAKE;
            case "SPRING":
                return WaterType.SPRING;
            default:
                return WaterType.OTHER;
        }
    }

    @Override
    public String toString() {
        return "Num: " + super.getNum() + "\n" + "Date: " + super.getDate() + "\n"
                + "Time: " + super.getTime() + "\n" + "Name: " + super.getName()
                + "\n" + "Lat: " + super.getLat() + "\n" + "Long: " + super.getLong()
                + "\n" + "Type: " + this.getType() + "\n" + "Condition: " + this.getCondition();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WaterSourceReport) {
            WaterSourceReport report = (WaterSourceReport) o;
            if (this.getDate().equals(report.getDate())
                    && this.getType().equals(report.getType())
                    && this.getCondition().equals(report.getCondition())
                    && this.getName().equals(report.getName())
                    && this.getLat() == report.getLat()
                    && this.getLong() == report.getLong()) {
                return true;
            }
        }
        return false;
    }
}
