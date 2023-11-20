package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchInterface implements Initializable {

    @FXML
    private TableView<?> activityTable;

    @FXML
    private ComboBox<Activity> comboBoxActivity;

    @FXML
    private Button searchTaskButton;

    @FXML
    private ComboBox<?> searchTypeCombo;

    @FXML
    private TableView<?> taskTable;

    @FXML
    private Button backButton;

    private Stage stage;
    private ProcessList processList;
    private Activity activity;

    public void uploadTable(){
        itemList = FXCollections.observableArrayList();
        for (Activity a: this.process.getActivities()) {
            itemList.add(a);
        }
        tableActivity.setItems(itemList);

        TableColumn<Process, String> nameColumn = new TableColumn<>("Proceso");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Activity, String> descriptionColumn = new TableColumn<>("Descripcion");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Activity, String> mandatoryColumn = new TableColumn<>("Obligatorio");
        mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

        this.tableActivity.getColumns().setAll(nameColumn, descriptionColumn, mandatoryColumn);
    }

    @FXML
    void backMenu(ActionEvent ignoredEvent) throws IOException {
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
    public void selectActivity(){
        this.activity = comboBoxActivity.getValue();
        this.uploadTable();
    }

    public ObservableList<Activity> generateComboBoxActivities(){
        ObservableList<Activity> comboActivity = FXCollections.observableArrayList();
        List<String> names = new ArrayList<>();
        for (Process process: this.processList.getProcessList()) {
            for (Activity a: process.getActivities()) {
                if(!names.contains(a.getName())) {
                    names.add(a.getName());
                    comboActivity.add(a);
                }
            }
        }
        return comboActivity;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.processList = ProcessList.getInstance();

        comboBoxActivity.setItems(generateComboBoxActivities());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
