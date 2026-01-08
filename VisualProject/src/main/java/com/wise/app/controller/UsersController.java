package com.wise.app.controller;

import com.wise.app.MainApp;
import com.wise.app.dao.UserDAO;
import com.wise.app.model.User;
import com.wise.app.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsersController {

    @FXML private TableView<User> table;
    @FXML private TableColumn<User, Number> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colRole;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(d -> d.getValue().userIdProperty());
        colUsername.setCellValueFactory(d -> d.getValue().usernameProperty());
        colRole.setCellValueFactory(d -> d.getValue().roleProperty());
        refresh();
    }

    private void refresh() {
        try {
            table.setItems(FXCollections.observableArrayList(UserDAO.getAll()));
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML
    void deleteSelected() {
        try {
            User u = table.getSelectionModel().getSelectedItem();
            if (u == null) return;
            if ("admin".equalsIgnoreCase(u.getUsername())) {
                AlertUtil.error("Can't delete admin user.");
                return;
            }
            UserDAO.delete(u.getUserId());
            refresh();
        } catch (Exception e) {
            AlertUtil.error(e.getMessage());
        }
    }

    @FXML void back() { MainApp.open("dashboard.fxml", "Dashboard"); }
}
