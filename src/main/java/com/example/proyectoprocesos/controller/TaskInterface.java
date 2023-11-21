package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskInterface implements Initializable {

    @FXML
    private ComboBox<Activity> comboBoxActivity;

    @FXML
    private ComboBox<String> comboBoxTime;

    @FXML
    private Button deleteTask;

    @FXML
    private Button addAnother;

    @FXML
    private TextField descriptionTask;

    @FXML
    private CheckBox mandatoryTask;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private Button updateTask;

    private String time = "1.0";
    private Stage stage;
    private ObservableList<Task> itemList;
    private Activity activity;
    private Task task;
    private ProcessList processList;
    int positionTask = 0;

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
        Queue<Task> tasks = this.activity.getTasks().clone();
        for (Task t: tasks) {
            itemList.add(t);
        }
        taskTable.setItems(itemList);

        Util<Task> t = new Util<>();

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Descripcion");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        t.columnSettings(descriptionColumn, 403);

        TableColumn<Task, String> mandatoryColumn = new TableColumn<>("Obligatorio");
        mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));
        t.columnSettings(mandatoryColumn, 100);

        TableColumn<Task, String> timeColumn = new TableColumn<>("Tiempo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
        t.columnSettings(timeColumn, 95);

        this.taskTable.getColumns().setAll(descriptionColumn, mandatoryColumn, timeColumn);
    }

    @FXML
    void addTask(ActionEvent ignoredEvent) {
        if (!validation()) {
            return;
        }

        this.activity.pushTask(new Task(descriptionTask.getText(), mandatoryTask.isSelected(), Double.parseDouble(time)));
        this.uploadTable();
        clean();
        this.processList.refreshTime();
    }

    @FXML
    public void update(){
        if(!validation()){
            return;
        }

        this.task.setDescription(descriptionTask.getText());
        this.task.setMandatory(mandatoryTask.isSelected());
        this.task.setTime(Double.parseDouble(time));
        this.uploadTable();
        clean();
        this.processList.refreshTime();
    }

    @FXML
    public void delete(){
        if(!validation()){
            return;
        }

        Queue<Task> taskQueue = this.activity.getTasks().clone();
        deleteTaskQueue(taskQueue);
        this.activity.setTasks(taskQueue);
        this.uploadTable();
        clean();
        this.processList.refreshTime();
    }

    public void deleteTaskQueue(Queue<Task> taskQueue){
        if (taskQueue.isEmpty()){
            return;
        }

        Task taskData = taskQueue.pop();
        if (taskData == task){
            return;
        }
        deleteTaskQueue(taskQueue);
        taskQueue.push(taskData);
    }
    @FXML
    void addTaskIndex(ActionEvent ignoredEvent) {
        if (!validation()) {
            return;
        }

        String index = JOptionPane.showInputDialog("Ingrese la posicion que desea agregar la tarea: ");
        this.activity.pushTask(new Task(descriptionTask.getText(), mandatoryTask.isSelected(), Double.parseDouble(time)),
                Integer.parseInt(index));
        this.uploadTable();
        clean();
    }

    @FXML
    public void selectActivity(){
        this.activity = comboBoxActivity.getValue();
        this.uploadTable();
        this.clean();
    }

    @FXML
    public void selectTime(){
        time = comboBoxTime.getValue();
    }

    @FXML
    public void addAnotherActivity(ActionEvent ignoredActionEvent){
        openPopup();
    }
    private void openPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");

        ComboBox<Activity> comboBoxPopup = new ComboBox<>();
        comboBoxPopup.setItems(generateComboBoxActivities());

        Button addButton = new Button("Agregar Tarea");
        addButton.setOnAction(e ->  {
            comboBoxPopup.getValue().pushTask(task);
            popupStage.close();
        });

        Button closeButton = new Button("Cerar Popup");
        closeButton.setOnAction(e -> popupStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(comboBoxPopup, addButton, closeButton);
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 200, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void selectTask() {
        task = getTaskTableSelect();

        if (task != null) {
            positionTask = itemList.indexOf(task);
            descriptionTask.setText(task.getDescription());
            mandatoryTask.setSelected(task.isMandatory());

            updateTask.setDisable(false);
            deleteTask.setDisable(false);
            addAnother.setDisable(false);
        }
    }
    private final ListChangeListener<Task> selectTaskTable =
            c -> selectTask();

    public Task getTaskTableSelect() {
        if (taskTable != null) {
            List<Task> tabla = taskTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                return tabla.get(0);
            }
        }
        return null;
    }

    public void clean(){
        this.descriptionTask.setText("");

        deleteTask.setDisable(true);
        updateTask.setDisable(true);
        addAnother.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

        comboBoxActivity.setItems(generateComboBoxActivities());
        generateComboBoxTime();

        comboBoxTime.getSelectionModel().selectFirst();
        ObservableList<Task> tableActivitySelect = taskTable.getSelectionModel().getSelectedItems();
        tableActivitySelect.addListener(selectTaskTable);
        clean();
    }
}
