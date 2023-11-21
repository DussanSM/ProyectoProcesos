package com.example.proyectoprocesos.controller;

import com.example.proyectoprocesos.modelo.ProcessList;
import com.example.proyectoprocesos.modelo.Process;
import javafx.collections.FXCollections;
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
import javafx.collections.ListChangeListener;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProcessInterface implements Initializable {
    private Stage stage;

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

    @FXML
    private Button processTime;

    private ProcessList processList;
    private ObservableList<Process> itemList;
    private Process p;
    int positionProcess = 0;

    public void uploadTable(){
        itemList = FXCollections.observableArrayList(this.processList.getProcessList());
        tableProcess.setItems(itemList);

        TableColumn<Process, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Process, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.tableProcess.getColumns().setAll(idColumn, nameColumn);
    }

    public boolean validation(){
        if(nameProcess.getText().isEmpty() || idProcess.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return false;
        }
        return true;
    }

    @FXML
    void addProcess(ActionEvent ignoredEvent) {
        if(!validation()){
            return;
        }

        this.processList.addProcess(new Process(idProcess.getText(), nameProcess.getText()));
        this.uploadTable();
        clean();
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
    void deleteProcess(ActionEvent ignoredEvent) {
        this.processList.removeProcess(p);
        this.uploadTable();
        clean();
    }

    @FXML
    void updateProcess(ActionEvent ignoredEvent) {
        if(!validation()){
            return;
        }
        this.processList.update(p, new Process(idProcess.getText(), nameProcess.getText()));
        this.uploadTable();
        p = null;
        clean();
    }


    private final ListChangeListener<Process> selectTableProcess =
            c -> selectProcess();
    public Process getTableProcessSelect() {
        if (tableProcess != null) {
            List<Process> tabla = tableProcess.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                return tabla.get(0);
            }
        }
        return null;
    }

    private void selectProcess() {
        p = getTableProcessSelect();

        if (p != null) {
            positionProcess = itemList.indexOf(p);
            nameProcess.setText(p.getName());
            idProcess.setText(p.getId());

            update.setDisable(false);
            delete.setDisable(false);
            processTime.setDisable(false);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clean(){
        nameProcess.setText("");
        idProcess.setText("");
        update.setDisable(true);
        delete.setDisable(true);
        processTime.setDisable(true);
    }

    @FXML
    void addTime(ActionEvent ignoredActionEvent){
        openPopup();
    }
    private void openPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup Window");

        String time = p.getMinTime()+"";

        Label label = new Label("El tiempo minimo es: " + time);

        ComboBox<String> comboBoxTime = new ComboBox<>();
        ObservableList<String> comboNumber = FXCollections.observableArrayList();
        for (double i = p.calculateMinTime(); i <=  900; i++){
            comboNumber.add(i+"");
        }
        comboBoxTime.setItems(comboNumber);
        comboBoxTime.getSelectionModel().selectFirst();

        Button aceptButton = new Button("Aceptar");
        aceptButton.setOnAction(e ->  {
            p.setManualTime(true);
            p.setMinTime(Double.parseDouble(comboBoxTime.getValue()));
            popupStage.close();
        });

        Button closeButton = new Button("Cerar Popup");
        closeButton.setOnAction(e -> popupStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, comboBoxTime, aceptButton, closeButton);
        layout.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 200, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.processList = ProcessList.getInstance();
        this.uploadTable();
        final ObservableList<Process> tableProcessSelect = tableProcess.getSelectionModel().getSelectedItems();
        tableProcessSelect.addListener(selectTableProcess);

        clean();
    }
}
