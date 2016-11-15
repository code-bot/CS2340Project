package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by sahajbot on 10/10/16.
 */
@SuppressWarnings("ALL")
public class WaterSourceReport extends Report {

    public enum WaterType {
        BOTTLED, WELL, STREAM, LAKE, SPRING, OTHER;

        /**
         * Transforms the enum into a list
         * @return  the final list
         */
        public static Collection<WaterType> toList() {
            ObservableList<WaterType> list = FXCollections.observableArrayList();

            Collections.addAll(list, values());
            return list;
        }
    }

    public enum WaterCondition {
        WASTE, TREATABLECLEAR, TREATABLEMUDDY, POTABLE;

        /**
         * Transforms the enum into a list
         * @return  the final list
         */
        public static Collection<WaterCondition> toList() {
            ObservableList<WaterCondition> list = FXCollections.observableArrayList();

            Collections.addAll(list, values());
            return list;
        }
    }

    private static int num = 1;

    private final IntegerProperty reportNum = new SimpleIntegerProperty();
    public int getNum() { return reportNum.get(); }

    private final ObjectProperty<WaterType> type = new SimpleObjectProperty<>();
    private final ObjectProperty<WaterCondition> condition = new SimpleObjectProperty<>();

    private WaterType getType() { return type.get(); }

    private WaterCondition getCondition() { return condition.get(); }

    public WaterSourceReport(String date, String time, String name,
                             double lat, double lon, WaterType type, WaterCondition condition) {
        super(date, time, name, lat, lon);
        this.type.set(type);
        this.condition.set(condition);
        this.reportNum.set(num);
        num++;
    }

    public WaterSourceReport(int num, String date, String time, String name,
                             double lat, double lon, WaterType type, WaterCondition condition) {
        super(date, time, name, lat, lon);
        this.type.set(type);
        this.condition.set(condition);
        this.reportNum.set(num);
        WaterSourceReport.num++;
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
        switch (s) {
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
    public String getTypeOfReport() {
        return "Source Report";
    }

    @Override
    public String toString() {
        return "Num: " + this.getNum() + "\n" + "Date: " + super.getDate() + "\n"
                + "Time: " + super.getTime() + "\n" + "Name: " + super.getName()
                + "\n" + "Lat: " + super.getLat() + "\n" + "Long: " + super.getLong()
                + "\n" + "Type: " + this.getType() + "\n" + "Condition: " + this.getCondition();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!other.getClass().equals(this.getClass())) {
            return false;
        }
        WaterSourceReport that = (WaterSourceReport) other;
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
