package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Task;
import com.example.proyectoprocesos.modelo.estructuraDatos.Queue;
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

public class SearchInterface implements Initializable {

    @FXML
    private TableView<Process> activityTable;

    @FXML
    private ComboBox<Activity> comboBoxActivity;

    @FXML
    private Button searchTaskButton;

    @FXML
    private ComboBox<String> searchTypeCombo;

    @FXML
    private TableView<Task> taskColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private Button backButton;

    @FXML
    private TextField word;

    @FXML
    private Label minTimeProcess;

    @FXML
    private Label maxTimeProcess;

    @FXML
    private Label minTimeActivity;

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
        this.minTimeActivity.setText(this.activity.calculateMinTime()+"");
        uploadTaskTable();

        searchTypeCombo.setDisable(false);
    }

    private final ListChangeListener<Process> selectTableProcess =
            c -> selectProcess();
    public Process getTableProcessSelect() {
        if (activityTable != null) {
            List<Process> tabla = activityTable.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                return tabla.get(0);
            }
        }
        return null;
    }
    private void selectProcess() {
        process = getTableProcessSelect();
        this.minTimeProcess.setText(this.process.calculateMinTime()+"");
        this.maxTimeProcess.setText(this.process.getMinTime()+"");
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

    @FXML
    public void searchTask(ActionEvent ignoredActionEvent){

        if(searchTypeCombo.getSelectionModel().isEmpty()){
            return;
        }

        ObservableList<Task> itemTaskSearch = FXCollections.observableArrayList();
        if(searchTypeCombo.getSelectionModel().isSelected(0)){
            if(!validationWord()){
                return;
            }

            for (Process p: processList.getProcessList()) {
                for (Activity a: p.getActivities()) {
                    searchTaskQueue(itemTaskSearch, a);
                }
            }
        }

        if(searchTypeCombo.getSelectionModel().isSelected(1)){
            if(!validationWord()){
                return;
            }
            searchTaskQueue(itemTaskSearch, activity);
        }

        if(searchTypeCombo.getSelectionModel().isSelected(2)){
            if(!validationWord()){
                return;
            }
            String name = JOptionPane.showInputDialog("Ingrese el nombre de la activida: ");
            Activity ac = null;
            for (Process process: this.processList.getProcessList()) {
                for (Activity a: process.getActivities()) {
                    if(name.equals(a.getName())) {
                        ac = a;
                    }
                }
            }

            if (ac == null){
                JOptionPane.showMessageDialog(null, "Esa actividad no existe");
                return;
            }

            searchTaskQueue(itemTaskSearch, ac);
        }

        uploadSearchTaskTable(itemTaskSearch);
    }

    public void searchTaskQueue(ObservableList<Task> i, Activity a){
        Queue<Task> tasks = a.getTasks().clone();
        for (Task t: tasks) {
            if(t.getDescription().contains(word.getText())){
                i.add(t);
            }
        }
    }
    public boolean validationWord(){
        if(word.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar una palabra para buscar");
            return false;
        }

        return true;
    }
    public void uploadSearchTaskTable(ObservableList<Task> itemsTable){
        this.taskTable.setItems(itemsTable);

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Descripcion");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Task, String> mandatoryColumn = new TableColumn<>("Obligatorio");
        mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

        TableColumn<Task, Double> timeColumn = new TableColumn<>("Tiempo");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
        this.taskTable.getColumns().setAll(descriptionColumn, mandatoryColumn, timeColumn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.processList = ProcessList.getInstance();
        comboBoxActivity.setItems(generateComboBoxActivities());

        final ObservableList<Process> tableProcessSelect = activityTable.getSelectionModel().getSelectedItems();
        tableProcessSelect.addListener(selectTableProcess);

        ObservableList<String> list = FXCollections.observableArrayList(
                "Buscar desde el inicio",
                "Desde la actividad actual",
                "Desde una actividad dado su nombre"
                );
        searchTypeCombo.setItems(list);
        searchTypeCombo.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
