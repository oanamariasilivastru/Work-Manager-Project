package com.example.proiectiss.controller;

import com.example.proiectiss.model.Admin;
import com.example.proiectiss.model.LoginResponseType;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.service.Service;
import com.example.proiectiss.utils.Functions;
import com.example.proiectiss.utils.LoginResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements Controller {
    public Label addedText;
    public TextField usernameText;
    public PasswordField passwordText;

    private Service service;


    public void setService(Service service) {
        this.service = service;
    }

    public void onLoginButtonClick() throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        LoginResponse loginResponse = service.login(username, password);
        if (loginResponse.getType() == LoginResponseType.FAILED) {
            addedText.setText("Credentials are invalid");
        } else if (loginResponse.getType() == LoginResponseType.ADMIN) {
            System.out.println("Aici suntem Admin");
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin-window.fxml"));
                Parent root = loader.load();

                AdminController adminController = loader.getController();
                adminController.setService(service);
                //adminController.setLoggedAdmin((Admin) loginResponse.getBody());

                stage.setTitle("Admin Window");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (loginResponse.getType() == LoginResponseType.WORKER) {
            System.out.println("Aici suntem WORKER");
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/worker-window.fxml"));
                Parent root = loader.load();

                WorkerController workerController = loader.getController();
                workerController.setService(service);
                workerController.setLoggedWorker((Worker) loginResponse.getBody());


                stage.setTitle("Worker Window");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            addedText.setText("Credentials are invalid");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) addedText.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update() {}
}