package com.example.proiectiss.controller;

import com.example.proiectiss.model.Task;
import com.example.proiectiss.model.TaskType;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.service.Service;
import com.example.proiectiss.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.LocalDateTime;

public class TaskCrudController implements Controller, Observer {
    ObservableList<TaskType> tasksType = FXCollections.observableArrayList();

    public TextField taskTitleTextEdit;
    public TextArea descriptionTextArea;
    public DatePicker deadlineDatePicker;
    public ComboBox<TaskType> taskTypeComboBox;

    private Worker loggedWorker;
    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        taskTypeComboBox.setItems(tasksType);
        tasksType.setAll(TaskType.values());
    }

    public void setLoggedWorker(Worker loggedWorker) {
        this.loggedWorker = loggedWorker;
    }

    public void createTaskClicked() {
        String title = taskTitleTextEdit.getText();
        String description = descriptionTextArea.getText();
        TaskType taskType = taskTypeComboBox.getValue();
        LocalDateTime deadline = deadlineDatePicker.getValue().atStartOfDay();

        if (title.isEmpty() || description.isEmpty() || taskType == null || ! deadline.isAfter(LocalDateTime.now()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("All fields must be filled with a valid value");
            alert.showAndWait();

            return;
        }

        Task task = new Task(service.getIdForTask(), title, description, deadline, taskType);
        task.setWorker(loggedWorker);
        System.out.println("In controller:" + task.toString());
        Task task1 = service.addTask(task);

        if (task1 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Task could not be created");
            alert.showAndWait();

            return;
        }

        service.removeObserver(this);
        taskTitleTextEdit.getScene().getWindow().hide();
    }

    @Override
    public void update() {}
}
