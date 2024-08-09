package com.acn.jive.mastadonweatherbot;

import com.acn.jive.mastadonweatherbot.http.HttpConnection;
import com.acn.jive.mastadonweatherbot.mastodon.MastodonException;
import com.acn.jive.mastadonweatherbot.mastodon.MastodonPost;
import com.acn.jive.mastadonweatherbot.mastodon.PostStatus;
import com.acn.jive.mastadonweatherbot.persistence.Location;
import com.acn.jive.mastadonweatherbot.persistence.LocationRepository;
import com.acn.jive.mastadonweatherbot.persistence.PostHistoryRepository;
import com.acn.jive.mastadonweatherbot.persistence.RepositoryException;
import com.acn.jive.mastadonweatherbot.weather.Weather;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiResponse;
import com.acn.jive.mastadonweatherbot.weather.WeatherApiService;
import com.acn.jive.mastadonweatherbot.weather.WeatherException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public void run(Connection connection) {
        HttpConnection HTTPConnection = new HttpConnection();
        WeatherApiService weatherApiService = new WeatherApiService();
        PostStatus postStatus = new PostStatus();
        LocationRepository locationRepository = new LocationRepository(connection);
        PostHistoryRepository postHistoryRepository = new PostHistoryRepository(connection);

        try {
            List<Location> locations = locationRepository.readAllActiveLocations();
            for (Location location : locations) {
                WeatherApiResponse weatherApiResponse = weatherApiService.readWeatherDataByCoordinates(location, HTTPConnection);
                Long id = postHistoryRepository.saveWeatherApiResponse(weatherApiResponse, location);
                JSONObject jsonObject = weatherApiResponse.getJsonObject();
                if (jsonObject != null) {
                    Weather weather = weatherApiService.createWeatherObjectFromJson(jsonObject);
                    MastodonPost mastodonPost = postStatus.execute(weather);
                    postHistoryRepository.saveMastodonPostInfo(mastodonPost, id);
                }
            }
        } catch (RepositoryException | WeatherException | MastodonException ex) {
            logger.error("Could not run job", ex);
        }
    }

    public static void main(String[] args) {
        logger.info("Application started.");
        Main main = new Main();
        Connector connector = new Connector();
        try (Connection connection = connector.getConnection()) {
            main.run(connection);
        } catch (SQLException ex) {
            logger.error("Connection to DB failed", ex);
        }
    }
}
