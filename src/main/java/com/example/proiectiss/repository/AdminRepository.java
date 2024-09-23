package com.example.proiectiss.repository;

import com.example.proiectiss.model.Admin;
import com.example.proiectiss.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class AdminRepository {

    private JdbcUtils jdbcUtils;

    public AdminRepository(JdbcUtils jdbcUtils){
        this.jdbcUtils = jdbcUtils;
    }

    public Admin findByIdAndPassword(String username, String password) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Admin WHERE username = ? AND password = ?\n")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return null;
    }
}
