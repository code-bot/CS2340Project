package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents level of power of user within the app
 * Created by Sahaj Bhatt on 9/17/16.
 */
public enum UserLevel {
    NORMAL, WORKER, MANAGER, ADMIN;



    public static ObservableList<UserLevel> toList() {
        ObservableList<UserLevel> list = FXCollections.observableArrayList();

        for (UserLevel value : values()) {
            list.add(value);
        }
        return list;
    }
}