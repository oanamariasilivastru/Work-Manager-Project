package com.example.proiectiss.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties jdcProps;
    public JdbcUtils(Properties props){
        jdcProps = props;
    }
    private Connection instance = null;

    private Connection getNewConnection() {
        String url = jdcProps.getProperty("jdbc.url");
        String user = jdcProps.getProperty("jdbc.user");
        String pass = jdcProps.getProperty("jdbc.pass");
        Connection con = null;
        try {
            if (user != null && pass != null)
                con = DriverManager.getConnection(url, user, pass);
            else
                con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting connection", e);
        }
        return con;
    }

    public Connection getConnection(){
        try{
            if(instance ==null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting connection", e);
        }
        return instance;
    }

    public static Properties loadProperties(String filePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
