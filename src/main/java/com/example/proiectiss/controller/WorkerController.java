package com.example.proiectiss.controller;

import com.example.proiectiss.model.Task;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WorkerController implements Controller {
    public ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    public TableView<Task> tasksTable;
    @FXML
    public TableColumn<Task, String> titleColumn;
    @FXML
    public TableColumn<Task, String> descriptionColumn;
    @FXML
    public TableColumn<Task, String> typeColumn;
    @FXML
    public TableColumn<Task, String> deadlineColumn;

    public Worker loggedWorker;
    public Service service;

    @Override
    public void update() {
        populateTasksTable();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initializeTasksTable();
    }

    public void populateTasksTable() {
        tasks.clear();
        tasks.setAll(service.getTasksForWorker(loggedWorker.getId()));
    }

    public void initializeTasksTable() {
        tasksTable.setItems(tasks);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("taskType"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
    }

    public void setLoggedWorker(Worker loggedWorker) {
        this.loggedWorker = loggedWorker;
        populateTasksTable();
    }

    public void logoutClicked() {
        service.removeObserver(this);
        service.stopWorking(loggedWorker.getId());
        Stage stage = (Stage) tasksTable.getScene().getWindow();
        stage.close();
    }

    public void markAsSolvedClicked() {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
        } else {
            service.markAsDone(selectedTask.getId());
            populateTasksTable();
        }
    }


}
