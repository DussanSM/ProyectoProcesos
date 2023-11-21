package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.CsvService;
import com.example.proyectoprocesos.modelo.ProcessList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainScreen {
    private Stage stage;

    @FXML
    void back(ActionEvent ignoredEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/login.fxml"));
        Parent root = loader.load();
        Login controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }

    @FXML
    void goProcess(ActionEvent ignoredEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/proceso.fxml"));
        Parent root = loader.load();
        ProcessInterface controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }

    @FXML
    void goActivities(ActionEvent ignoredEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/actividad.fxml"));
        Parent root = loader.load();
        ActivityInterface controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }

    @FXML
    void goTask(ActionEvent ignoredEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/tarea.fxml"));
        Parent root = loader.load();
        TaskInterface controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }

    @FXML
    void goSearch(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/buscar.fxml"));
        Parent root = loader.load();
        SearchInterface controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void importData(ActionEvent ignoredEvent) throws IOException{
        ProcessList processList = ProcessList.getInstance();
        processList.importFromCsv("src/main/resources/com/example/proyectoprocesos/dataProcess.csv");
    }

    @FXML
    public void exportData(ActionEvent ignoredEvent) throws IOException{
        ProcessList processList = ProcessList.getInstance();
        processList.exportToCsv("src/main/resources/com/example/proyectoprocesos/dataProcess.csv");
    }

    public Stage getStage() {
        return stage;
    }
}
