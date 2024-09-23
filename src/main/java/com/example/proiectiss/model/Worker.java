package com.example.proiectiss.model;

import java.time.LocalDateTime;
import java.util.List;

public class Worker extends Employee {
    private List<Task> tasks;
    private LocalDateTime startedWorking;


    public Worker(Long id, String username, String password) {
        super(id, username, password);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDateTime getStartedWorking() {
        return startedWorking;
    }

    public void setStartedWorking(LocalDateTime startedWorking) {
        this.startedWorking = startedWorking;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id= " + this.getId() +
                "password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
