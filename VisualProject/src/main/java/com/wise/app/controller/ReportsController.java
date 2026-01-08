package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.TxDAO;
import com.wise.app.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportsController {

    @FXML private Label lblSales;
    @FXML private Label lblCount;

    @FXML
    public void initialize() {
        try {
            lblSales.setText("Total Sales: " + TxDAO.totalSales());
            lblCount.setText("Total Transactions: " + TxDAO.totalTransactions());
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML void back() { MainApp.open("dashboard.fxml", "Dashboard"); }
}
