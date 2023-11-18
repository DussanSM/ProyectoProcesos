package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ProcessInterface {
    private Stage stage;
    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private TextField idProcess;

    @FXML
    private TextField nameProcess;

    @FXML
    private TableView<Process> tableProcess;

    @FXML
    private Button update;

    private ProcessList processList;

    @FXML
    public void initialize() {
        this.processList = ProcessList.getInstance();
        this.chargeTable();
    }

    public void chargeTable(){
        ObservableList<Process> itemList = FXCollections.observableArrayList(this.processList.getProcessList());
        tableProcess.setItems(itemList);

        TableColumn<Process, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Process, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.tableProcess.getColumns().setAll(idColumn, nameColumn);
    }

    @FXML
    void addProcess(ActionEvent event) {
        if(nameProcess.getText().isEmpty() && idProcess.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return;
        }

        this.processList.addProcess(new Process(idProcess.getText(), nameProcess.getText()));
        this.chargeTable();
    }

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

    @FXML
    void deleteProcess(ActionEvent event) {

    }

    @FXML
    void updateProcess(ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
