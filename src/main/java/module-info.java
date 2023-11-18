    module com.example.proyectoprocesos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectoprocesos to javafx.fxml;
    exports com.example.proyectoprocesos;
}