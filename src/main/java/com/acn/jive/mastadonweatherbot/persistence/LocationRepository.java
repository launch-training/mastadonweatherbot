package com.acn.jive.mastadonweatherbot.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private static final Logger logger = LogManager.getLogger();

    private final Connection connection;

    public LocationRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Location> readAllActiveLocations() throws RepositoryException {
        List<Location> activeLocations = new ArrayList<>();
        String sql = "SELECT * FROM location WHERE active = 1";
        //logger.debug("Executing sql: " + sql);
        logger.debug("Executing sql: {}", sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                BigDecimal latitude = resultSet.getBigDecimal("latitude");
                BigDecimal longitude = resultSet.getBigDecimal("longitude");
                Location location = new Location(id, latitude, longitude);

                logger.debug("Found result with id {}, long/lat: {}/{}", id, longitude, latitude);

                activeLocations.add(location);

            }

            // Is this really necessary? Bloated code
//            if (activeLocations.isEmpty()) {
//                logger.trace("Active locations is still empty, no results");
//            }

            return activeLocations;
        } catch (SQLException ex) {
            throw new RepositoryException("An exception occurred while selecting active locations from the database", ex);
        }
    }
}
