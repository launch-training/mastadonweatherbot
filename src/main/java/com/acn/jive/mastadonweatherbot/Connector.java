package com.acn.jive.mastadonweatherbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    public Connection getConnection() throws SQLException {

        String dbUser = System.getenv("db_user");
        String dbPass = System.getenv("db_pass");

        if (dbUser == null) {
            dbUser = "root";
        }
        if (dbPass == null) {
            dbPass = "";
        }

        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUser);
        connectionProps.put("password", dbPass);

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mastodon",
                connectionProps);

        System.out.println("Connected to database");
        return conn;
    }
}
