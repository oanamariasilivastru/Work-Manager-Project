package com.example.proiectiss.controller;

import com.example.proiectiss.model.Admin;
import com.example.proiectiss.model.Task;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.service.Service;
import com.example.proiectiss.utils.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController implements Controller, Observer {

    public TableView<Task> taskTable;
    public TableColumn<Task, String> taskColumn;
    public TableColumn<Task, String> workerColumn;
    public TableColumn<Task, String> deadlineColumn;

    public TableView<Worker> workersTable;
    public TableColumn<Worker, String> nameColumn;
    public TableColumn<Worker, String> startedWorkingColumn;

    public Admin loggedAdmin;

    ObservableList<Worker> workers = FXCollections.observableArrayList();
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    private Service service;

    @Override
    public void update() {
        Platform.runLater(() -> {
            populateTasksTable();
            populateWorkersTable();
        });

    }

    @Override
    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initializeTables();
        populateWorkersTable();
        populateTasksTable();


    }

    private void initializeTables() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startedWorkingColumn.setCellValueFactory(new PropertyValueFactory<>("startedWorking"));
        workersTable.setItems(workers);

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorker().getName()));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTable.setItems(tasks);

    }

    private void populateTasksTable() {
        tasks.clear();
        tasks.setAll(service.getAllTasks());
    }

    private void populateWorkersTable() {
        workers.clear();
        workers.setAll(service.getAllAvailableWorkers());
    }

    public void createWorkerClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/worker-detalies.fxml"));
        Parent root = loader.load();

        WorkerCrudController ctrl = loader.getController();
        ctrl.setService(service);
        ctrl.setAdminController(this);
        Stage stage = new Stage();
        stage.setTitle("Create Worker");
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void updateWorkerClicked() throws IOException {
        Worker selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to update");
            alert.showAndWait();
            return;
        }

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/worker-detalies.fxml"));
        Parent root = loader.load();

        // Get the controller from the FXML loader
        WorkerCrudController ctrl = loader.getController();
        ctrl.setWorker(selectedWorker);
        ctrl.setService(service);
        ctrl.setAdminController(this);

        // Set up the new stage
        Stage stage = new Stage();
        stage.setTitle("Update Worker");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void deleteWorkerClicked() {
        Worker selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to delete");
            alert.showAndWait();
            return;
        }

        service.deleteWorker(selectedWorker.getId());
        populateWorkersTable();
    }

    public void seeAllWorkersClicked() {
        workers.clear();
        workers.setAll(service.getAllWorkers());
    }

    public void seeAllAvailableWorkers() {
        workers.clear();
        workers.setAll(service.getAllAvailableWorkers());
    }

    public void assignTasksClicked() throws IOException {
        Worker selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to assign the task");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Assign task");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/task-detalies.fxml"));
        Parent root = loader.load();
        Controller ctrl = loader.getController();
        ctrl.setService(service);
        ((TaskCrudController) ctrl).setLoggedWorker(selectedWorker);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logoutButtonClicked() {
        service.removeObserver(this);
        Stage stage = (Stage) workersTable.getScene().getWindow();
        stage.close();
    }

    public void deleteTasksClicked() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            return;
        }

        service.markAsDone(selectedTask.getId());
        populateTasksTable();
    }


}
