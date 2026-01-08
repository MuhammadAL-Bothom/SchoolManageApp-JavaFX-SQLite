package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.ItemDAO;
import com.wise.app.model.Item;
import com.wise.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemsController {

    @FXML private TableView<Item> table;
    @FXML private TableColumn<Item, Number> colId;
    @FXML private TableColumn<Item, String> colName;
    @FXML private TableColumn<Item, Number> colQty;
    @FXML private TableColumn<Item, Number> colPrice;

    @FXML private TextField txtName, txtQty, txtPrice, txtSearch;

    private ObservableList<Item> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getSelectionModel().selectedItemProperty().addListener((a,b,it)->{
            if(it!=null){
                txtName.setText(it.getName());
                txtQty.setText(String.valueOf(it.getQuantity()));
                txtPrice.setText(String.valueOf(it.getPrice()));
            }
        });

        txtSearch.textProperty().addListener((a,b,q)-> filter(q));
        refresh();
    }

    private void refresh() {
        try {
            data = FXCollections.observableArrayList(ItemDAO.getAll());
            table.setItems(data);
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    private void filter(String q) {
        if (q == null || q.isBlank()) {
            table.setItems(data);
            return;
        }
        String s = q.toLowerCase();
        table.setItems(data.filtered(i -> i.getName().toLowerCase().contains(s)));
    }

    @FXML
    void add() {
        try {
            ItemDAO.add(txtName.getText().trim(),
                    Integer.parseInt(txtQty.getText().trim()),
                    Double.parseDouble(txtPrice.getText().trim()));
            clear();
            refresh();
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML
    void update() {
        try {
            Item it = table.getSelectionModel().getSelectedItem();
            if (it == null) return;
            ItemDAO.update(it.getItemId(),
                    txtName.getText().trim(),
                    Integer.parseInt(txtQty.getText().trim()),
                    Double.parseDouble(txtPrice.getText().trim()));
            clear();
            refresh();
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML
    void delete() {
        try {
            Item it = table.getSelectionModel().getSelectedItem();
            if (it == null) return;
            ItemDAO.delete(it.getItemId());
            clear();
            refresh();
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    private void clear() {
        txtName.clear(); txtQty.clear(); txtPrice.clear();
    }

    @FXML void back() { MainApp.open("dashboard.fxml", "Dashboard"); }
}
