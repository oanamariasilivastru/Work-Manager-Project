package com.example.proiectiss.repository;

import com.example.proiectiss.model.Task;
import com.example.proiectiss.model.TaskType;
import com.example.proiectiss.model.Worker;
import com.example.proiectiss.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private JdbcUtils jdbcUtils;

    public TaskRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    public Task save(Task task) {
        Connection con = jdbcUtils.getConnection();
        String query = "INSERT INTO Task (name, description, task_type, deadline, worker_id, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getTaskType().name());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(task.getDeadline()));
            preparedStatement.setLong(5, task.getWorker().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("In save la task");
            System.out.println("Error DB " + e);
        }
        return task;
    }


    public void delete(Long id) {
        Connection con = jdbcUtils.getConnection();
        String query = "DELETE FROM Task WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
    }

    public Task findById(Long id) {
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Task WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Task(
                        Long.valueOf(rs.getLong("id")), // Convert primitive long to Long object
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("deadline").toLocalDateTime(),
                        TaskType.valueOf(rs.getString("task_type"))

                );


            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }


    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Task WHERE status = 'ACTIVE'";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long workerId = rs.getLong("worker_id");
                Worker worker = findWorkerById(workerId);

                Task task = new Task(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("deadline").toLocalDateTime(),
                        TaskType.valueOf(rs.getString("task_type"))
                );
                task.setWorker(worker);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("in findAllTask");
            System.out.println("Error DB " + e);
        }
        return tasks;
    }
    public void markAsDone(Long id) {
        Connection con = jdbcUtils.getConnection();
        String query = "UPDATE Task SET STATUS = 'FINISHED' WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
    }

    public List<Task> findAllByWorkerId(Long workerId) {
        List<Task> tasks = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Task WHERE worker_id = ? AND STATUS = 'ACTIVE'";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, workerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("deadline").toLocalDateTime(),
                        TaskType.valueOf(rs.getString("task_type"))
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return tasks;
    }

    public Worker findWorkerById(Long id) {
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Worker WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Worker(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("In findByIdWorker");
            System.out.println("Error DB " + e);
        }
        return null;
    }

}

