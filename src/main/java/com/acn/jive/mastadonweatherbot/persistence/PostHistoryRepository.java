package com.acn.jive.mastadonweatherbot.persistence;

import java.sql.Connection;

public class PostHistoryRepository {

    private Connection connection;

    public PostHistoryRepository(Connection connection) {
        this.connection = connection;
    }



}
