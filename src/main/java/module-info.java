module com.example.proiectiss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.proiectiss.controller;
    opens com.example.proiectiss to javafx.fxml;
    exports com.example.proiectiss;
    opens com.example.proiectiss.model to javafx.base;
}