package com.uay.aws.service;

import com.uay.aws.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcService {
    private static final String INSERT_INTO_EVENTS = "INSERT INTO events(name, data, version) VALUES(?, ?, ?)";
    private static final String SELECT_LAST_VERSION_FROM_EVENTS = "SELECT max(version) FROM events WHERE name=?";
    private static final String CONNECTION_URL = "jdbc:mysql://" + Constants.DB_HOST + ":" + Constants.DB_PORT + "/" + Constants.DB_NAME;

    private Connection connection = getDbConnection();

    public JdbcService() {
    }

    private Connection getDbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONNECTION_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void persistEvent(String key, String data, long version) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_EVENTS);
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, data);
        preparedStatement.setLong(3, version + 1);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public long getEventVersion(String key) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_VERSION_FROM_EVENTS);
        preparedStatement.setString(1, key);

        long version = 0;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            version = resultSet.getLong(1);
        }
        resultSet.close();
        preparedStatement.close();
        return version;
    }

    public void release() throws SQLException {
        connection.close();
    }
}
