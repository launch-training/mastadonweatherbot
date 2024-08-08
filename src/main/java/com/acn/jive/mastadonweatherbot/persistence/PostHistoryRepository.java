package com.acn.jive.mastadonweatherbot.persistence;

import com.acn.jive.mastadonweatherbot.mastodon.MastodonPost;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiResponse;
import java.sql.*;
import java.util.UUID;

public class PostHistoryRepository {

    private final Connection connection;

    public PostHistoryRepository(Connection connection) {
        this.connection = connection;
    }

    public Long saveWeatherApiResponse(WeatherApiResponse weatherApiResponse, Location location) throws RepositoryException {
        String guid = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.valueOf(weatherApiResponse.getRequestTimestamp());
        String jsonAsString = weatherApiResponse.getJsonObject().toString();
        Long locationId = location.getId();
        String sql = "INSERT INTO post_history(guid, timestamp_weather_request, weather_api_response, location_id) " +
                "VALUES(?,?,CAST(? AS json),?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, guid);
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setString(3, jsonAsString);
            preparedStatement.setLong(4, locationId);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException ex) {
            throw new RepositoryException("An exception occurred while saving the weather api response to the database", ex);
        }
    }

    public void saveMastodonPostInfo(MastodonPost mastodonPost, Long id) throws RepositoryException {
        Timestamp timestamp = Timestamp.valueOf(mastodonPost.getPostTimestamp());
        String postLink = mastodonPost.getPostLink();
        String sql = "UPDATE post_history SET timestamp_mastodon_posted = ?, post_link = ?, icon_url = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setString(2, postLink);
            preparedStatement.setString(3, mastodonPost.getIconUrl());
            preparedStatement.setString(4, mastodonPost.getDescription());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RepositoryException("An exception occurred while saving the mastodon post info to the database", ex);
        }
    }
}
