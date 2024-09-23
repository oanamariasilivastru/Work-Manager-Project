package com.example.proiectiss;

import com.example.proiectiss.controller.LoginController;
import com.example.proiectiss.repository.AdminRepository;
import com.example.proiectiss.repository.TaskRepository;
import com.example.proiectiss.repository.WorkerRepository;
import com.example.proiectiss.service.Service;
import com.example.proiectiss.utils.JdbcUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Load properties from config file
        Properties jdbcProps = JdbcUtils.loadProperties("bd.config");
        // Create JdbcUtils instance
        JdbcUtils jdbcUtils = new JdbcUtils(jdbcProps);

        // Create repository instances
        WorkerRepository workerRepository = new WorkerRepository(jdbcUtils);
        AdminRepository adminRepository = new AdminRepository(jdbcUtils);
        TaskRepository taskRepository = new TaskRepository(jdbcUtils);

        Service service = new Service(adminRepository, workerRepository, taskRepository);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        VBox root = (VBox) loader.load();
        Stage dialogStage1 = new Stage();
        LoginController loginController = loader.getController();
        loginController.setService(service);

        Scene scene = new Scene(root);

        // Show the stage
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}