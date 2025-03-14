package com.example.indivproj2_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class logSignApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(logSignApp.class.getResource("startPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 460);
        stage.setTitle("The Best Sign Up/Log In Page Ever");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}