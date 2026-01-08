package com.wise.app.model;

import javafx.beans.property.*;

public class Tx {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private final StringProperty date = new SimpleStringProperty();

    public Tx(int id, String username, String itemName, int qty, double total, String date) {
        this.id.set(id);
        this.username.set(username);
        this.itemName.set(itemName);
        this.qty.set(qty);
        this.total.set(total);
        this.date.set(date);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty itemNameProperty() { return itemName; }
    public IntegerProperty qtyProperty() { return qty; }
    public DoubleProperty totalProperty() { return total; }
    public StringProperty dateProperty() { return date; }

    // اختياري (لكن ممتاز للعرض/الديباغ)
    public int getId() { return id.get(); }
    public String getUsername() { return username.get(); }
    public String getItemName() { return itemName.get(); }
    public int getQty() { return qty.get(); }
    public double getTotal() { return total.get(); }
    public String getDate() { return date.get(); }
}
