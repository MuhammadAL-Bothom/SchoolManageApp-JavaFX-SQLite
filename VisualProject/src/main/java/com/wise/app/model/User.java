package com.wise.app.model;

import javafx.beans.property.*;

public class User {
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty();

    public User(int id, String username, String role) {
        this.userId.set(id);
        this.username.set(username);
        this.role.set(role);
    }

    public int getUserId() { return userId.get(); }
    public IntegerProperty userIdProperty() { return userId; }

    public String getUsername() { return username.get(); }
    public StringProperty usernameProperty() { return username; }

    public String getRole() { return role.get(); }
    public StringProperty roleProperty() { return role; }

    @Override public String toString() { return getUsername() + " (" + getRole() + ")"; }
}
