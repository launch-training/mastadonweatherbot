package com.acn.jive.mastadonweatherbot.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {

    private Connection connection;

    public LocationRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Location> readAllActiveLocations() throws RepositoryException {
        List<Location> activeLocations = new ArrayList<>();
        String sql = "SELECT * FROM location WHERE active = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                BigDecimal latitude = resultSet.getBigDecimal("latitude");
                BigDecimal longitude = resultSet.getBigDecimal("longitude");
                Location location = new Location(latitude, longitude);
                activeLocations.add(location);
            }
            return activeLocations;
        } catch (SQLException ex) {
            throw new RepositoryException("An exception occurred while selecting active locations from the database", ex);
        }
    }

}
