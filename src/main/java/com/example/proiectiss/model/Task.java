package com.example.proiectiss.model;

import java.time.LocalDateTime;

public class Task extends Identifiable<Long> {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private TaskType taskType;
    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }


    public Task(Long id, String name, String description, LocalDateTime deadline, TaskType taskType) {
        super(id);
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", taskType=" + taskType +
                ", worker=" + worker +
                '}';
    }
}
