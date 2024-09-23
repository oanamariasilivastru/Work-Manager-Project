package com.example.proiectiss.repository;

import com.example.proiectiss.model.Worker;
import com.example.proiectiss.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerRepository {
    private  JdbcUtils jdbcUtils;

    public WorkerRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    public Worker findByIdAndPassword(String username, String password) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Worker WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Worker(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }

    public void startWorking(Long id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE Worker SET started_working = CURRENT_TIMESTAMP WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
    }

    public void stopWorking(Long id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE Worker SET started_working = NULL WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
    }


    public List<Worker> findAllAvailableWorkers() {
        List<Worker> workers = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Worker WHERE started_working IS NOT NULL")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                Worker worker = new Worker(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                workers.add(worker);
            }
        } catch (SQLException e) {
            System.out.println("In findallAvailableWorkers");
            System.out.println("Error DB " + e);
        }
        return workers;
    }

    public Worker save(Worker worker) {
        Connection con = jdbcUtils.getConnection();
        String query = "INSERT INTO Worker (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getPassword());
            preparedStatement.executeUpdate();

            // Retrieve the generated keys
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    worker.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating worker failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
            return null; // Return null if saving fails
        }
        return worker; // Return the saved worker object with the generated id
    }

    public List<Worker> findAll() {
        List<Worker> workers = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Worker";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Worker worker = new Worker(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")

                );
                if(rs.getTimestamp("started_working") != null)
                    worker.setStartedWorking(rs.getTimestamp("started_working").toLocalDateTime());
                workers.add(worker);
            }
        } catch (SQLException e) {
            System.out.println("In findallWorker");
            System.out.println("Error DB " + e);
        }
        return workers;
    }

    public void deleteById(Long id) {
        Connection con = jdbcUtils.getConnection();
        String query = "DELETE FROM Worker WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
    }
    public Worker findByNameAndPassword(String name, String password) {
        Connection con = jdbcUtils.getConnection();
        String query = "SELECT * FROM Worker WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Worker(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("In findbyNameandPasswordWorker");
            System.out.println("Error DB " + e);
        }
        return null;
    }

    public boolean updateWorker(Worker worker) {
        String query = "UPDATE Worker SET username = ?, password = ? WHERE id = ?";
        try (Connection con = jdbcUtils.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, worker.getName());
            preparedStatement.setString(2, worker.getPassword());
            preparedStatement.setLong(3, worker.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
            return false; // Return false if the update fails
        }
    }

    public Worker findById(Long id) {
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