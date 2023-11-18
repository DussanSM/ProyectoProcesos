package com.example.proyectoprocesos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.swing.JOptionPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    private Stage stage;
    @FXML
    private Label welcomeText;

    @FXML
    private Button login;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String name = this.name.getText();
        String password = this.password.getText();
        if(password.isEmpty() && name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un usuario y/o password");
            return;
        }

        if(!name.equals("admin") && !password.equals("admin")){
            JOptionPane.showMessageDialog(null, "Usuario y/o password incorrectos");
            return;
        }

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uniquindio/biblioteca/login-bibliotecario.fxml"));
//        Parent root = loader.load();
//        InicioBibliotecario controller = loader.getController();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
//        controller.setStage(stage);
//        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}