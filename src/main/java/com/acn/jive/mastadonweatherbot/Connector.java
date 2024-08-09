package com.acn.jive.mastadonweatherbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final Logger logger = LogManager.getLogger();

    public Connection getConnection() throws SQLException {
        String dbUser = envOrDefault("db_user", "root");
        String dbPass = envOrDefault("db_pass", "");
        String dbUrl = envOrDefault("db_url", "jdbc:mysql://localhost:3306/mastodon");

        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(dbUrl, connectionProps);

        logger.info("Connected to database");

        return conn;
    }

    private String envOrDefault(String key, String dflt) {
        logger.debug("Searching for key: {}", key);

        String value = System.getenv(key);
        logger.debug("Searching for value: {}", value);

        if (value == null || value.isEmpty()) {
            logger.debug("Returning default: {}", dflt);
            return dflt;
        }
        return value;
    }
}
