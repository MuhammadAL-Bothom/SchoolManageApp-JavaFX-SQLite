package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.UserDAO;
import com.wise.app.model.User;
import com.wise.app.util.AlertUtil;
import com.wise.app.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML
    void onLogin() {
        try {
            String u = txtUsername.getText().trim();
            String p = txtPassword.getText().trim();

            User user = UserDAO.login(u, p);
            if (user == null) {
                AlertUtil.error("Wrong username or password");
                return;
            }

            Session.currentUser = user;
            MainApp.open("dashboard.fxml", "Dashboard");
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML
    void onGoRegister() {
        MainApp.open("register.fxml", "Register");
    }
}
