package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.ItemDAO;
import com.wise.app.dao.TxDAO;
import com.wise.app.model.Item;
import com.wise.app.model.Tx;
import com.wise.app.util.AlertUtil;
import com.wise.app.util.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionsController {

    @FXML private ComboBox<Item> cbItems;
    @FXML private TextField txtQty;

    @FXML private TableView<Tx> table;
    @FXML private TableColumn<Tx, Number> colId;
    @FXML private TableColumn<Tx, String> colUser;
    @FXML private TableColumn<Tx, String> colItem;
    @FXML private TableColumn<Tx, Number> colQty;
    @FXML private TableColumn<Tx, Number> colTotal;
    @FXML private TableColumn<Tx, String> colDate;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(d -> d.getValue().idProperty());
        colUser.setCellValueFactory(d -> d.getValue().usernameProperty());
        colItem.setCellValueFactory(d -> d.getValue().itemNameProperty());
        colQty.setCellValueFactory(d -> d.getValue().qtyProperty());
        colTotal.setCellValueFactory(d -> d.getValue().totalProperty());
        colDate.setCellValueFactory(d -> d.getValue().dateProperty());

        reloadItems();
        refreshTx();
    }

    private void reloadItems() {
        try {
            cbItems.setItems(FXCollections.observableArrayList(ItemDAO.getAll()));
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    private void refreshTx() {
        try {
            table.setItems(FXCollections.observableArrayList(TxDAO.getAll()));
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML
    void createTx() {
        try {
            Item it = cbItems.getSelectionModel().getSelectedItem();
            if (it == null) return;
            int qty = Integer.parseInt(txtQty.getText().trim());

            TxDAO.createTx(Session.currentUser.getUserId(), it.getItemId(), qty);

            AlertUtil.info("Transaction saved!");
            txtQty.clear();
            reloadItems();
            refreshTx();
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML void back() { MainApp.open("dashboard.fxml", "Dashboard"); }
}
