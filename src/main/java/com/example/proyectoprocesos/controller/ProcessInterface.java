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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ObservableList<Process> itemList;
    private Process p;
    int positionProcess = 0;

    public void chargeTable(){
        itemList = FXCollections.observableArrayList(this.processList.getProcessList());
        tableProcess.setItems(itemList);

        TableColumn<Process, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Process, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.tableProcess.getColumns().setAll(idColumn, nameColumn);
    }

    public boolean validation(){
        if(nameProcess.getText().isEmpty() && idProcess.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Llene todo los campos");
            return false;
        }
        return true;
    }

    @FXML
    void addProcess(ActionEvent event) {
        if(!validation()){
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
        this.processList.removeProcess(p);
        this.chargeTable();
    }

    @FXML
    void updateProcess(ActionEvent event) {
        if(!validation()){
            return;
        }
        this.processList.update(p, new Process(idProcess.getText(), nameProcess.getText()));
        this.chargeTable();
    }


    private final ListChangeListener<Process> selectorTablaPersonas =
            new ListChangeListener<Process>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Process> c) {
                    selectProcess();
                }
            };
    public Process getTablaPersonasSeleccionada() {
        if (tableProcess != null) {
            List<Process> tabla = tableProcess.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Process process = tabla.get(0);
                return process;
            }
        }
        return null;
    }

    private void selectProcess() {
        p = getTablaPersonasSeleccionada();

        if (p != null) {
            positionProcess = itemList.indexOf(p);
            nameProcess.setText(p.getName());
            idProcess.setText(p.getId());

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
        this.chargeTable();
        final ObservableList<Process> tableProcessSelect = tableProcess.getSelectionModel().getSelectedItems();
        tableProcessSelect.addListener(selectorTablaPersonas);

        update.setDisable(true);
        delete.setDisable(true);
    }
}
