package com.acn.jive.mastadonweatherbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    public Connection getConnection() throws SQLException {
        String dbUser = envOrDefault("db_user", "root");
        String dbPass = envOrDefault("db_pass", "");

        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mastodon",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }

    private String envOrDefault(String key, String dflt) {
        String value = System.getenv(key);
        if (value == null) {
            return dflt;
        }
        return value;
    }
}
