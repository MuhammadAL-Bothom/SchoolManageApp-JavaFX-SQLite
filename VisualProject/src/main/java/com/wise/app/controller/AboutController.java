package com.wise.app.controller;

import com.wise.app.MainApp;
import javafx.fxml.FXML;

public class AboutController {
    @FXML void back() { MainApp.open("dashboard.fxml", "Dashboard"); }
}
