package com.example.proyectoprocesos.controller;
import javafx.scene.control.TableColumn;

public class Util <T>{
    public void columnSettings(TableColumn<T, String> column, double size){
        column.setResizable(false);
        column.setReorderable(false);
        column.setEditable(false);
        column.setMinWidth(size);
        column.setStyle("-fx-alignment: CENTER;");
    }
}
