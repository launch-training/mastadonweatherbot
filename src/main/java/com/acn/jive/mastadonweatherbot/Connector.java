package com.acn.jive.mastadonweatherbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    public Connection getConnection() throws SQLException {
        String dbUser = envOrDefault("db_user", "root");
        String dbPass = envOrDefault("db_pass", "");
        String dbUrl = envOrDefault("db_url", "jdbc:mysql://localhost:3306/mastodon");

        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(dbUrl, connectionProps);

        System.out.println("Connected to database");
        return conn;
    }

    private String envOrDefault(String key, String dflt) {
        System.out.println("Searching for key " + key);
        String value = System.getenv(key);
        System.out.println("Found value " + value);
        if (value == null || value.isEmpty()) {
            System.out.println("Returning default " + dflt);
            return dflt;
        }
        return value;
    }
}
