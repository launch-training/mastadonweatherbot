package com.acn.jive.mastadonweatherbot.weather;

import com.acn.jive.mastadonweatherbot.http.HTTPConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class WeatherApiService {


    public Optional<Weather> readWeatherData(String city, HTTPConnection HTTPConnection) throws WeatherException {
        try {
            String API_KEY = "437ff40d4bf2412581d135526241807";
            String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city; // ab ? request param
            HttpURLConnection apiConnection = HTTPConnection.createApiConnection(url);

            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return Optional.empty();
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject locationWeatherJson = (JSONObject) jsonObject.get("location");
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");
            JSONObject condition = (JSONObject) currentWeatherJson.get("condition");

            double temperature = (double) currentWeatherJson.get("temp_c");

            String description = (String) condition.get("text");

            String dateTime = (String) locationWeatherJson.get("localtime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

            String iconUrl = (String) condition.get("icon");
            System.out.println("URL: " + iconUrl);

            Weather weather = new Weather();
            weather.setCity(city);
            weather.setTemperature(temperature);
            weather.setDescription(description);
            weather.setLocalTime(localDateTime);
            weather.setIconUrl(iconUrl);
            return Optional.of(weather);

        } catch (IOException | ParseException e) {
            throw new WeatherException(e.getMessage(), e);
        }
    }

    private String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }

            scanner.close();

            return resultJson.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
