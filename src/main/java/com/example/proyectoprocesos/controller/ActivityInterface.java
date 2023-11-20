package com.example.proyectoprocesos.controller;


import com.example.proyectoprocesos.modelo.Activity;
import com.example.proyectoprocesos.modelo.Process;
import com.example.proyectoprocesos.modelo.ProcessList;
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
import java.util.List;
import java.util.ResourceBundle;

public class ActivityInterface implements Initializable {
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
    private Stage stage;
    private ObservableList<Activity> itemList;
    private Activity a;
    private ProcessList processList;
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
        if(nameActivity.getText().isEmpty() || descriptionActivity.getText().isEmpty() || processComboBox.getValue().getName().isEmpty()){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return false;
        }
        return true;
    }

    @FXML
    void addActivity(ActionEvent ignoredEvent) {
        if (!validation()) {
            return;
        }

        int size = this.process.getActivities().getSize();
        this.process.addActivityEnd(new Activity(nameActivity.getText(), descriptionActivity.getText(), mandatory.isSelected()));

        if (size == this.process.getActivities().getSize()){
            JOptionPane.showMessageDialog(null, "El nombre ya existe");
            return;
        }

        this.uploadTable();
        clean();
    }

    @FXML
    void addActivityName(ActionEvent ignoredEvent) {
        if (!validation()) {
            return;
        }

        int size = this.process.getActivities().getSize();
        String name = JOptionPane.showInputDialog("Ingrese el nombre de la Actividad: ");
        this.process.addActivity(new Activity(nameActivity.getText(), descriptionActivity.getText(), mandatory.isSelected()), name);

        if (size == this.process.getActivities().getSize()){
            JOptionPane.showMessageDialog(null, "El nombre ya existe");
            return;
        }
        this.uploadTable();
        clean();
    }

    @FXML
    void addActivityRecent(ActionEvent ignoredEvent) {
        if (!validation()) {
            return;
        }

        int size = this.process.getActivities().getSize();
        this.process.addActivityRecent(new Activity(nameActivity.getText(), descriptionActivity.getText(), mandatory.isSelected()));
        if (size == this.process.getActivities().getSize()){
            JOptionPane.showMessageDialog(null, "El nombre ya existe");
            return;
        }
        this.uploadTable();
        clean();
    }

    @FXML
    void selectProcess(ActionEvent ignoredEvent){
        process = processComboBox.getValue();
        this.uploadTable();
        this.clean();
    }

    private final ListChangeListener<Activity> selectorTablaPersonas =
            c -> selectActivity();

    public Activity getTablaPersonasSeleccionada() {
        if (tableActivity != null) {
            List<Activity> tabla = tableActivity.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                return tabla.get(0);
            }
        }
        return null;
    }

    @FXML
    void deleteActivity(ActionEvent ignoredEvent) {
        this.process.getActivities().removeNode(a);
        this.uploadTable();
        clean();
    }

    @FXML
    void updateActivity(ActionEvent ignoredEvent) {
        if(!validation() && !this.process.onlyNameEdit(nameActivity.getText())){
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
        processList = ProcessList.getInstance();
        ObservableList<Process> comboProcess = FXCollections.observableArrayList(processList.getProcessList());
        processComboBox.setItems(comboProcess);
        final ObservableList<Activity> tableActivitySelect = tableActivity.getSelectionModel().getSelectedItems();
        tableActivitySelect.addListener(selectorTablaPersonas);

        clean();
    }

    @FXML
    public void addOtherProcess(ActionEvent ignoredActionEvent){
        openPopup();
    }
    private void openPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");

        ComboBox<Process> comboBoxPopup = new ComboBox<>();
        ObservableList<Process> comboProcess = FXCollections.observableArrayList(processList.getProcessList());
        comboBoxPopup.setItems(comboProcess);

        Button addButton = new Button("Agregar Tarea");
        addButton.setOnAction(e ->  {
            comboBoxPopup.getValue().addActivityEnd(a);
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
}