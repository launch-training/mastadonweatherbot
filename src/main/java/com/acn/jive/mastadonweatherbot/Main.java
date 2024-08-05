package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.http.HTTPConnection;
import com.acn.jive.mastadonweatherbot.mastodon.PostStatus;
import com.acn.jive.mastadonweatherbot.persistence.Location;
import com.acn.jive.mastadonweatherbot.persistence.LocationRepository;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Main {

    public void run(Connection connection) {
        HTTPConnection HTTPConnection = new HTTPConnection();
        WeatherApiService weatherApiService = new WeatherApiService();
        PostStatus postStatus = new PostStatus();
        LocationRepository locationRepository = new LocationRepository(connection);

        try {
            List<Location> locations = locationRepository.readAllActiveLocations();
            for (Location location : locations) {
                Optional<Weather> weather = weatherApiService.readWeatherDataByCoordinates(location, HTTPConnection);
                postStatus.execute(weather.get());
            }

//            Optional<Weather> weather = weatherApiService.readWeatherDataByCity("Augsburg", HTTPConnection);
//            postStatus.execute(weather.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        Connector connector = new Connector();
        try (Connection connection = connector.getConnection()) {
            main.run(connection);
        } catch (SQLException ex) {
            System.out.println("The connection to the database failed!");
            ex.printStackTrace();
        }
    }

}
