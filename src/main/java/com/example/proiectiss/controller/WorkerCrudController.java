package com.example.proiectiss.controller;

import com.example.proiectiss.model.Worker;
import com.example.proiectiss.service.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WorkerCrudController implements Controller{
    private Worker selectedWorker;
    public AdminController adminController;

    public void setWorker(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
    }

    public void setAdminController(AdminController adminController){
        this.adminController = adminController;
    }
    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        if (selectedWorker != null) {
            nameTextField.setText(selectedWorker.getName());
            usernameTextField.setText(selectedWorker.getName());
            usernameTextField.setDisable(true);
            passwordTextField.setText(selectedWorker.getPassword());
        }
    }

    @Override
    public void update() {}

    public TextField nameTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;

    public void cancelButtonClicked() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void submitButtonClicked() {
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (name.equals("") || username.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Some fields are empty");
            alert.setContentText("Please fill in all the fields.");
            alert.show();
            return;
        }

        if (password.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password is too short");
            alert.setContentText("Please insert a longer password.");
            alert.show();
            return;
        }

        Worker worker;
        if (selectedWorker != null) {
            // Update mode
            selectedWorker.setName(name);
            selectedWorker.setPassword(password);
            service.updateWorker(selectedWorker);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Worker updated successfully");
            alert.show();
            adminController.update();
        } else {
            // Add mode
            worker = new Worker(null, username, password);
            worker.setStartedWorking(null);

            if (service.addWorker(worker) != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Worker added successfully");
                alert.show();
                service.removeObserver(this);
                adminController.update();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Something went wrong! Try again");
                alert.show();
                return;
            }
        }

        // Close the stage
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();

    }

}
