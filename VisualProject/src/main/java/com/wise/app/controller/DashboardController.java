package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.TxDAO;
import com.wise.app.util.AlertUtil;
import com.wise.app.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblUser;
    @FXML private Label lblSales;
    @FXML private Label lblTx;

    @FXML
    public void initialize() {
        lblUser.setText(Session.currentUser == null ? "Guest" :
                Session.currentUser.getUsername() + " • " + Session.currentUser.getRole());

        try {
            lblSales.setText(String.valueOf(TxDAO.totalSales()));
            lblTx.setText(String.valueOf(TxDAO.totalTransactions()));
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML void goUsers() { MainApp.open("users.fxml", "Users"); }
    @FXML void goItems() { MainApp.open("items.fxml", "Items"); }
    @FXML void goTx() { MainApp.open("transactions.fxml", "Transactions"); }
    @FXML void goReports() { MainApp.open("reports.fxml", "Reports"); }
    @FXML void goAbout() { MainApp.open("about.fxml", "About"); }

    @FXML
    void logout() {
        Session.currentUser = null;
        MainApp.open("login.fxml", "Login");
    }
}
