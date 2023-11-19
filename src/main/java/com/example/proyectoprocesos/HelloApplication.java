package com.example.proyectoprocesos;

import com.example.proyectoprocesos.controller.Login;
import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Login controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        ProcessList processList = ProcessList.getInstance();
        launch();
    }
}