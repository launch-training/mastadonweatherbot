package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.http.HTTPConnection;
import com.acn.jive.mastadonweatherbot.mastodon.PostStatus;
import com.acn.jive.mastadonweatherbot.persistence.Location;
import com.acn.jive.mastadonweatherbot.persistence.LocationRepository;
import com.acn.jive.mastadonweatherbot.persistence.PostHistoryRepository;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiResponse;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiService;
import org.json.simple.JSONObject;

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
        PostHistoryRepository postHistoryRepository = new PostHistoryRepository(connection);

        try {
            List<Location> locations = locationRepository.readAllActiveLocations();
            for (Location location : locations) {

                WeatherApiResponse weatherApiResponse = weatherApiService.readWeatherDataByCoordinates(location, HTTPConnection);
                JSONObject jsonObject = weatherApiResponse.getJsonObject();
                if (jsonObject != null) {
                    Weather weather = weatherApiService.createWeatherObjectFromJson(jsonObject);
                    postStatus.execute(weather);
                }
            }
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
