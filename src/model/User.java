package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class User {

    private final StringProperty username = new SimpleStringProperty();

    private final StringProperty password = new SimpleStringProperty();

    private final ObjectProperty<UserLevel> level = new SimpleObjectProperty<>();

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public UserLevel getUserLevel() { return level.get(); }

    User(String uname, String pass, UserLevel level) {
        username.set(uname);
        password.set(pass);
        this.level.set(level);
    }
}
