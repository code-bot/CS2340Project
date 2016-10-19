package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Aman on 9/28/16.
 */
public enum States {

    AL("AL"),
    AK("AK"),
    AZ("AZ"),
    AR("AR"),
    CA("CA"),
    CO("CO"),
    CT("CT"),
    DE("DE"),
    FL("FL"),
    GA("GA"),
    HI("HI"),
    ID("ID"),
    IL("IL"),
    IN("IN"),
    IA("IA"),
    KS("KS"),
    KY("KY"),
    LA("LA"),
    ME("ME"),
    MD("MD"),
    MA("MA"),
    MI("MI"),
    MN("MN"),
    MS("MS"),
    MO("MO"),
    MT("MT"),
    NE("NE"),
    NV("NV"),
    NH("NH"),
    NJ("NJ"),
   	NM ("NM"),
    NY("NY"),
    NC("NC"),
    ND("ND"),
    OH("OH"),
    OK("OK"),
    OR("OR"),
    PA("PA"),
    RI("RI"),
    SC("SC"),
    SD("SD"),
    TN("TN"),
    TX("TX"),
    UT("UT"),
    VT("VT"),
    VA("VA"),
    WA("WA"),
    WV("WV"),
    WI("WI"),
    WY("WY");


    private final String value;



    private States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {

        return getValue();
    }

    /**
     * Transforms the ENUM into a list
     * @return  the final list
     */
    public static ObservableList<String> toList() {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (States value: values()) {
            list.add(value.name());
        }
        return list;
    }

    public static States stringToState(String state) {
        for (States val: values()) {
            if (val.getValue().equals(state)) {
                return val;
            }
        }
        return null;
    }
}