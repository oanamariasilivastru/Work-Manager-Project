package com.example.proiectiss.service;

import com.example.proiectiss.model.Admin;
import com.example.proiectiss.model.LoginResponseType;
import com.example.proiectiss.model.Task;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.repository.AdminRepository;
import com.example.proiectiss.repository.TaskRepository;
import com.example.proiectiss.repository.WorkerRepository;
import com.example.proiectiss.utils.LoginResponse;
import com.example.proiectiss.utils.Observable;

import java.time.LocalDateTime;
import java.util.*;

public class Service extends Observable {
    private AdminRepository adminRepository;
    private WorkerRepository workerRepository;
    private TaskRepository taskRepository;

    public Service(AdminRepository adminRepository, WorkerRepository workerRepository, TaskRepository taskRepository){
        this.adminRepository = adminRepository;
        this.workerRepository = workerRepository;
        this.taskRepository = taskRepository;
    }

    public LoginResponse login(String username, String password) {
        LoginResponse response = new LoginResponse(LoginResponseType.FAILED, null);
        Admin adminFound = adminRepository.findByIdAndPassword(username, password);
        if (adminFound != null) {
            response.setType(LoginResponseType.ADMIN);
            response.setBody(adminFound);
        } else {
            Worker workerFound = workerRepository.findByIdAndPassword(username, password);
            startWorking(workerFound.getId());
            if (workerFound != null) {
                response.setType(LoginResponseType.WORKER);
                response.setBody(workerFound);
            }
        }
        notifyObservers();

        return response;
    }
    public Task addTask(Task task) {
        Task task1 = taskRepository.save(task);
        notifyObservers();
        return task1;
    }

    public void startWorking(Long id) {
        workerRepository.startWorking(id);
        notifyObservers();
    }

    public void stopWorking(Long id) {
        workerRepository.stopWorking(id);
        notifyObservers();
    }

    public void markAsDone(Long id) {
        taskRepository.markAsDone(id);
        notifyObservers();
    }

    public List<Worker> getAllAvailableWorkers() {
        return workerRepository.findAllAvailableWorkers();
    }

    public Worker addWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public void updateWorker(Worker worker) {
        workerRepository.updateWorker(worker);
    }

    public List<Task> getTasksForWorker(Long id) {
        return taskRepository.findAllByWorkerId(id);
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Long getIdForTask() {
        List<Task> allTasks = taskRepository.findAll();

        if (allTasks.isEmpty()) {
            return 1L; // If there are no tasks, start IDs from 1
        } else {
            Task lastTask = Collections.max(allTasks, Comparator.comparing(Task::getId));
            return lastTask.getId() + 1; // Return the ID of the last task incremented by 1
        }
    }

    public Worker findByUsernameAndPassword(String username, String password) {
        return workerRepository.findByNameAndPassword(username, password);
    }


}
