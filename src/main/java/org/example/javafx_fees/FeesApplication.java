/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */


package org.example.javafx_fees;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//Todo: Change DB password to "password" in /resources/db.properties.
public class FeesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FeesApplication.class.getResource("fees-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true); // Maximize on launch
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}