package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class TaskInterface {

    @FXML
    private Button addTaskEnd;

    @FXML
    private Button addTaskPosition;

    @FXML
    private Button back;

    @FXML
    private ComboBox<Activity> comboBoxActivity;

    @FXML
    private ComboBox<String> comboBoxTime;

    @FXML
    private Button deleteTask;

    @FXML
    private TextField descriptionTask;

    @FXML
    private CheckBox mandatoryTask;

    @FXML
    private TableView<?> taskTable;

    @FXML
    private Button updateTask;

    private Stage stage;

    private Activity activity;

    @FXML
    void backMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectoprocesos/menu.fxml"));
        Parent root = loader.load();
        MainScreen controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        this.stage.close();
    }

    public boolean validation(){
        if(descriptionTask.getText().isEmpty() || comboBoxActivity.getValue().getName().isEmpty() ||
                comboBoxTime.getValue().isEmpty() ){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return false;
        }
        return true;
    }

    @FXML
    void addTask(ActionEvent event) {
        if (!validation()) {
            return;
        }

        int size = this.activity.getTasks().getSize();
        this.activity.pushTask(new Task(descriptionTask.getText(), mandatoryTask.isSelected()), comboBoxTime.getValue());

        if (size == this.process.getActivities().getSize()){
            JOptionPane.showMessageDialog(null, "El nombre ya existe");
            return;
        }

        this.uploadTable();
        clean();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
