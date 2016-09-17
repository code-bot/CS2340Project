package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sahaj Bhatt on 9/17/16.
 */
public class User {

    private final StringProperty username = new SimpleStringProperty();

    private final StringProperty password = new SimpleStringProperty();

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    User(String uname, String pass) {
        username.set(uname);
        password.set(pass);
    }
}
