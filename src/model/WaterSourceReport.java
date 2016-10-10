package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by sahajbot on 10/10/16.
 */
public class WaterSourceReport extends Report {

    private enum WaterType {
        BOTTLED, WELL, STREAM, LAKE, SPRING, OTHER
    }

    private enum WaterCondition {
        WASTE, TREATABLECLEAR, TREATABLEMUDDY, POTABLE
    }

    private ObjectProperty<WaterType> type = new SimpleObjectProperty<>();
    private ObjectProperty<WaterCondition> condition = new SimpleObjectProperty<>();

    public WaterType getType() { return type.get(); }
    public void setType(WaterType waterType) { type.set(waterType); }

    public WaterCondition getCondition() { return condition.get(); }
    public void setCondition(WaterCondition waterCondition) { condition.set(waterCondition); }

    WaterSourceReport(String date, String time, String name, double lat, double lon, WaterType type, WaterCondition condition) {
        super(date, time, name, lat, lon);
        this.type.set(type);
        this.condition.set(condition);
    }
}
