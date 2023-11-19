package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Task;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskInterface implements Initializable {

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
    private TableView<Task> taskTable;

    @FXML
    private Button updateTask;

    private String time = "0";
    private Stage stage;
    private Task task;
    private ObservableList<Task> itemList;
    private Activity activity;
    private ProcessList processList;
    int positionTask = 0;

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

    public void uploadTable(){
        itemList = FXCollections.observableArrayList();
        for (Task t: this.activity.getTasks()) {
            itemList.add(t);
        }
        taskTable.setItems(itemList);

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Descripcion");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Task, String> mandatoryColumn = new TableColumn<>("Obligatorio");
        mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

        this.taskTable.getColumns().setAll(descriptionColumn, mandatoryColumn);
    }

    @FXML
    void addTask(ActionEvent event) {
        if (!validation()) {
            return;
        }

//        int size = this.activity.getTasks().getSize();
        this.activity.pushTask(new Task(descriptionTask.getText(), mandatoryTask.isSelected(), 0.0));
//
//        if (size == this.process.getActivities().getSize()){
//            JOptionPane.showMessageDialog(null, "El nombre ya existe");
//            return;
//        }
//
        this.uploadTable();
        clean();
    }

    @FXML
    public void selectActivity(){
        activity = comboBoxActivity.getValue();
        this.uploadTable();
        this.clean();
    }

    @FXML
    public void selectTime(){
        time = comboBoxTime.getValue();
    }

    public void selectTask() {
        task = getTablaPersonasSeleccionada();

        if (task != null) {
            positionTask = itemList.indexOf(task);
            descriptionTask.setText(task.getDescription());
            mandatoryTask.setSelected(task.isMandatory());

            updateTask.setDisable(false);
            deleteTask.setDisable(false);
        }
    }
    private final ListChangeListener<Task> selectorTablaPersonas =
            new ListChangeListener<Task>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Task> c) {
                    selectTask();
                }
            };

    public Task getTablaPersonasSeleccionada() {
        if (taskTable != null) {
            List<Task> tabla = taskTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Task task = tabla.get(0);
                return task;
            }
        }
        return null;
    }

    public void clean(){
        this.descriptionTask.setText("");

        deleteTask.setDisable(true);
        updateTask.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void generateComboBoxActivities(){
        ObservableList<Activity> comboActivity = FXCollections.observableArrayList();
        List<String> names = new ArrayList<>();
        for (Process process: this.processList.getProcessList()) {
            for (Activity activity: process.getActivities()) {
                if(!names.contains(activity.getName())) {
                    names.add(activity.getName());
                    comboActivity.add(activity);
                }
            }
        }
        comboBoxActivity.setItems(comboActivity);
    }

    public void generateComboBoxTime(){
        ObservableList<String> comboNumber = FXCollections.observableArrayList();
        for (int i = 1; i <=  60; i++){
            comboNumber.add(i+"");
        }
        comboBoxTime.setItems(comboNumber);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.processList = ProcessList.getInstance();

        generateComboBoxActivities();
        generateComboBoxTime();

        comboBoxTime.getSelectionModel().selectFirst();
        final ObservableList<Task> tableActivitySelect = taskTable.getSelectionModel().getSelectedItems();
        tableActivitySelect.addListener(selectorTablaPersonas);
        clean();
    }
}
