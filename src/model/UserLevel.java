package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents level of power of user within the app
 * Created by Sahaj Bhatt on 9/17/16.
 */
public enum UserLevel {
    NORMAL, WORKER, MANAGER, ADMIN;


    /**
     * Transforms the enum into a list
     * @return  the final list
     */
    public static ObservableList<UserLevel> toList() {
        ObservableList<UserLevel> list = FXCollections.observableArrayList();

        for (UserLevel value : values()) {
            list.add(value);
        }
        return list;
    }

    public static UserLevel stringToUserLevel(String level) {
        switch(level) {
            case "NORMAL":
                return UserLevel.NORMAL;
            case "WORKER":
                return UserLevel.WORKER;
            case "MANAGER":
                return UserLevel.MANAGER;
            case "ADMIN":
                return UserLevel.ADMIN;
        }
        return null;
    }
}