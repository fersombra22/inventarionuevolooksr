package com.nuevolooksr.stock.ui;

import javafx.application.Application;
import com.nuevolooksr.stock.utils.StageManager;

import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        StageManager.setPrimaryStage(primaryStage);

        // Cargar la vista inicial (Login)
        StageManager.changeScene("/com/nuevolooksr/stock/views/LoginView.fxml");


    }


    public static void main(String[] args) {
        launch(args);
    }
}

