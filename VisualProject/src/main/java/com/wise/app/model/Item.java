package com.wise.app.model;

import javafx.beans.property.*;

public class Item {
    private final IntegerProperty itemId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public Item(int id, String name, int qty, double price) {
        this.itemId.set(id);
        this.name.set(name);
        this.quantity.set(qty);
        this.price.set(price);
    }

    public int getItemId() { return itemId.get(); }
    public IntegerProperty itemIdProperty() { return itemId; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    @Override public String toString() { return getName() + " (qty=" + getQuantity() + ")"; }
}
