    module com.example.proyectoprocesos {
    requires javafx.controls;
    requires javafx.fxml;
        requires java.desktop;


        opens com.example.proyectoprocesos to javafx.fxml;
    exports com.example.proyectoprocesos;
        exports com.example.proyectoprocesos.controller;
        opens com.example.proyectoprocesos.controller to javafx.fxml;
        opens com.example.proyectoprocesos.modelo to javafx.base;
        opens com.example.proyectoprocesos.modelo.estructuraDatos to javafx.base;
    }