package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Task;
import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;
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
    private TableView<Process> activityTable;

    @FXML
    private ComboBox<Activity> comboBoxActivity;

    @FXML
    private Button searchTaskButton;

    @FXML
    private ComboBox<?> searchTypeCombo;

    @FXML
    private TableView<Task> taskColumn;

    @FXML
    private TableView<?> taskTable;

    @FXML
    private Button backButton;

    private Stage stage;
    private ProcessList processList;
    private Activity activity;
    private ObservableList<Process> itemList;
    private ObservableList<Task> itemListTask;
    private Process process;

    public void uploadProcessTable(){

        List<Process> processes = this.processList.getProcessList();
        List<Process> p = new ArrayList<>();

        for (Process process: processes) {
            for (Activity a: process.getActivities()) {
                if(a.getName().equals(activity.getName())){
                    p.add(process);
                }
            }
        }

        itemList = FXCollections.observableArrayList(p);
        activityTable.setItems(itemList);

        TableColumn<Process, String> nameColumn = new TableColumn<>("Procesos");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.activityTable.getColumns().setAll(nameColumn);
    }

    public void uploadTaskTable(){

        itemListTask = FXCollections.observableArrayList();
        Queue<Task> tasks = this.activity.getTasks().clone();
        for (Task t: tasks) {
            itemListTask.add(t);
        }
        taskColumn.setItems(itemListTask);

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Tareas");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.taskColumn.getColumns().setAll(descriptionColumn);
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
        this.uploadProcessTable();
        uploadTaskTable();

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
