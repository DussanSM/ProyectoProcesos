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
    private TableView<Activity> tableActivity;

    @FXML
    private Button update;

    private Process process;
    private ProcessList processList;
    private Stage stage;
    private ObservableList<Activity> itemList;
    private Activity a;
    int positionActivity = 0;

    public void uploadTable(){
        itemList = FXCollections.observableArrayList();
        for (Activity a: this.process.getActivities()) {
            itemList.add(a);
        }
        tableActivity.setItems(itemList);

        TableColumn<Activity, String> nameColumn = new TableColumn<>("Actividad");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Activity, String> descriptionColumn = new TableColumn<>("Descripcion");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Activity, String> mandatoryColumn = new TableColumn<>("Obligatorio");
        mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

        this.tableActivity.getColumns().setAll(nameColumn, descriptionColumn, mandatoryColumn);
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
        if(nameActivity.getText().isEmpty() || descriptionActivity.getText().isEmpty() || processComboBox.getValue().getName().isEmpty()){
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
        this.uploadTable();
    }

    private final ListChangeListener<Activity> selectorTablaPersonas =
            new ListChangeListener<Activity>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Activity> c) {
                    selectActivity();
                }
    };

    public Activity getTablaPersonasSeleccionada() {
        if (tableActivity != null) {
            List<Activity> tabla = tableActivity.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Activity activity = tabla.get(0);
                return activity;
            }
        }
        return null;
    }

    @FXML
    void deleteActivity(ActionEvent event) {
        this.process.getActivities().removeNode(a);
        this.uploadTable();
        clean();
    }

    @FXML
    void updateProcess(ActionEvent event) {
        if(!validation()){
            return;
        }
        Activity ac = this.process.getActivities().getNodeValue(positionActivity);
        ac.setName(nameActivity.getText());
        ac.setDescription(descriptionActivity.getText());
        ac.setMandatory(mandatory.isSelected());
        this.uploadTable();
        a = null;
        clean();
    }

    public void clean(){
        nameActivity.setText("");
        descriptionActivity.setText("");
        mandatory.setSelected(false);
        update.setDisable(true);
        delete.setDisable(true);
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
        final ObservableList<Activity> tableActivitySelect = tableActivity.getSelectionModel().getSelectedItems();
        tableActivitySelect.addListener(selectorTablaPersonas);

        clean();
    }

}
