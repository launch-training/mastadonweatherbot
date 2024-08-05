package com.acn.jive.mastadonweatherbot.persistence;

import com.acn.jive.mastadonweatherbot.weather.WeatherApiResponse;

import java.sql.*;
import java.util.UUID;

public class PostHistoryRepository {

    private Connection connection;

    public PostHistoryRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveWeatherApiResponse(WeatherApiResponse weatherApiResponse, Location location) throws SQLException {
        String guid = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.valueOf(weatherApiResponse.getRequestTimestamp());
        //todo: null check of json
        String jsonAsString = weatherApiResponse.getJsonObject().toString();
        Long locationId = location.getId();

        String sql = "INSERT INTO post_history(guid, timestamp_weather_request, weather_api_response, location_id) " +
                "VALUES(?,?,CAST(? AS json),?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, guid);
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setString(3, jsonAsString);
            preparedStatement.setLong(4, locationId);
            preparedStatement.executeUpdate();
        }

        //todo: exception handling
        //todo: return generated keys
    }


}
