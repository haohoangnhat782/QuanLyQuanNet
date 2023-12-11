package DAO;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PoolConnection {
    private static final String SERVER = "HUUHOANG";
    private static final String DATABASE_NAME = "NetCF";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "310303";
    private static final int MAX_CONNECTION = 10;
    private List<Connection> connections = new LinkedList<>();

    private Connection createConnection(){
        try {
            String url = String
                    .format("jdbc:sqlserver://%s;databaseName=%s;trustServerCertificate=true;encrypt=true;", SERVER, DATABASE_NAME);
            return DriverManager.getConnection(url, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public synchronized Connection getConnection() throws SQLException {

        Connection connection = null;
        if (connections.size() > 0) {
            connection = connections.remove(0);
        } else {
           connection = createConnection();
        }
        return connection;
    }
    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection==null)
            return;
        if (connections.size() < MAX_CONNECTION) {
            connections.add(connection);
        } else {
            connection.close();
        }
    }
    public void closeAllConnections() throws SQLException {
        for (Connection connection : connections) {
            connection.close();
        }
        connections.clear();
    }

}
