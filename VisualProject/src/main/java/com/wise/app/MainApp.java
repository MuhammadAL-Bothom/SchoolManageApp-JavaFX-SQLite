package com.wise.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            DB.init();
            primaryStage = stage;

            open("login.fxml", "Login");

            stage.setMinWidth(1050);
            stage.setMinHeight(650);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void open(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/com/wise/app/view/" + fxmlFile)
            );

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(MainApp.class.getResource("/com/wise/app/style/app.css").toExternalForm());

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);

        } catch (Exception e) {
            System.out.println("❌ Failed to load: " + fxmlFile);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
