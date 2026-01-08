package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.UserDAO;
import com.wise.app.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML
    void onRegister() {
        try {
            UserDAO.register(txtUsername.getText().trim(), txtPassword.getText().trim());
            AlertUtil.info("Account created. Now login.");
            MainApp.open("login.fxml", "Login");
        } catch (Exception e) {
            AlertUtil.error("Username maybe taken.\n" + e.getMessage());
        }
    }

    @FXML
    void onBack() {
        MainApp.open("login.fxml", "Login");
    }
}
