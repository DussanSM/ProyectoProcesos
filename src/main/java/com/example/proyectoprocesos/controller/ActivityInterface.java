package com.example.proyectoprocesos.controller;


import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.estructuraDatos.DoubleList;
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
import java.util.List;
import java.util.ResourceBundle;

public class ActivityInterface implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private TextField descriptionActivity;

    @FXML
    private CheckBox mandatory;

    @FXML
    private TextField nameActivity;

    @FXML
    private ComboBox<Process> processComboBox;

    @FXML
    private TableView<DoubleList<Activity>> tableActivity;

    @FXML
    private Button update;

    private Process process;
    private ProcessList processList;
    private Stage stage;
    private ObservableList<DoubleList<Activity>> itemList;
    private Activity a;
    int positionActivity = 0;

    public void uploadTable(){

        if(this.process.getActivities().isEmpty()){
            return;
        }
        itemList = FXCollections.observableArrayList(this.process.getActivities());
        tableActivity.setItems(itemList);

        TableColumn<DoubleList<Activity>, String> nameColumn = new TableColumn<>("Actividad");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DoubleList<Activity>, String> descriptionColumn = new TableColumn<>("Descripción");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.tableActivity.getColumns().setAll(nameColumn, descriptionColumn);
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

    public boolean validation(){
        if(nameActivity.getText().isEmpty() && descriptionActivity.getText().isEmpty() && processComboBox.getValue() != null){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return false;
        }
        return true;
    }

    @FXML
    void addActivity(ActionEvent event) {
        if (!validation()) {
            return;
        }

        this.process.addActivityEnd(new Activity(nameActivity.getText(), descriptionActivity.getText(), mandatory.isSelected()));
        this.uploadTable();
    }

    @FXML
    void selectProcess(ActionEvent event){
        process = processComboBox.getValue();
    }

    private final ListChangeListener<DoubleList<Activity>> selectorTablaPersonas =
            new ListChangeListener<DoubleList<Activity>>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends DoubleList<Activity>> c) {
                    selectActivity();
                }
    };

    public Activity getTablaPersonasSeleccionada() {
        if (tableActivity != null) {
            ObservableList<DoubleList<Activity>> tabla = tableActivity.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Activity activity = tabla.get(0).getNodeValue(0);
                return activity;
            }
        }
        return null;
    }

    private void selectActivity() {
        a = getTablaPersonasSeleccionada();

        if (a != null) {
            positionActivity = itemList.indexOf(a);
            nameActivity.setText(a.getName());
            descriptionActivity.setText(a.getDescription());
            mandatory.setSelected(a.isMandatory());

            update.setDisable(false);
            delete.setDisable(false);
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.processList = ProcessList.getInstance();
        ObservableList<Process> comboProcess = FXCollections.observableArrayList(processList.getProcessList());
        processComboBox.setItems(comboProcess);
        //this.uploadTable();
        final ObservableList<DoubleList<Activity>> tableActivitySelect = tableActivity.getSelectionModel().getSelectedItems();
        tableActivitySelect.addListener(selectorTablaPersonas);

        update.setDisable(true);
        delete.setDisable(true);
    }

}
