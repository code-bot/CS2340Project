package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
